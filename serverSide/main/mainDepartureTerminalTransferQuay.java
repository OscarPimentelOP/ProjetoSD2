/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package serverSide.main;

import java.net.SocketTimeoutException;

import AuxTools.SimulatorParam;
import clientSide.Stubs.ArrivalTerminalTransferQuayStub;
import clientSide.Stubs.RepoStub;
import serverSide.ServerCom;
import serverSide.Proxys.DepartureTerminalTransferQuayProxy;
import serverSide.sharedRegionInterfaces.DepartureTerminalTransferQuayInterface;
import serverSide.sharedRegions.DepartureTerminalTransferQuay;


/**
 * This class implements the Departure Terminal Transfer Quay Main that instantiates the shared region Stubs
 * that are part of Departure Terminal Transfer Quay arguments, instantiates the Departure Terminal Transfer Quay Interface 
 * and launches the Departure Terminal Transfer Quay Proxy.
 */
public class mainDepartureTerminalTransferQuay {
	
	public static int terminated;
	
    public static void main (String [] args){
    	
    	//Departure terminal transfer quay port
		final int portNumb = SimulatorParam.departureTerminalTransferQuayPort;
		
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
	    terminated = 0;
	    while (terminated != 2)
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