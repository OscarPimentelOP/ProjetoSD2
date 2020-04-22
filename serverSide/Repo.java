/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package SharedRegions;

import java.io.File;

import Entities.PorterState;
import Entities.PassengerState;
import Entities.BusDriverState;

import java.io.PrintWriter;
import java.io.FileNotFoundException;

import Main.SimulatorParam;

/**
 * This class implements the General Repository of Information shared region.
 * This keeps the program's internal state and reports it to a log file
 * for visualizing and debugging goals.
 */

public class Repo {

    /**
     * The file to store the information.
     */
    private File file;
    private PrintWriter pw;
    /**
     * Porter states abbreviations, to be displayed as the Porter's state changes.
     */
    private final String[] porterStates = {"WPTL", "APLH", "ALCB", "ASTR"};

    /**
     * Passenger states abbreviations, to be displayed as the Passenger's state changes.
     */
    private final String[] passengerStates = {"WSD", "LCP", "BRO", "DTT", "EAT", "ATT", "TRT", "EDT", "---"};

    /**
     * Bus Driver states abbreviations, to be displayed as the Bus Driver's state changes.
     */
    private final String[] busDriverStates = {"PKAT", "DRFW", "DRBW", "PKDT"};

    /**
     * Porter current state.
     */
    private PorterState porterSt;

    /**
     * Passenger states.
     */
    private PassengerState[] passengerSt;

    /**
     * Bus Driver current state.
     */
    private BusDriverState busDriverSt;

    /**
     * Current flight number.
     */
    private int flightNum;


    /**
     * Number of bags at currently at the plane's hold
     */
    private int numOfBagsAtPlaneHold;


    /**
     * Number of bags in the convoy belt
     */
    private int numOfBagsInTheConvoyBelt;


    /**
     * Number of bags in the temporary storage area
     */
    private int numOfBagsInTheTempArea;


    /**
     * Passengers (identified by their id) on the queue waiting for the bus
     */
    private int[] passengersOnTheQueue;


    /**
     * Passengers (identified by their id) on the bus
     */
    private int[] passangersOnTheBus;


    /**
     * Destination of the each passenger
     * TRT (in transit) / FDT (has this airport as his final destination)
     */
    private String[] passengerDestination;


    /**
     * Number of bags carried at the start of his journey
     */
    private int[] numOfBagsAtTheBegining;


    /**
     * Number of bags that the passenger has currently collected
     */
    private int[] numOfBagsCollected;


    /**
     * Number of passengers which have this airport as their final destination
     */
    private int passengersFinalDest;


    /**
     * Number of passengers in transit
     */
    private int passengersTransit;


    /**
     * Number of bags that should have been transported in the the planes hold
     */
    private int totalBags;


    /**
     * Number of bags that were lost
     */
    private int lostBags;

    /**
     * Repository's instantiation
     *
     * @param al -> arrival lounge
     * @throws FileNotFoundException in case the file is not found
     */
    public Repo() throws FileNotFoundException {
        file = new File(SimulatorParam.fileName);
        pw = new PrintWriter(file);

        flightNum = 1;
        numOfBagsAtPlaneHold = 0;
        numOfBagsInTheConvoyBelt = 0;
        numOfBagsInTheTempArea = 0;
        passengerSt = new PassengerState[SimulatorParam.NUM_PASSANGERS];
        passengersOnTheQueue = new int[SimulatorParam.NUM_PASSANGERS];
        passangersOnTheBus = new int[SimulatorParam.BUS_CAPACITY];
        passengerDestination = new String[SimulatorParam.NUM_PASSANGERS];
        numOfBagsAtTheBegining = new int[SimulatorParam.NUM_PASSANGERS];
        numOfBagsCollected = new int[SimulatorParam.NUM_PASSANGERS];
        for (int p = 0; p < SimulatorParam.NUM_PASSANGERS; p++) {
            passengersOnTheQueue[p] = -1;
            passengerDestination[p] = "TRT";
            numOfBagsAtTheBegining[p] = 0;
            numOfBagsCollected[p] = 0;
        }
        for (int p = 0; p < SimulatorParam.BUS_CAPACITY; p++) {
            passangersOnTheBus[p] = -1;
        }
        passengersFinalDest = 0;
        passengersTransit = 0;
        totalBags = 0;
        lostBags = 0;

        //defining initial states for the entities
        porterSt = PorterState.WAITING_FOR_A_PLANE_TO_LAND;
        busDriverSt = BusDriverState.PARKING_AT_THE_ARRIVAL_TERMINAL;
        passengerSt = new PassengerState[SimulatorParam.NUM_PASSANGERS];
        for (int p = 0; p < SimulatorParam.NUM_PASSANGERS; p++) {
            passengerSt[p] = PassengerState.NO_STATE;
        }

        reportInitialStatus();
    }


