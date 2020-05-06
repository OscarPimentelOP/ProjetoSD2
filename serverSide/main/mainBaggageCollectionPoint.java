package serverSide.main;

import AuxTools.SimulatorParam;
import clientSide.Stubs.BaggageCollectionPointStub;
import clientSide.Stubs.RepoStub;
import serverSide.ServerCom;
import serverSide.Proxys.ArrivalLoungeProxy;
import serverSide.Proxys.BaggageCollectionPointProxy;
import serverSide.sharedRegionInterfaces.ArrivalLoungeInterface;
import serverSide.sharedRegionInterfaces.BaggageCollectionPointInterface;
import serverSide.sharedRegions.ArrivalLounge;
import serverSide.sharedRegions.BaggageCollectionPoint;

public class mainBaggageCollectionPoint {
	public static void main (String [] args){
		final int portNumb = SimulatorParam.mainBaggageCollectionPointPort;
		
		ServerCom scon, sconi;                              
		BaggageCollectionPointProxy bcpProxy;
	    
	    scon = new ServerCom (portNumb);                    
	    scon.start ();
	    
	    RepoStub repo = new RepoStub();
	    
	    BaggageCollectionPoint bcp = new BaggageCollectionPoint(repo);
	    
	    BaggageCollectionPointInterface bcpInter = new BaggageCollectionPointInterface(bcp);
	    
	    while (true)
	    { sconi = scon.accept ();
	    	bcpProxy = new BaggageCollectionPointProxy(sconi, bcpInter);    
	    	bcpProxy.start ();
	    }
	}
}
