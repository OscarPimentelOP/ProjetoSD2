package serverSide.main;

import AuxTools.SimulatorParam;
import clientSide.Stubs.ArrivalTerminalTransferQuayStub;
import clientSide.Stubs.DepartureTerminalTransferQuayStub;
import clientSide.Stubs.RepoStub;
import serverSide.ServerCom;
import serverSide.Proxys.DepartureTerminalTransferQuayProxy;
import serverSide.Proxys.TemporaryStorageAreaProxy;
import serverSide.sharedRegionInterfaces.DepartureTerminalTransferQuayInterface;
import serverSide.sharedRegions.DepartureTerminalTransferQuay;

public class mainDepartureTerminalTransferQuay {
    public static void main (String [] args){
		final int portNumb = SimulatorParam.mainTemporaryStorageAreaPort;
		
		ServerCom scon, sconi;                              
	    DepartureTerminalTransferQuayProxy dttqProxy;
	    
	    scon = new ServerCom (portNumb);                    
	    scon.start ();
	    
	    RepoStub repo = new RepoStub();
	    
	    DepartureTerminalTransferQuay dttq = new DepartureTerminalTransferQuay(repo);
	    
        DepartureTerminalTransferQuayInterface dttqInter = new DepartureTerminalTransferQuayInterface(dttq);
        
        ArrivalTerminalTransferQuayStub attqStub = new ArrivalTerminalTransferQuayStub();
	    dttq.setArrivalTerminalTransferQuay(attqStub);
	    while (true)
	    { sconi = scon.accept ();
	    	dttqProxy = new DepartureTerminalTransferQuayProxy(sconi, dttqInter);    
	    	dttqProxy.start ();
	    }
	}
    
}