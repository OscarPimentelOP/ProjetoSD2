package serverSide.main;

import AuxTools.SimulatorParam;
import clientSide.Stubs.RepoStub;
import serverSide.ServerCom;
import serverSide.Proxys.TemporaryStorageAreaProxy;
import serverSide.sharedRegionInterfaces.TemporaryStorageAreaInterface;
import serverSide.sharedRegions.TemporaryStorageArea;

public class mainTemporaryStorageArea {
    public static void main (String [] args){
		final int portNumb = SimulatorParam.mainTemporaryStorageAreaPort;
		
		ServerCom scon, sconi;                              
	    TemporaryStorageAreaProxy alProxy;
	    
	    scon = new ServerCom (portNumb);                    
	    scon.start ();
	    
	    RepoStub repo = new RepoStub();
	    
	    TemporaryStorageArea tsa = new TemporaryStorageArea(repo);
	    
	    TemporaryStorageAreaInterface tsaInter = new TemporaryStorageAreaInterface(tsa);
	    
	    while (true)
	    { sconi = scon.accept ();
	    	alProxy = new TemporaryStorageAreaProxy(sconi, tsaInter);    
	    	alProxy.start ();
	    }
	}
    
}