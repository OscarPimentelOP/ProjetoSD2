package serverSide.main;

import java.net.SocketTimeoutException;

import AuxTools.SimulatorParam;
import clientSide.Stubs.RepoStub;
import serverSide.ServerCom;
import serverSide.Proxys.TemporaryStorageAreaProxy;
import serverSide.sharedRegionInterfaces.TemporaryStorageAreaInterface;
import serverSide.sharedRegions.TemporaryStorageArea;

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