    /**
     * Prints the head of the log
     */
    private void reportInitialStatus() {
        pw.write("               AIRPORT RHAPSODY - Description of the internal state of the problem\n\n");
        pw.write("PLANE    PORTER                  DRIVER\n");
        pw.write("FN BN  Stat CB SR   Stat  Q1 Q2 Q3 Q4 Q5 Q6  S1 S2 S3\n");
        pw.write("                                                         PASSENGERS\n");
        pw.write("St1 Si1 NR1 NA1 St2 Si2 NR2 NA2 St3 Si3 NR3 NA3 St4 Si4 NR4 NA4 St5 Si5 NR5 NA5 St6 Si6 NR6 NA6\n");
    }


    /**
     * Prints the program's state.
     * Line by line it prints the updates in the states of all entities and values displayed.
     */
    private String reportStatus() {
        String[] Q = new String[SimulatorParam.NUM_PASSANGERS];
        for (int p = 0; p < SimulatorParam.NUM_PASSANGERS; p++) {
            if (this.passengersOnTheQueue[p] < 0) {
                Q[p] = "-";
            } else {
                Q[p] = Integer.toString(this.passengersOnTheQueue[p]);
            }
        }
        String[] S = new String[SimulatorParam.BUS_CAPACITY];
        for (int s = 0; s < SimulatorParam.BUS_CAPACITY; s++) {
            if (this.passangersOnTheBus[s] < 0) {
                S[s] = "-";
            } else {
                S[s] = Integer.toString(this.passangersOnTheBus[s]);
            }
        }
        String lineStatus = "";                              // linha a imprimir
        lineStatus += " " + Integer.toString(this.flightNum) + "  " +
                Integer.toString(this.numOfBagsAtPlaneHold) + "  " +
                this.porterStates[this.porterSt.ordinal()] +
                "  " + Integer.toString(this.numOfBagsInTheConvoyBelt) + "  " +
                Integer.toString(this.numOfBagsInTheTempArea) + "   " +
                this.busDriverStates[this.busDriverSt.ordinal()] + "   " + Q[0] + "  " + Q[1] + "  " + Q[2] +
                "  " + Q[3] + "  " + Q[4] + "  " + Q[5] + "   " + S[0] + "  " + S[1] + "  " + S[2] + "\n";

        for (int p = 0; p < SimulatorParam.NUM_PASSANGERS; p++) {
            if (this.passengerSt[p].ordinal() == 8) {
                lineStatus += this.passengerStates[this.passengerSt[p].ordinal()] + " " +
                        "---" + "  " +
                        "-" + "   " +
                        "-" + "  ";
            } else {
                lineStatus += this.passengerStates[this.passengerSt[p].ordinal()] + " " +
                        this.passengerDestination[p] + "  " +
                        Integer.toString(this.numOfBagsAtTheBegining[p]) + "   " +
                        Integer.toString(this.numOfBagsCollected[p]) + "  ";
            }
        }
        return lineStatus + "\n";
    }


    /**
     * Prints the 4 values on the final report.
     */
    public void reportFinalStatus() {
        pw.write("Final report");
        pw.write("N. of passengers which have this airport as their final destination = " + Integer.toString(this.passengersFinalDest) + "\n");
        pw.write("N. of passengers in transit = " + Integer.toString(this.passengersTransit) + "\n");
        pw.write("N. of bags that should have been transported in the the planes hold = " + Integer.toString(this.totalBags) + "\n");
        pw.write("N. of bags that were lost = " + Integer.toString(this.lostBags) + "\n");
        pw.close();
    }

    /**
     * Sets the Passenger's state. Calls printInfo() to print the updated info.
     *
     * @param id -> the Passenger's ID
     * @param ps -> the state to be set
     */
    public synchronized void setPassengerState(int id, PassengerState ps) {
        if (passengerSt[id] != ps) {
            passengerSt[id] = ps;
            if (ps.ordinal() != 8)
                printInfo();
        }
        if (passengerSt[id].ordinal() == 8) {
            for (int p = 0; p < SimulatorParam.NUM_PASSANGERS; p++) {
                this.numOfBagsCollected[p] = 0;
            }
        }
    }

    /**
     * Sets the Porter's state. Calls printInfo() to print the updated info.
     *
     * @param ps -> the state to be set
     */
    public synchronized void setPorterState(PorterState ps) {
        if (this.porterSt != ps) {
            this.porterSt = ps;
            printInfo();
        }
    }

    /**
     * Sets the Bus Driver's state. Calls printInfo() to print the updated info.
     *
     * @param bds -> the state to be set
     */
    public synchronized void setBusDriverState(BusDriverState bds) {
        if (this.busDriverSt != bds) {
            this.busDriverSt = bds;
            printInfo();
        }
    }

