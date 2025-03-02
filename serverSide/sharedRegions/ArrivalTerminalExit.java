/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package serverSide.sharedRegions;

import java.util.Random;

import AuxTools.SharedException;
import clientSide.Entities.PassengerState;
import clientSide.Stubs.ArrivalLoungeStub;
import clientSide.Stubs.ArrivalTerminalTransferQuayStub;
import clientSide.Stubs.DepartureTerminalEntranceStub;
import clientSide.Stubs.RepoStub;
import serverSide.main.mainArrivalTerminalExit;
import AuxTools.SimulatorParam;

/**
 * This class implements the Arrival Terminal exit shared region.
 */

public class ArrivalTerminalExit {

	Random rand = new Random();

    /**
     * The repository's stub, to store the program status
     */
    private RepoStub repo;

    /**
     * Arrival lounge's shared region stub
     */
    private ArrivalLoungeStub al;

    /**
     * Arrival terminal transfer quay's shared region stub
     */
    private ArrivalTerminalTransferQuayStub attq;

    /**
     * Departure terminal entrance's shared region stub
     */
    private DepartureTerminalEntranceStub dte;

    /**
     * passengers count at the end 
     */
    private int cntPassengersEnd;

    /**
     * It's time to wake up
     */
    private boolean timeToWakeUp;


    /**
     * Arrival Terminal Exit's instantiation
     *
     * @param al   -> arrival lounge stub
     * @param attq -> arrival terminal transfer quay stub
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
    public synchronized void goHome(int flight,int id) throws SharedException {
        try {
            if (flight + 1 > SimulatorParam.NUM_FLIGHTS)                         /* check for proper parameter range */
                throw new SharedException("Flight cannot exceed the defined parameter for number of flights: " + flight + ".");
        } catch (SharedException e) {
            System.out.println("Thread " + ((Thread) Thread.currentThread()).getName() + "terminated.");
            System.out.println("Error on goHome()" + e.getMessage());
            System.exit(1);
        }
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
        	//Waiting for porter and bus driver to fall asleep before changing the passenger state to NO_STATE
            while(ArrivalLounge.b)
        	try {
                wait(20);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            this.timeToWakeUp = false;
            dte.setTimeToWakeUpToFalse();
            if (flight + 1 == SimulatorParam.NUM_FLIGHTS) {
                al.setEndOfWork();
                attq.setEndOfWork();
            }
        }
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
            wait(rand.nextInt(100)+20);
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
    
    /**
     * Shuts the server increasing the number of terminated regions
     */
    public synchronized void shutServer() {
    	mainArrivalTerminalExit.terminated = mainArrivalTerminalExit.terminated + 1;
    }
}
