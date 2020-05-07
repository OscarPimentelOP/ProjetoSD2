package serverSide.main;

import java.net.SocketTimeoutException;

import AuxTools.SimulatorParam;
import clientSide.Stubs.ArrivalTerminalTransferQuayStub;
import clientSide.Stubs.RepoStub;
import serverSide.ServerCom;
import serverSide.Proxys.DepartureTerminalTransferQuayProxy;
import serverSide.sharedRegionInterfaces.DepartureTerminalTransferQuayInterface;
import serverSide.sharedRegions.DepartureTerminalTransferQuay;

public class mainDepartureTerminalTransferQuay {
	
	public static boolean opened;
	
    public static void main (String [] args){
    	
    	//Departure terminal transfer quay port
		final int portNumb = SimulatorParam.mainDepartureTerminalTransferQuayPort;
		
		ServerCom scon, sconi;                              
	    DepartureTerminalTransferQuayProxy dttqProxy;
	    
	    //Create listening channel
	    scon = new ServerCom (portNumb);                    
	    scon.start ();
	    
	    //Instantiate Stubs
	    RepoStub repo = new RepoStub();
	    ArrivalTerminalTransferQuayStub attqStub = new ArrivalTerminalTransferQuayStub();
	    
	    //Instantiate Shared Region
	    DepartureTerminalTransferQuay dttq = new DepartureTerminalTransferQuay(repo);
	    dttq.setArrivalTerminalTransferQuay(attqStub);
	    
	    //Instantiate Shared Region interface
        DepartureTerminalTransferQuayInterface dttqInter = new DepartureTerminalTransferQuayInterface(dttq);
	    
        //Process Requests while clients not finished
	    opened = true;
	    while (opened)
	    {	try {
	    		//listening
				sconi = scon.accept ();
				//Launch proxy
		    	dttqProxy = new DepartureTerminalTransferQuayProxy(sconi, dttqInter);    
		    	dttqProxy.start ();
			} catch (SocketTimeoutException e) {}
	    }
	    //Terminate operations
	    scon.end();
	}
    
}