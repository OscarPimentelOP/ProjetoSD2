package clientSide.main;


import Stubs.ArrivalTerminalTransferQuayStub;
import Stubs.DepartureTerminalTransferQuayStub;
import clientSide.Entities.BusDriver;
import clientSide.Entities.BusDriverState;

public class mainBusDriver {
	public static void main(String args[]) {
		//Instantiate shared region stubs
		ArrivalTerminalTransferQuayStub attq = new ArrivalTerminalTransferQuayStub();
		DepartureTerminalTransferQuayStub dttq = new DepartureTerminalTransferQuayStub();
		
		//Instantiate porter
		BusDriver busdriver = new BusDriver(BusDriverState.PARKING_AT_THE_ARRIVAL_TERMINAL,
                attq, dttq, repo);
		//Start thread
		busdriver.start();
		
		//Join thread
		try{
			busdriver.join();
		} catch(InterruptedException e){}
	}
}