    /**
     * Prints the updated info in the report stats.
     */
    private void printInfo() {
        String infoToPrint = reportStatus();
        System.out.println(infoToPrint);
        pw.write(infoToPrint);
        pw.flush();

    }

    /**
     * Sets and updates the flight number
     *
     * @param flight -> the flight number
     */
    public synchronized void setFlightNumber(int flight) {
        if (flightNum != flight + 1)
            flightNum = flight + 1;
    }

    /**
     * Sets and updates the number of bags at the plane's hold.
     *
     * @param flight               -> the flight number
     * @param numOfBagsAtPlaneHold -> number of bags at the plane's hold to be set.
     */
    public synchronized void setNumOfBagsAtPlaneHold(int flight, int numOfBagsAtPlaneHold) {
        if (this.numOfBagsAtPlaneHold == 0) {
            this.numOfBagsAtPlaneHold = numOfBagsAtPlaneHold;
            this.setTotalBags(numOfBagsAtPlaneHold);
        }
    }

    /**
     * Decrements the number of bags at the plane's hold
     */
    public synchronized void decNumOfBagsAtPlaneHold() {
        this.numOfBagsAtPlaneHold--;
    }

    /**
     * Sets and updates the number of bags at the convoy belt.
     *
     * @param numOfBagsInTheConvoyBelt -> number of bags at the convoy belt to be set.
     */
    public synchronized void setNumOfBagsInTheConvoyBelt(int numOfBagsInTheConvoyBelt) {
        if (this.numOfBagsInTheConvoyBelt != numOfBagsInTheConvoyBelt) {
            this.numOfBagsInTheConvoyBelt = numOfBagsInTheConvoyBelt;
            printInfo();
        }
    }

    /**
     * Sets and updates the number of bags at the temporary storage area.
     *
     * @param numOfBagsInTheTempArea -> number of bags at the temporary storage area to be set.
     */
    public synchronized void setNumOfBagsInTheTempArea(int numOfBagsInTheTempArea) {
        if (this.numOfBagsInTheTempArea != numOfBagsInTheTempArea) {
            this.numOfBagsInTheTempArea = numOfBagsInTheTempArea;
            printInfo();
        }
    }

    /**
     * Sets and updates the passengers on the queue waiting for the bus.
     *
     * @param queueNum    -> position on the queue.
     * @param passengerId -> Passenger's ID to be set.
     */
    public synchronized void setPassengersOnTheQueue(int queueNum, int passengerId) {
        this.passengersOnTheQueue[queueNum] = passengerId;
        if (passengerId != -1)
            printInfo();
    }

    /**
     * Sets and updates the passengers on the bus.
     *
     * @param seatNum     -> bus' seat for the passenger.
     * @param passengerId -> Passenger's ID to be set.
     */
    public synchronized void setPassangersOnTheBus(int seatNum, int passengerId) {
        this.passangersOnTheBus[seatNum] = passengerId;
        printInfo();
    }

    /**
     * Sets and updates the passengers destination.
     *
     * @param passengerId -> Passenger's ID to be set.
     * @param destination -> destination to be set.
     */
    public synchronized void setPassengerDestination(int passengerId, String destination) {
        this.passengerDestination[passengerId] = destination;
    }

    /**
     * Sets and updates the number of bags at the start.
     *
     * @param passengerId -> Passenger's ID to be set.
     * @param numOfBags   -> number of bags to set.
     */
    public synchronized void setNumOfBagsAtTheBegining(int passengerId, int numOfBags) {
        this.numOfBagsAtTheBegining[passengerId] = numOfBags;
    }

    /**
     * Sets and updates the number of bags that a passenger has collected.
     *
     * @param passengerId -> Passenger's ID to be set.
     */
    public synchronized void setNumOfBagsCollected(int passengerId) {
        this.numOfBagsCollected[passengerId]++;
        printInfo();
    }

    /**
     * Sets the passengers final destination.
     */
    public synchronized void setPassengersFinalDest(int passengersFinalDest) {
        this.passengersFinalDest = passengersFinalDest;
    }

    /**
     * Sets the passenger on transit.
     */
    public synchronized void setPassengersTransit(int passengersTransit) {
        this.passengersTransit = passengersTransit;
    }

    /**
     * Sets and updates the total number of bags transported in the the planes hold
     *
     * @param totalBags -> total number of Bags
     */
    public synchronized void setTotalBags(int totalBags) {
        this.totalBags += totalBags;
    }

    /**
     * Sets and updates the amount of lost bags
     *
     * @param lostBags -> number of lost bags.
     */
    public synchronized void setLostBags(int lostBags) {
        this.lostBags = lostBags;
    }

}