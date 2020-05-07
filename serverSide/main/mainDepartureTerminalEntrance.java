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

public class mainDepartureTerminalEntrance {
	
	public static boolean opened;
	
	public static void main (String [] args){
		
		//Departure terminal entrance port
		final int portNumb = SimulatorParam.mainDepartureTerminalEntrancePort;
		
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
	    opened = true;
	    while (opened)
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
