/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package serverSide.main;

import java.net.SocketTimeoutException;

import AuxTools.SimulatorParam;
import clientSide.Stubs.BaggageCollectionPointStub;
import clientSide.Stubs.RepoStub;
import serverSide.ServerCom;
import serverSide.Proxys.ArrivalLoungeProxy;
import serverSide.sharedRegionInterfaces.ArrivalLoungeInterface;
import serverSide.sharedRegions.ArrivalLounge;

/**
 * This class implements the Arrival Lounge Main that instantiates the shared region Stubs
 * that are part of Arrival Lounge arguments, instantiates the Arrival Lounge Interface and launches the Arrival Lounge Proxy.
 */
public class mainArrivalLounge {
	
	public static int terminated;
	
	public static void main (String [] args){
		
		//Arrival lounge port
		final int portNumb = SimulatorParam.arrivalLoungePort;
		
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
	    terminated = 0;
	    while (terminated != 2)
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
