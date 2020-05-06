package serverSide.main;

import AuxTools.SimulatorParam;
import clientSide.Stubs.BaggageCollectionPointStub;
import clientSide.Stubs.RepoStub;
import serverSide.ServerCom;
import serverSide.Proxys.ArrivalLoungeProxy;
import serverSide.sharedRegionInterfaces.ArrivalLoungeInterface;
import serverSide.sharedRegions.ArrivalLounge;

public class mainArrivalLounge {
	public static void main (String [] args){
		final int portNumb = SimulatorParam.mainArrivalLoungePort;
		
		ServerCom scon, sconi;                              
	    ArrivalLoungeProxy alProxy;
	    
	    scon = new ServerCom (portNumb);                    
	    scon.start ();
	    
	    BaggageCollectionPointStub bcp = new BaggageCollectionPointStub();
	    RepoStub repo = new RepoStub();
	    
	    ArrivalLounge al = new ArrivalLounge(bcp, repo);
	    
	    ArrivalLoungeInterface alInter = new ArrivalLoungeInterface(al);
	    
	    while (true)
	    { sconi = scon.accept ();
	    	alProxy = new ArrivalLoungeProxy(sconi, alInter);    
	    	alProxy.start ();
	    }
	}
}
