/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package serverSide.main;

import java.net.SocketTimeoutException;

import AuxTools.SimulatorParam;
import clientSide.Stubs.RepoStub;
import serverSide.ServerCom;
import serverSide.Proxys.BaggageReclaimOfficeProxy;
import serverSide.sharedRegionInterfaces.BaggageReclaimOfficeInterface;
import serverSide.sharedRegions.BaggageReclaimOffice;

/**
 * This class implements the Baggage Reclaim Office Main that instantiates the shared region Stubs
 * that are part of Baggage Reclaim Office arguments, instantiates the Baggage Reclaim Office Interface 
 * and launches the Baggage Reclaim Office Proxy.
 */
public class mainBaggageReclaimOffice {
	
	public static int terminated;
	
	public static void main (String [] args){
		
		//Baggage reclaim office port
		final int portNumb = SimulatorParam.baggageReclaimOfficePort;
		
		ServerCom scon, sconi;                              
		BaggageReclaimOfficeProxy broProxy;
	    
		//Create listening channel
	    scon = new ServerCom (portNumb);                    
	    scon.start ();
	    
	    //Instantiate Stub
	    RepoStub repo = new RepoStub();
	    
	    //Instantiate Shared Region
	    BaggageReclaimOffice bro = new BaggageReclaimOffice(repo);
	    
	    //Instantiate Shared Region interface
	    BaggageReclaimOfficeInterface broInter = new BaggageReclaimOfficeInterface(bro);
	    
	    //Process Requests while clients not finished
	    terminated = 0;
	    while (terminated != 1)
	    { 	try {
	    		//listening
				sconi = scon.accept ();
				//Launch proxy
		    	broProxy = new BaggageReclaimOfficeProxy(sconi, broInter);    
		    	broProxy.start ();
			} catch (SocketTimeoutException e) {}
	    }
	    //Terminate operations
	    scon.end();
	}
}
