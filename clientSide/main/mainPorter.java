package clientSide.main;

import Stubs.ArrivalLoungeStub;
import Stubs.TemporaryStorageAreaStub;
import clientSide.Entities.Porter;
import clientSide.Entities.PorterState;
import Stubs.BaggageCollectionPointStub;

public class mainPorter {
	public static void main(String args[]) {
		//Instantiate shared region stubs
		ArrivalLoungeStub al = new ArrivalLoungeStub();
		TemporaryStorageAreaStub tsa = new TemporaryStorageAreaStub();
		BaggageCollectionPointStub bcp = new BaggageCollectionPointStub();
		
		//Instantiate porter
		Porter porter = new Porter(PorterState.WAITING_FOR_A_PLANE_TO_LAND, al, tsa, bcp);
		//Start thread
		porter.start();
		
		//Join thread
		try{
			porter.join();
		} catch(InterruptedException e){}
	}

}
