package serverSide.main;

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
	public static void main (String [] args){
		final int portNumb = SimulatorParam.mainDepartureTerminalEntrancePort;
		
		ServerCom scon, sconi;                              
		DepartureTerminalEntranceProxy dteProxy;
	    
	    scon = new ServerCom (portNumb);                    
	    scon.start ();
	    
	    ArrivalLoungeStub al = new ArrivalLoungeStub();
	    RepoStub repo = new RepoStub();
	    ArrivalTerminalTransferQuayStub attq = new ArrivalTerminalTransferQuayStub();
	    ArrivalTerminalExitStub ate = new ArrivalTerminalExitStub();
	    
	    DepartureTerminalEntrance dte = new DepartureTerminalEntrance(al, attq, repo);
	    dte.setArrivalExit(ate);
	    
	    DepartureTerminalEntranceInterface dteInter = new DepartureTerminalEntranceInterface(dte);
	    
	    while (true)
	    { sconi = scon.accept ();
	    	dteProxy = new DepartureTerminalEntranceProxy(sconi, dteInter);    
	    	dteProxy.start ();
	    }
	}
}
