/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package clientSide.Entities;

import AuxTools.Bag;
import clientSide.Stubs.ArrivalLoungeStub;
import clientSide.Stubs.BaggageCollectionPointStub;
import clientSide.Stubs.TemporaryStorageAreaStub;

/**
 * This file implements the Porter entity/thread.
 * A porter's cycle is briefly the following:
 * He is waiting for a plane to land,
 * and then he collects the bags at the plane's hold
 * and carries them to the luggage conveyor belt or
 * to the storeroom.
 * When he finishes the job, he returns and
 * waits for another plane to land.
 */

public class Porter extends Thread {

    /**
     * Porter's current state
     */
    private PorterState state;

    /**
     * Bag
     */
    private Bag bag;

    /**
     * If the plane hold is empty
     */
    private boolean planeHoldEmpty;

    /**
     * Arrival Lounge
     */
    private final ArrivalLoungeStub al;

    /**
     * Temporary Storage Area
     */
    private final TemporaryStorageAreaStub tsa;

    /**
     * Baggage Collection Point
     */
    private final BaggageCollectionPointStub bcp;


    /**
     * Porter instantiation
     *
     * @param s   -> state
     * @param al  -> arrival lounge
     * @param tsa -> temporary storage area
     * @param bcp -> baggage collection point
     */
    public Porter(PorterState s, ArrivalLoungeStub al, TemporaryStorageAreaStub tsa, BaggageCollectionPointStub bcp) {
        this.state = s;
        this.al = al;
        this.tsa = tsa;
        this.bcp = bcp;
    }


    /**
     * Sets the Porter's state
     *
     * @param s -> the state to be set
     */
    public void setPorterState(PorterState s) {
        this.state = s;
    }

    /**
     * Returns the Porter's state
     *
     * @return Porter's state
     */
    public PorterState getPorterState() {
        return this.state;
    }

    /**
     * Porter's life-cycle
     */
    @Override
    public void run() {

        while (al.takeARest() != 'E') {              //until it's not the end of the day (E)
            planeHoldEmpty = false;

            while (!planeHoldEmpty) {
                bag = al.tryToCollectABag();        //while there's bags on the plane's hold, he collects them
                //Bag storage is empty
                if (bag == null) {
                    planeHoldEmpty = true;            // bag = null -> plane's hold empty
                } else if (bag.getDestination() == 'T') {     //the bag is on transit
                    tsa.carryItToAppropriateStore(bag);

                } else {
                	bcp.carryItToAppropriateStore(bag);   //bag has as its final destination that airport      
                }
            }
            al.noMoreBagsToCollect();
        }
    }

}
