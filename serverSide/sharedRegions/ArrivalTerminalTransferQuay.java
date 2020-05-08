/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package serverSide.sharedRegions;

import clientSide.Entities.PassengerState;
import clientSide.Stubs.RepoStub;
import serverSide.main.mainArrivalTerminalTransferQuay;
import AuxTools.SimulatorParam;
import clientSide.Entities.BusDriverState;
import AuxTools.MemFIFO;
import AuxTools.SharedException;
import AuxTools.MemException;

/**
 * This class implements the Arrival Terminal Transfer Quay shared region.
 * The passengers can take (and enter) a bus if they are in transit
 * after the bus driver announces the bus for them.
 */

public class ArrivalTerminalTransferQuay {


    /**
     * Warns the passengers the they can enter on the bus
     */
    private boolean announced;

    /**
     * Warns the bus driver that he can go rest
     * Last passenger of the last flight accuses in goHome function or in prepareNextLeg function
     */
    private boolean endOfOperations;


    /**
     * Count of the number of passengers waiting to enter the bus
     */
    private int cntPassengersInQueue;


    /**
     * Count of the number of passengers that entered in the bus
     */
    protected int cntPassengersInBus;


    /**
     * Queue with the passengers waiting for the bus
     */
    private MemFIFO<Thread> waitingForBus;


    /**
     * Queue with the passengers in the bus
     */
    protected MemFIFO<Thread> inTheBus;

    /**
     * The repository, to store the program status
     */
    private RepoStub repo;


    /**
     * Bus driver waiting for passengers
     */
    private boolean busDriveSleep;

    /**
     * Arrival Terminal Transfer Quay's instantiation
     *
     * @param repo -> repository of information
     */
    public ArrivalTerminalTransferQuay(RepoStub repo) {
        this.repo = repo;
        this.busDriveSleep = true;
        this.cntPassengersInBus = 0;
        this.cntPassengersInQueue = 0;
        this.endOfOperations = false;
        this.announced = false;
        try {
            waitingForBus = new MemFIFO<Thread>(new Thread[SimulatorParam.NUM_PASSANGERS]);
        } catch (MemException e) {
        }

        try {
            inTheBus = new MemFIFO<Thread>(new Thread[SimulatorParam.BUS_CAPACITY]);
        } catch (MemException e) {
        }
    }

    //Passenger functions

    /**
     * The passenger takes a bus and it gets
     * into the queue with the passengers waiting for the bus
     */
    public synchronized void takeABus(int id) {
        repo.setPassengerState(id, PassengerState.AT_THE_ARRIVAL_TRANSFER_TERMINAL);
        try {
            this.waitingForBus.write((Thread)Thread.currentThread());
            repo.setPassengersOnTheQueue(cntPassengersInQueue, id);
            try {
                if (cntPassengersInQueue > SimulatorParam.NUM_PASSANGERS)                         /* check for proper parameter range */
                    throw new SharedException("The number of passengers in queue cannot be higher than the defined on the parameter file " + cntPassengersInQueue + ".");
            } catch (SharedException e) {
                System.out.println("Thread " + ((Thread) Thread.currentThread()).getName() + "terminated.");
                System.out.println("Error in takeABus()" + e.getMessage());
                System.exit(1);
            }
            cntPassengersInQueue++;
            if (!announced) {
                notifyAll();
            }
        } catch (MemException e) {
            System.out.println("The queue bounds were violated");
            System.out.println("Error in takeABus()");
        }
    }


