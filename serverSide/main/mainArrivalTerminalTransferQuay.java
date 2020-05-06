package serverSide.main;

import AuxTools.SimulatorParam;
import clientSide.Stubs.RepoStub;
import serverSide.ServerCom;
import serverSide.Proxys.ArrivalTerminalTransferQuayProxy;
import serverSide.sharedRegionInterfaces.ArrivalTerminalTransferQuayInterface;
import serverSide.sharedRegions.ArrivalTerminalTransferQuay;

public class mainArrivalTerminalTransferQuay {
	public static void main (String [] args){
		final int portNumb = SimulatorParam.mainArrivalTerminalTransferQuayPort;
		
		ServerCom scon, sconi;                              
		ArrivalTerminalTransferQuayProxy attqProxy;
	    
	    scon = new ServerCom (portNumb);                    
	    scon.start ();
	    
	    RepoStub repo = new RepoStub();
	    
	    ArrivalTerminalTransferQuay attq = new ArrivalTerminalTransferQuay(repo);
	    
	    ArrivalTerminalTransferQuayInterface attqInter = new ArrivalTerminalTransferQuayInterface(attq);
	    
	    while (true)
	    { sconi = scon.accept ();
	    	attqProxy = new ArrivalTerminalTransferQuayProxy(sconi, attqInter);    
	    	attqProxy.start ();
	    }
	}
}
