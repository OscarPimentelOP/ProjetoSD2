/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package serverSide.sharedRegions;

import AuxTools.SharedException;
import clientSide.Entities.PassengerState;
import clientSide.Stubs.ArrivalLoungeStub;
import clientSide.Stubs.ArrivalTerminalExitStub;
import clientSide.Stubs.ArrivalTerminalTransferQuayStub;
import clientSide.Stubs.RepoStub;
import serverSide.main.mainDepartureTerminalEntrance;
import AuxTools.SimulatorParam;
import java.util.Random;
/**
 * This class implements the Departure Terminal Entrance shared region.
 * The passengers can go prepare their next leg if they are in transit.
 */

public class DepartureTerminalEntrance {
	Random rand = new Random();
	
    /**
     * The repository's stub, to store the program status
     */
    private RepoStub repo;

    /**
     * Arrival terminal transfer quay's shared region stub
     */
    private ArrivalTerminalTransferQuayStub attq;

    /**
     * Arrival terminal exit's shared region stub
     */
    private ArrivalTerminalExitStub ate;

    /**
     * Arrival lounge's shared region stub
     */
    private ArrivalLoungeStub al;

    
    private boolean timeToWakeUp;

    /**
     * Departure Terminal Exit's instantiation
     *
     * @param al   -> arrival lounge stub
     * @param attq -> arrival terminal transfer quay stub
     * @param repo -> repository of information stub
     */
    public DepartureTerminalEntrance(ArrivalLoungeStub al, ArrivalTerminalTransferQuayStub attq, RepoStub repo) {
        this.repo = repo;
        this.al = al;
        this.attq = attq;
        this.timeToWakeUp = false;
    }

    //Passenger functions

    /**
     * If the passenger is in transit, it must prepare his next leg.
     * This changes the state to entering departure terminal.
     * He waits until every other passenger arrives at the end.
     *
     * @param flight -> the flight number
     * @throws SharedException if the flight number is higher than the number of flights parameter defined in the parameters file.
     */
    public synchronized void prepareNextLeg(int flight, int id) throws SharedException {
        try {
            if (flight + 1 > SimulatorParam.NUM_FLIGHTS)                         /* check for proper parameter range */
                throw new SharedException("Flight cannot exceed the defined parameter for number of flights: " + flight + ".");
        } catch (SharedException e) {
            System.out.println("Thread " + ((Thread) Thread.currentThread()).getName() + "terminated.");
            System.out.println("Error on prepareNextLeg()" + e.getMessage());
            System.exit(1);
        }
        repo.setPassengerState(id, PassengerState.ENTERING_THE_DEPARTURE_TERMINAL);
        ate.incCntPassengersEnd();
        if (ate.getCntPassengersEnd() == SimulatorParam.NUM_PASSANGERS) {
            ate.wakeUpAll();
            this.wakeUpAll();
        }
        while (!this.timeToWakeUp) {
            try {
                wait();
            } catch (InterruptedException e) {

            }
        }
        ate.decCntPassengersEnd();
        if (ate.getCntPassengersEnd() == 0) {
        	//Waiting for porter and bus driver to fall asleep before changing the passenger state to NO_STATE
        	while(ArrivalLounge.b)
        	try {
                wait(20);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            this.timeToWakeUp = false;
            ate.setTimeToWakeUpToFalse();
            if (flight + 1 == SimulatorParam.NUM_FLIGHTS) {
                al.setEndOfWork();
                attq.setEndOfWork();
            }
        }
        repo.setPassengerState(id, PassengerState.NO_STATE);
    }

    /**
     * Sets the arrival terminal exit shared region
     *
     * @param ate -> the arrival terminal to set
     */
    public synchronized void setArrivalExit(ArrivalTerminalExitStub ate) {
        this.ate = ate;
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
     * Shuts the server increasing the number of terminated regions
     */
    public synchronized void shutServer() {
    	mainDepartureTerminalEntrance.terminated = mainDepartureTerminalEntrance.terminated + 1;
    }
}
