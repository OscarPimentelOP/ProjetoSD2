package serverSide.main;

import java.net.SocketTimeoutException;

import AuxTools.SimulatorParam;
import clientSide.Stubs.RepoStub;
import serverSide.ServerCom;
import serverSide.Proxys.BaggageReclaimOfficeProxy;
import serverSide.sharedRegionInterfaces.BaggageReclaimOfficeInterface;
import serverSide.sharedRegions.BaggageReclaimOffice;

public class mainBaggageReclaimOffice {
	
	public static boolean opened;
	
	public static void main (String [] args){
		
		//Baggage reclaim office port
		final int portNumb = SimulatorParam.mainBaggageReclaimOfficePort;
		
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
	    opened = true;
	    while (opened)
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
