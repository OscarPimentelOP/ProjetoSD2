/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package clientSide.Entities;

import AuxTools.SimulatorParam;
import clientSide.Stubs.ArrivalLoungeStub;
import clientSide.Stubs.ArrivalTerminalExitStub;
import clientSide.Stubs.ArrivalTerminalTransferQuayStub;
import clientSide.Stubs.DepartureTerminalTransferQuayStub;
import clientSide.Stubs.DepartureTerminalEntranceStub;
import clientSide.Stubs.BaggageReclaimOfficeStub;
import clientSide.Stubs.BaggageCollectionPointStub;

/**
 * This file implements the Passenger entity/thread.
 * A passenger's cycle is briefly the following:
 * The passenger arrives at the disembarking zone,
 * and then depending on his trip destination or if he has luggage he can
 * go home immediately, collect his bags (if he has any)
 * or take a bus in order to prepare his next leg.
 * The passenger is able to report missing bags at the baggage reclaim office
 * if any of his bags are missing.
 */

public class Passenger extends Thread {

    /**
     * Passenger's current state
     */
    private PassengerState state;

    /**
     * Passenger's ID
     */
    private int identifier;


    /**
     * Passenger's number of bags per flight
     */
    private int numBags[];

    /**
     * Passenger's trip state (final destination-> F, transit -> T)
     */
    private char tripState[];

    /**
     * Arrival Lounge
     */
    private final ArrivalLoungeStub al;

    /**
     * Arrival Terminal Exit
     */
    private final ArrivalTerminalExitStub ate;

    /**
     * Arrival Terminal Transfer Quay
     */
    private final ArrivalTerminalTransferQuayStub attq;

    /**
     * Departure Terminal Transfer Quay
     */
    private final DepartureTerminalTransferQuayStub dttq;

    /**
     * Departure Terminal Entrance
     */
    private final DepartureTerminalEntranceStub dte;

    /**
     * Baggage Reclaim Office
     */
    private final BaggageReclaimOfficeStub bro;

    /**
     * Baggage Collection Point
     */
    private final BaggageCollectionPointStub bcp;
    
    
    /**
	 *  Nome do sistema computacional onde está localizado o servidor
	 *    @serialField serverHostName
	 */
	
	 private String serverHostName = "localhost";
	
	/**
	 *  Número do port de escuta do servidor
	 *    @serialField serverPortNumb
	 */
	
	 private int serverPortNumb = 4000;


    /**
     * Passenger instantiation
     *
     * @param s    -> state
     * @param id   -> passenger ID
     * @param nb   -> number of bags
     * @param ts   -> trip state
     * @param al   -> arrival lounge
     * @param ate  -> arrival terminal exit
     * @param attq -> arrival terminal transfer quay
     * @param dttq -> departure terminal transfer quay
     * @param dte  -> departure terminal exit
     * @param bro  -> baggage reclaim office
     * @param bcp  -> baggage collection point
     */
    public Passenger(PassengerState s, int id, int[] nb, char[] ts, ArrivalLoungeStub al, ArrivalTerminalExitStub ate, ArrivalTerminalTransferQuayStub attq,
                     DepartureTerminalTransferQuayStub dttq, DepartureTerminalEntranceStub dte, BaggageReclaimOfficeStub bro, BaggageCollectionPointStub bcp) {
        this.state = s;
        this.identifier = id;
        this.numBags = nb;
        this.tripState = ts;
        this.al = al;
        this.ate = ate;
        this.attq = attq;
        this.dttq = dttq;
        this.dte = dte;
        this.bro = bro;
        this.bcp = bcp;
    }


    /**
     * Sets the Passenger's state
     *
     * @param s -> the state to be set
     */
    public void setPassengerState(PassengerState s) {
        this.state = s;
    }

    /**
     * Returns the Passenger's state
     *
     * @return Passenger's state
     */
    public PassengerState getPassengerState() {
        return this.state;
    }

    /**
     * Returns the Passenger's ID
     *
     * @return ID
     */
    public int getIdentifier() {
        return this.identifier;
    }

    /**
     * Returns the Passenger's number of bags
     *
     * @return number of bags
     */
    public int getNumBags(int flight) {
        return this.numBags[flight];
    }

    /**
     * Returns the Passenger's trip state
     *
     * @return trip state
     */
    public char getTripState(int flight) {
        return this.tripState[flight];
    }

    /**
     * Passenger's life-cycle
     */
    @Override
    public void run() {
        for (int flight = 0; flight < SimulatorParam.NUM_FLIGHTS; flight++) {
            //Sleep introduced so all passengers have time to leave the previous flight
            try {
                sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            char a = al.whatShouldIDo(flight, identifier, getTripState(flight), getNumBags(flight));

            switch (a) {
                case 'H':
                    ate.goHome(flight, identifier);        //Reached final destiny, has no bag to collect, goes home
                    break;

                case 'T':
                    attq.takeABus(identifier);          //Take a bus and prepares the next leg
                    attq.enterTheBus(identifier);
                    dttq.leaveTheBus(identifier);
                    dte.prepareNextLeg(flight, identifier);
                    break;


                case 'B':                           //Has bags to collect
                    int numOfCollectedBags = 0;
                    while (numOfCollectedBags != numBags[flight]) {
                        if (bcp.goCollectABag(identifier)) {            //Collect a bag
                            numOfCollectedBags += 1;

                        } else {
                            bro.reportMissingBags(numBags[flight] - numOfCollectedBags, identifier);    //or reports missing bags
                            break;
                        }
                    }
                    ate.goHome(flight, identifier);                    //Goes Home
                    break;
            }
        }
    }
}