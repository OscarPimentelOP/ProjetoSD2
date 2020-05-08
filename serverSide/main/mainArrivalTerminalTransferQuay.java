package serverSide.main;

import java.net.SocketTimeoutException;

import AuxTools.SimulatorParam;
import clientSide.Stubs.RepoStub;
import serverSide.ServerCom;
import serverSide.Proxys.ArrivalTerminalTransferQuayProxy;
import serverSide.sharedRegionInterfaces.ArrivalTerminalTransferQuayInterface;
import serverSide.sharedRegions.ArrivalTerminalTransferQuay;

public class mainArrivalTerminalTransferQuay {
	
	public static int terminated;
	
	public static void main (String [] args){
		
		//Arrival terminal transfer quay port
		final int portNumb = SimulatorParam.arrivalTerminalTransferQuayPort;
		
		ServerCom scon, sconi;                              
		ArrivalTerminalTransferQuayProxy attqProxy;
	    
		//Create listening channel
	    scon = new ServerCom (portNumb);                    
	    scon.start ();
	    
	    //Instantiate Stub
	    RepoStub repo = new RepoStub();
	    
	    //Instantiate Shared Region
	    ArrivalTerminalTransferQuay attq = new ArrivalTerminalTransferQuay(repo);
	    
	    //Instantiate Shared Region interface
	    ArrivalTerminalTransferQuayInterface attqInter = new ArrivalTerminalTransferQuayInterface(attq);
	    
	    //Process Requests while clients not finished
	    terminated = 0;
	    while (terminated != 2)
		{	try {
				//listening
				sconi = scon.accept ();
				//Launch proxys
		    	attqProxy = new ArrivalTerminalTransferQuayProxy(sconi, attqInter);    
		    	attqProxy.start ();
			} catch (SocketTimeoutException e) {}
	    }
	    //Terminate operations
	    scon.end();
	}
}
