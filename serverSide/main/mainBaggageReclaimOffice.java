package serverSide.main;

import AuxTools.SimulatorParam;
import clientSide.Stubs.RepoStub;
import serverSide.ServerCom;
import serverSide.Proxys.BaggageCollectionPointProxy;
import serverSide.Proxys.BaggageReclaimOfficeProxy;
import serverSide.sharedRegionInterfaces.BaggageCollectionPointInterface;
import serverSide.sharedRegionInterfaces.BaggageReclaimOfficeInterface;
import serverSide.sharedRegions.BaggageCollectionPoint;
import serverSide.sharedRegions.BaggageReclaimOffice;

public class mainBaggageReclaimOffice {
	public static void main (String [] args){
		final int portNumb = SimulatorParam.mainBaggageReclaimOfficePort;
		
		ServerCom scon, sconi;                              
		BaggageReclaimOfficeProxy broProxy;
	    
	    scon = new ServerCom (portNumb);                    
	    scon.start ();
	    
	    RepoStub repo = new RepoStub();
	    
	    BaggageReclaimOffice bro = new BaggageReclaimOffice(repo);
	    
	    BaggageReclaimOfficeInterface broInter = new BaggageReclaimOfficeInterface(bro);
	    
	    while (true)
	    { sconi = scon.accept ();
	    	broProxy = new BaggageReclaimOfficeProxy(sconi, broInter);    
	    	broProxy.start ();
	    }
	}
}
