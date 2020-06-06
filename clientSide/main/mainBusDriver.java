/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package clientSide.main;


import clientSide.Stubs.ArrivalTerminalTransferQuayStub;
import clientSide.Stubs.DepartureTerminalTransferQuayStub;
import clientSide.Stubs.RepoStub;
import clientSide.Entities.BusDriver;
import clientSide.Entities.BusDriverState;


/**
 * This class implements the Bus Driver Main. It instantiates the shared region Stubs which the Bus Driver interacts with
 * and most importantly it instantiates the Bus Driver entity, starts its thread and finally joins it and shutdowns the servers related
 * to the Stubs previously instantiated.
 */
public class mainBusDriver {
	public static void main(String args[]) {
		//Instantiate shared region stubs
		ArrivalTerminalTransferQuayStub attq = new ArrivalTerminalTransferQuayStub();
		DepartureTerminalTransferQuayStub dttq = new DepartureTerminalTransferQuayStub();
		RepoStub repo = new RepoStub();
		
		//Instantiate Bus Driver
		BusDriver busdriver = new BusDriver(BusDriverState.PARKING_AT_THE_ARRIVAL_TERMINAL,
                attq, dttq, repo);
		//Start thread
		busdriver.start();
		
		//Join thread
		try{
			busdriver.join();
		} catch(InterruptedException e){}
		//Shutdown servers
		attq.shutServer();
		dttq.shutServer();
		repo.shutServer();
	}
}
