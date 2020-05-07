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

public class mainArrivalTerminalExit {
	
	public static boolean opened;
	
	public static void main (String [] args){
		
		//Arrival terminal exit port
		final int portNumb = SimulatorParam.mainArrivalTerminalExitPort;
		
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
	    opened = true;
	    while (opened)
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
