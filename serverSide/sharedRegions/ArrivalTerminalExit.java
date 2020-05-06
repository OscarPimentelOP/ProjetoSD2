/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package serverSide.sharedRegions;

import AuxTools.SharedException;
import clientSide.Entities.Passenger;
import clientSide.Entities.PassengerState;
import clientSide.Stubs.ArrivalLoungeStub;
import clientSide.Stubs.ArrivalTerminalTransferQuayStub;
import clientSide.Stubs.DepartureTerminalEntranceStub;
import clientSide.Stubs.RepoStub;
import AuxTools.SimulatorParam;

/**
 * This class implements the Arrival Terminal exit shared region.
 */

public class ArrivalTerminalExit {

    /**
     * The repository, to store the program status
     */
    private RepoStub repo;

    /**
     * Arrival lounge shared region
     */
    private ArrivalLoungeStub al;

    /**
     * Arrival terminal transfer quay shared region
     */
    private ArrivalTerminalTransferQuayStub attq;

    /**
     * Departure terminal entrance shared region
     */
    private DepartureTerminalEntranceStub dte;

    /**
     * passengers count at the end (???)
     */
    private int cntPassengersEnd;

    /**
     * (???)
     */
    private boolean timeToWakeUp;


    /**
     * Arrival Terminal Exit's instantiation
     *
     * @param al   -> arrival lounge
     * @param attq -> arrival terminal transfer quay
     * @param repo -> repository of information
     */
    public ArrivalTerminalExit(ArrivalLoungeStub al, ArrivalTerminalTransferQuayStub attq, RepoStub repo) {
        this.repo = repo;
        this.al = al;
        this.attq = attq;
        this.timeToWakeUp = false;
        this.cntPassengersEnd = 0;
    }

    //Passenger functions

    /**
     * The passenger exits the arrival terminal and goes home.
     *
     * @param flight -> the flight number
     * @throws SharedException if the flight number is higher than the number of flights parameter defined in the parameters file.
     */
    public synchronized void goHome(int flight) throws SharedException {
        try {
            if (flight + 1 > SimulatorParam.NUM_FLIGHTS)                         /* check for proper parameter range */
                throw new SharedException("Flight cannot exceed the defined parameter for number of flights: " + flight + ".");
        } catch (SharedException e) {
            System.out.println("Thread " + ((Thread) Thread.currentThread()).getName() + "terminated.");
            System.out.println("Error on goHome()" + e.getMessage());
            System.exit(1);
        }

        Passenger m = (Passenger) Thread.currentThread();
        m.setPassengerState(PassengerState.EXITING_THE_ARRIVAL_TERMINAL);
        int id = m.getIdentifier();
        repo.setPassengerState(id, PassengerState.EXITING_THE_ARRIVAL_TERMINAL);
        incCntPassengersEnd();
        if (getCntPassengersEnd() == SimulatorParam.NUM_PASSANGERS) {
            dte.wakeUpAll();
            this.wakeUpAll();
        }
        while (!this.timeToWakeUp) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        decCntPassengersEnd();
        if (getCntPassengersEnd() == 0) {
            this.timeToWakeUp = false;
            dte.setTimeToWakeUpToFalse();
            if (flight + 1 == SimulatorParam.NUM_FLIGHTS) {
                al.setEndOfWork();
                attq.setEndOfWord();
            }
        }
        //Waiting for porter and bus driver to fall asleep before changing the passenger state to NO_STATE
        try {
            wait(10);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        m.setPassengerState(PassengerState.NO_STATE);
        repo.setPassengerState(id, PassengerState.NO_STATE);
    }

    /**
     * Sets the departure terminal entrance
     *
     * @param dte -> departure terminal entrance to be set
     */
    public synchronized void setDepartureEntrance(DepartureTerminalEntranceStub dte) {
        this.dte = dte;
    }

    /**
     * Wakes up all the passengers of the terminal
     */
    public synchronized void wakeUpAll() {
        this.timeToWakeUp = true;
        try {
            wait(10);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        notifyAll();
    }

    /**
     * Marks the end of the flight, so then all the passengers are blocked for the next flight
     */
    public synchronized void setTimeToWakeUpToFalse() {
        this.timeToWakeUp = false;
    }

    /**
     * returns the number of passengers in both terminals
     */
    public synchronized int getCntPassengersEnd() {
        return this.cntPassengersEnd;
    }

    /**
     * increments the number of passengers in both terminals: a passenger arrived the terminal
     */
    public synchronized void incCntPassengersEnd() {
        this.cntPassengersEnd = this.cntPassengersEnd + 1;
    }

    /**
     * decrements the number of passengers in both terminals: a passenger has left the terminal
     */
    public synchronized void decCntPassengersEnd() {
        this.cntPassengersEnd = this.cntPassengersEnd - 1;
    }
}
