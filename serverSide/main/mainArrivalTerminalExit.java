package serverSide.main;

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
	public static void main (String [] args){
		final int portNumb = SimulatorParam.mainArrivalTerminalExitPort;
		
		ServerCom scon, sconi;                              
		ArrivalTerminalExitProxy ateProxy;
	    
	    scon = new ServerCom (portNumb);                    
	    scon.start ();
	    
	    ArrivalLoungeStub al = new ArrivalLoungeStub();
	    RepoStub repo = new RepoStub();
	    ArrivalTerminalTransferQuayStub attq = new ArrivalTerminalTransferQuayStub();
	    DepartureTerminalEntranceStub dte = new DepartureTerminalEntranceStub();
	    
	    ArrivalTerminalExit ate = new ArrivalTerminalExit(al, attq, repo);
	    ate.setDepartureEntrance(dte);
	    
	    ArrivalTerminalExitInterface ateInter = new ArrivalTerminalExitInterface(ate);
	    
	    while (true)
	    { sconi = scon.accept ();
	    	ateProxy = new ArrivalTerminalExitProxy(sconi, ateInter);    
	    	ateProxy.start ();
	    }
	}
}
