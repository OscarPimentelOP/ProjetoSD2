/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package serverSide.main;

import java.net.SocketTimeoutException;

import AuxTools.SimulatorParam;
import clientSide.Stubs.ArrivalLoungeStub;
import clientSide.Stubs.ArrivalTerminalTransferQuayStub;
import clientSide.Stubs.DepartureTerminalEntranceStub;
import clientSide.Stubs.RepoStub;
import serverSide.ServerCom;
import serverSide.Proxys.ArrivalTerminalExitProxy;
import serverSide.sharedRegionInterfaces.ArrivalTerminalExitInterface;
import serverSide.sharedRegions.ArrivalTerminalExit;

/**
 * This class implements the Arrival Terminal Exit Main that instantiates the shared region Stubs
 * that are part of Arrival Terminal Exit arguments, instantiates the Arrival Terminal Exit Interface and launches the Arrival Terminal Exit Proxy.
 */
public class mainArrivalTerminalExit {
	
	public static int terminated;
	
	public static void main (String [] args){
		
		//Arrival terminal exit port
		final int portNumb = SimulatorParam.arrivalTerminalExitPort;
		
		ServerCom scon, sconi;                              
		ArrivalTerminalExitProxy ateProxy;
	    
		//Create listening channel
	    scon = new ServerCom (portNumb);                    
	    scon.start ();
	    
	    //Instantiate Stubs
	    ArrivalLoungeStub al = new ArrivalLoungeStub();
	    RepoStub repo = new RepoStub();
	    ArrivalTerminalTransferQuayStub attq = new ArrivalTerminalTransferQuayStub();
	    DepartureTerminalEntranceStub dte = new DepartureTerminalEntranceStub();
	    
	    //Instantiate Shared Region
	    ArrivalTerminalExit ate = new ArrivalTerminalExit(al, attq, repo);
	    ate.setDepartureEntrance(dte);
	    
	    //Instantiate Shared Region interface
	    ArrivalTerminalExitInterface ateInter = new ArrivalTerminalExitInterface(ate);
	    
	    //Process Requests while clients not finished
	    terminated = 0;
	    while (terminated != 1)
	    { 	try {
	    		//listening
				sconi = scon.accept ();
				//Launch proxy
		    	ateProxy = new ArrivalTerminalExitProxy(sconi, ateInter);
		    	ateProxy.start ();
			} catch (SocketTimeoutException e) {}
	    }
	    //Terminate operations
	    scon.end();
	}
}
