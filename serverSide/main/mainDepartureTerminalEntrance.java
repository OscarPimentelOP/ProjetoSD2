/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package serverSide.main;

import java.net.SocketTimeoutException;

import AuxTools.SimulatorParam;
import clientSide.Stubs.ArrivalLoungeStub;
import clientSide.Stubs.ArrivalTerminalExitStub;
import clientSide.Stubs.ArrivalTerminalTransferQuayStub;
import clientSide.Stubs.RepoStub;
import serverSide.ServerCom;
import serverSide.Proxys.DepartureTerminalEntranceProxy;
import serverSide.sharedRegionInterfaces.DepartureTerminalEntranceInterface;
import serverSide.sharedRegions.DepartureTerminalEntrance;


/**
 * This class implements the Departure Terminal Entrance Main that instantiates the shared region Stubs
 * that are part of Departure Terminal Entrance arguments, instantiates the Departure Terminal Entrance Interface 
 * and launches the Departure Terminal Entrance Proxy.
 */
public class mainDepartureTerminalEntrance {
	
	public static int terminated;
	
	public static void main (String [] args){
		
		//Departure terminal entrance port
		final int portNumb = SimulatorParam.departureTerminalEntrancePort;
		
		ServerCom scon, sconi;                              
		DepartureTerminalEntranceProxy dteProxy;
	    
		//Create listening channel
	    scon = new ServerCom (portNumb);                    
	    scon.start ();
	    
	    //Instantiate Stubs
	    ArrivalLoungeStub al = new ArrivalLoungeStub();
	    RepoStub repo = new RepoStub();
	    ArrivalTerminalTransferQuayStub attq = new ArrivalTerminalTransferQuayStub();
	    ArrivalTerminalExitStub ate = new ArrivalTerminalExitStub();
	    
	    //Instantiate Shared Region
	    DepartureTerminalEntrance dte = new DepartureTerminalEntrance(al, attq, repo);
	    dte.setArrivalExit(ate);
	    
	    //Instantiate Shared Region interface
	    DepartureTerminalEntranceInterface dteInter = new DepartureTerminalEntranceInterface(dte);
	    
	    //Process Requests while clients not finished
	    terminated = 0;
	    while (terminated != 1)
	    { 	try {
	    		//listening
				sconi = scon.accept ();
				//Launch proxy
		    	dteProxy = new DepartureTerminalEntranceProxy(sconi, dteInter);    
		    	dteProxy.start ();
			} catch (SocketTimeoutException e) {}
	    }
	    //Terminate operations
	    scon.end();
	}
}
