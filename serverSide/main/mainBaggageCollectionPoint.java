/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package serverSide.main;

import java.net.SocketTimeoutException;

import AuxTools.SimulatorParam;
import clientSide.Stubs.RepoStub;
import serverSide.ServerCom;
import serverSide.Proxys.BaggageCollectionPointProxy;
import serverSide.sharedRegionInterfaces.BaggageCollectionPointInterface;
import serverSide.sharedRegions.BaggageCollectionPoint;

/**
 * This class implements the Baggage Collection Point Main that instantiates the shared region Stubs
 * that are part of Baggage Collection Point arguments, instantiates the Baggage Collection Point Interface 
 * and launches the Baggage Collection Point Proxy.
 */
public class mainBaggageCollectionPoint {
	
	public static int terminated;
	
	public static void main (String [] args){
		
		//Baggage collection point port
		final int portNumb = SimulatorParam.baggageCollectionPointPort;
		
		ServerCom scon, sconi;                              
		BaggageCollectionPointProxy bcpProxy;
	    
		//Create listening channel
	    scon = new ServerCom (portNumb);                    
	    scon.start ();
	    
	    //Instantiate Stub
	    RepoStub repo = new RepoStub();
	    
	    //Instantiate Shared Region
	    BaggageCollectionPoint bcp = new BaggageCollectionPoint(repo);
	    
	    //Instantiate Shared Region interface
	    BaggageCollectionPointInterface bcpInter = new BaggageCollectionPointInterface(bcp);
	    
	    //Process Requests while clients not finished
	    terminated = 0;
	    while (terminated != 2)
	    { 	try {
	    		//listening
				sconi = scon.accept ();
				//Launch proxy
		    	bcpProxy = new BaggageCollectionPointProxy(sconi, bcpInter);    
		    	bcpProxy.start ();
			} catch (SocketTimeoutException e) {}
	    }
	    //Terminate operations
	    scon.end();
	}
}