    /**
     * The passengers are blocked until the bus driver announces the bus.
     * A passenger enters the bus, moving from the waiting queue to the bus.
     * They can't get in if the bus is full and keep waiting.
     *
     * @throws SharedException if the number of passengers in queue is negative or if it is higher than the queue's capacity
     */
    public synchronized void enterTheBus(int id) throws SharedException {
        while (!announced || this.getCntPassengersInBus() == SimulatorParam.BUS_CAPACITY) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        try {
            this.waitingForBus.read();
            this.cntPassengersInQueue--;
            try {
                if (cntPassengersInQueue < 0)                         /* check for proper parameter range */
                    throw new SharedException("The number of passengers in queue cannot be negative " + cntPassengersInQueue + ".");
            } catch (SharedException e) {
                System.out.println("Thread " + ((Thread) Thread.currentThread()).getName() + "terminated.");
                System.out.println("Error in carryItToAppropriateStore()" + e.getMessage());
                System.exit(1);
            }
            this.inTheBus.write((Thread) Thread.currentThread());
            repo.setPassengersOnTheQueue(this.cntPassengersInQueue, -1);
            repo.setPassangersOnTheBus(this.getCntPassengersInBus(), id);
            try {
                if (cntPassengersInBus > SimulatorParam.BUS_CAPACITY)                         /* check for proper parameter range */
                    throw new SharedException("The number of passengers in bus cannot be higher than the bus capacity defined at the parameters file. " + cntPassengersInQueue + ".");
            } catch (SharedException e) {
                System.out.println("Thread " + ((Thread) Thread.currentThread()).getName() + "terminated.");
                System.out.println("Error while incrementing the number of passengers in the bus" + e.getMessage());
                System.exit(1);
            }
            this.incCntPassengersInBus();
        } catch (MemException e) {
            System.out.println("The queue bounds were violated");
            System.out.println("Error in enterTheBus()" + e.getMessage());
        }
        if (!this.busDriveSleep)
            notifyAll();
        else if (this.getCntPassengersInBus() == SimulatorParam.BUS_CAPACITY) {
            notifyAll();
        }
        repo.setPassengerState(id, PassengerState.TERMINAL_TRANSFER);

    }

    //Bus driver functions


    /**
     * The bus driver ends his job or returns to work
     *
     * @return 'W' if he is going to work again
     */
    public synchronized char hasDaysWorkEnded() {
        while (!this.endOfOperations && cntPassengersInQueue == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        if (this.endOfOperations) {
            return 'E';
        } else return 'W';
    }


    /**
     * The bus driver will unlock the passengers that are waiting to enter the bus.
     * If the end of the day isn't reached, it blocks the bus driver until the time quantum terminates or the bus gets full
     */
    public synchronized void announcingBusBoarding() {
        announced = true;
        notifyAll();
        //Blocks until reaches 10 passengers or reaches time quantum
        while (this.busDriveSleep) {
            try {
                wait(SimulatorParam.TIMEQUANTUM);
                this.busDriveSleep = false;
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        while (this.getCntPassengersInBus() < 1) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        this.busDriveSleep = true;
        this.announced = false;
    }

    /**
     * The bus driver parks the bus at the arrival terminal
     */
    public synchronized void parkTheBus() {
        repo.setBusDriverState(BusDriverState.PARKING_AT_THE_ARRIVAL_TERMINAL);
    }

    /**
     * Sets the end of work for the bus driver
     */
    public synchronized void setEndOfWord() {
        this.endOfOperations = true;
        notifyAll();
    }

    /**
     * Increments the number of passengers in the bus when a passenger gets in
     * to help managing the capacity
     */
    public synchronized void incCntPassengersInBus() {
        this.cntPassengersInBus++;
    }

    /**
     * Decrements the number of passengers in the bus when a passenger gets out
     * to help managing the capacity
     */
    public synchronized void decCntPassengersInBus() {
        this.cntPassengersInBus--;
    }

    /**
     * Returns the number of passengers in the bus
     *
     * @return the number of passengers inside the bus
     */
    public synchronized int getCntPassengersInBus() {
        return this.cntPassengersInBus;
    }

    public synchronized void readFromBus() {
        try {
            this.inTheBus.read();
        } catch (MemException e) {
            System.out.println(e);
        }
    }
    
    public synchronized void shutServer() {
    	mainArrivalTerminalTransferQuay.terminated = mainArrivalTerminalTransferQuay.terminated + 1;
    }
}
