/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package serverSide.main;

import java.net.SocketTimeoutException;

import AuxTools.SimulatorParam;
import clientSide.Stubs.RepoStub;
import serverSide.ServerCom;
import serverSide.Proxys.TemporaryStorageAreaProxy;
import serverSide.sharedRegionInterfaces.TemporaryStorageAreaInterface;
import serverSide.sharedRegions.TemporaryStorageArea;


/**
 * This class implements the Temporary Storage Area Main that instantiates the shared region Stubs
 * that are part of Temporary Storage Area arguments, instantiates the Temporary Storage Area Interface 
 * and launches the Temporary Storage Area Proxy.
 */
public class mainTemporaryStorageArea {
	
	public static int terminated;
	
    public static void main (String [] args){
    	
    	//Temporary storage area port
		final int portNumb = SimulatorParam.temporaryStorageAreaPort;
		
		ServerCom scon, sconi;                              
	    TemporaryStorageAreaProxy tsaProxy;
	    
	    //Create listening channel
	    scon = new ServerCom (portNumb);                    
	    scon.start ();
	    
	    //Instantiate Stub
	    RepoStub repo = new RepoStub();
	    
	    //Instantiate Shared Region
	    TemporaryStorageArea tsa = new TemporaryStorageArea(repo);
	    
	    //Instantiate Shared Region interface
	    TemporaryStorageAreaInterface tsaInter = new TemporaryStorageAreaInterface(tsa);
	    
	    //Process Requests while clients not finished
	    terminated = 0;
	    while (terminated != 1)
	    { 	try {
	    		//listening
				sconi = scon.accept ();
				//Launch proxy
		    	tsaProxy = new TemporaryStorageAreaProxy(sconi, tsaInter);    
		    	tsaProxy.start ();
			} catch (SocketTimeoutException e) {}
	    }
	    //Terminate operations
	    scon.end();
	}
    
}