/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package serverSide.sharedRegions;

import clientSide.Entities.BusDriverState;
import clientSide.Entities.PassengerState;
import clientSide.Stubs.ArrivalTerminalTransferQuayStub;
import clientSide.Stubs.RepoStub;

/**
 * This class implements the Departure Terminal Transfer Quay shared region.
 * In this region, the bus driver parks the bus, and the passengers leave the bus to prepare their next leg.
 */

public class DepartureTerminalTransferQuay {


    /**
     * Variable that will unblock the passengers when the bus driver parks the bus
     */
    private boolean parked;


    /**
     * Count of passengers that left the bus
     */
    private int cntPassengersOut;

    /**
     * The repository, to store the program status
     */
    private RepoStub repo;

    /**
     * Arrival terminal transfer quay shared region
     */
    private ArrivalTerminalTransferQuayStub attq;

    /**
     * Departure terminal transfer quay's instantiation
     *
     * @param repo -> repository of information
     */
    public DepartureTerminalTransferQuay(RepoStub repo) {
        this.repo = repo;
        this.parked = false;
        this.cntPassengersOut = 0;
    }

    //Passengers functions

    /**
     * The passengers leave the bus when it parks.
     * The passengers will be blocked inside the bus,
     * and when the bus driver parks, that unblocks the passengers
     * and they leave.
     */
    public synchronized void leaveTheBus(int id) {
        while (!this.getParked()) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        attq.readFromBus();
        repo.setPassangersOnTheBus(this.getCntPassengersOut(), -1);
        this.incCntPassengersOut();
        attq.decCntPassengersInBus();
        //When the last passenger exits the bus
        if (attq.getCntPassengersInBus() == 0) {
            parked = false;
            notifyAll();
        }
        repo.setPassengerState(id, PassengerState.AT_THE_DEPARTURE_TRANSFER_TERMINAL);
    }


    //Bus driver functions

    /**
     * The bus driver parks the bus and lets the passengers get off.
     * This changes the bus driver's state to parking at the departure terminal
     * and adjusts the number of passengers out of bus.
     */
    public synchronized void parkTheBusAndLetPassOff() {
        repo.setBusDriverState(BusDriverState.PARKING_AT_THE_DEPARTURE_TERMINAL);
        parked = true;
        notifyAll();
        while (attq.getCntPassengersInBus() != 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        this.setCntPassengersOut();
    }

    /**
     * Increments the number of passengers out of the bus.
     */
    public synchronized void incCntPassengersOut() {
        this.cntPassengersOut++;
    }

    /**
     * Resets the number of passengers out of the bus.
     */
    public synchronized void setCntPassengersOut() {
        this.cntPassengersOut = 0;
    }

    /**
     * Returns the number of passengers out of the bus.
     *
     * @return the count of passengers outside of the bus
     */
    public synchronized int getCntPassengersOut() {
        return this.cntPassengersOut;
    }

    /**
     * Sets the arrival terminal transfer quay shared region
     */
    public synchronized void setArrivalTerminalTransferQuay(ArrivalTerminalTransferQuayStub attqStub) {
        this.attq = attqStub;
    }

    /**
     * Tells if the bus driver has parked the bus
     */
    public synchronized boolean getParked() {
        return this.parked;
    }
}
