/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package clientSide.main;

import clientSide.Stubs.ArrivalLoungeStub;
import clientSide.Stubs.TemporaryStorageAreaStub;
import clientSide.Entities.Porter;
import clientSide.Entities.PorterState;
import clientSide.Stubs.BaggageCollectionPointStub;


/**
 * This class implements the Porter Main. It instantiates the shared region Stubs which the Porter interacts with
 * and most importantly it instantiates the Porter entity making him waiting for a plane to land, starts its thread and finally joins it and shutdowns the servers related
 * to the Stubs previously instantiated.
 */
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
		//Shutdown servers
		al.shutServer();
		tsa.shutServer();
		bcp.shutServer();
	}

}
