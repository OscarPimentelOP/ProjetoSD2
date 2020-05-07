package serverSide.main;

import java.net.SocketTimeoutException;

import AuxTools.SimulatorParam;
import clientSide.Stubs.BaggageCollectionPointStub;
import clientSide.Stubs.RepoStub;
import serverSide.ServerCom;
import serverSide.Proxys.ArrivalLoungeProxy;
import serverSide.sharedRegionInterfaces.ArrivalLoungeInterface;
import serverSide.sharedRegions.ArrivalLounge;

public class mainArrivalLounge {
	
	public static boolean opened;
	
	public static void main (String [] args){
		
		//Arrival lounge port
		final int portNumb = SimulatorParam.mainArrivalLoungePort;
		
		ServerCom scon, sconi;                              
	    ArrivalLoungeProxy alProxy;
	    
	    //Create listening channel
	    scon = new ServerCom (portNumb);                    
	    scon.start ();
	    
	    //Instantiate Stubs
	    BaggageCollectionPointStub bcp = new BaggageCollectionPointStub();
	    RepoStub repo = new RepoStub();
	    
	    //Instantiate Shared Region
	    ArrivalLounge al = new ArrivalLounge(bcp, repo);
	    
	    //Instantiate Shared Region interface
	    ArrivalLoungeInterface alInter = new ArrivalLoungeInterface(al);
	    
	    //Process Requests while clients not finished
	    opened = true;
	    while (opened)
	    { 	try {
	    		//listening
				sconi = scon.accept (); 
				//Launch proxy
				alProxy = new ArrivalLoungeProxy(sconi, alInter);    
		    	alProxy.start ();
			} catch (SocketTimeoutException e) {}
	    }
	    //Terminate operations
	    scon.end();
	}
}
