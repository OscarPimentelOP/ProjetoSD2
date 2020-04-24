/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package AuxTools;

import java.util.Date;

/**
 * This class has stored all the simulation parameters
 * needed to run the program.
 */

public class SimulatorParam {

    /**
     * Number of flights
     */
    public static final int NUM_FLIGHTS = 5;

    /**
     * Number of passengers
     */
    public static final int NUM_PASSANGERS = 6;

    /**
     * Number of maximum pieces of luggage in the plane hold
     */
    public static final int MAX_NUM_OF_BAGS = 2;

    /**
     * Transfer bus capacity
     */
    public static final int BUS_CAPACITY = 3;

    /**
     * time that the transfer bus waits for passengers at the arrival terminal in milliseconds
     */
    public static final int TIMEQUANTUM = 10;


    /**
     * Probability of passenger having taking 0 bags in %
     */
    public static final int PROB_OF_0_BAGS = 25;


    /**
     * Probability of passenger having taking 1 bags in %
     */
    public static final int PROB_OF_1_BAGS = 25;


    /**
     * Probability of passenger having taking 2 bags in %
     */
    public static final int PROB_OF_2_BAGS = 50;


    /**
     * Probability of 0 bags getting lost in %
     */
    public static final int PROB_LOSE_0_BAGS = 85;


    /**
     * Probability of 1 bags getting lost in %
     */
    public static final int PROB_LOSE_1_BAGS = 10;


    /**
     * Probability of 2 bags getting lost in %
     */
    public static final int PROB_LOSE_2_BAGS = 5;


    /**
     * Log file name to store the status of the program
     */
    public static final String fileName = "log" + new Date().toString().replace(' ', '_').replace(':', '_') + ".txt";

    

   

    /**
    * Arrival lounge host name
	* @serial arrivalLoungeHostName
    */
    public static final String  arrivalLoungeHostName = " ";

    /**
    * Arrival lounge port number
	* @serial arrivalLoungePort
    */
    public static final int arrivalLoungePort = 4000 ;

    /**
    * Arrival terminal exit host name
	* @serial arrivalTerminalExitHostName
    */
    public static final String  arrivalTerminalExitHostName = " ";

    /**
    * Arrival terminal exit port number
	* @serial arrivalTerminalExitPort
    */
    public static final int arrivalTerminalExitPort = 4000 ;

    /**
    * Arrival terminal transfer quay host name
	* @serial arrivalTerminalTransferQuayHostName
    */
    public static final String  arrivalTerminalTransferQuayHostName = " ";

    /**
    * Arrival terminal transfer quay port number
	* @serial arrivalTerminalTransferQuayPort
    */
    public static final int arrivalTerminalTransferQuayPort = 4000 ;

    /**
    * Baggage collection point host name
	* @serial baggageCollectionPointHostName
    */
    public static final String  baggageCollectionPointHostName = " ";

    /**
    * Baggage collection point port number
	* @serial baggageCollectionPointPort
    */
    public static final int baggageCollectionPointPort = 4000 ;

    /**
    * Baggage reclaim office host name
	* @serial baggageReclaimOfficeHostName
    */
    public static final String  baggageReclaimOfficeHostName = " ";

    /**
    * Baggage reclaim office port number
	* @serial baggageReclaimOfficePort
    */
    public static final int baggageReclaimOfficePort = 4000 ;

    /**
    * Departure terminal exit host name
	* @serial departureTerminalExitHostName
    */
    public static final String  departureTerminalEntranceHostName = " ";

    /**
    * Departure terminal exit port number
	* @serial departureTerminalExitPort
    */
    public static final int departureTerminalEntrancePort = 4000 ;

    /**
    * Departure terminal transfer quay host name
	* @serial departureTerminalTransferQuayHostName
    */
    public static final String  departureTerminalTransferQuayHostName = " ";

    /**
    * Departure terminal tranfer quay
	* @serial departureTerminalTransferQuayPort
    */
    public static final int departureTerminalTransferQuayPort = 4000 ;
    
     /**
    * Temporary storage area host name
	* @serial temporaryStorageAreaHostName
    */
    public static final String  temporaryStorageAreaHostName = " ";

    /**
    * Temporary storage area port number
	* @serial temporaryStorageAreaPort
    */
    public static final int temporaryStorageAreaPort = 4000 ;




}