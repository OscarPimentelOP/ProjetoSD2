/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package clientSide.Entities;

public enum BusDriverState {
    /**
     * double blocking state (initial / final state)
     * the driver is waken up the first time by the operation takeABus of the passenger who arrives at
     * the transfer terminal and finds out her place in the waiting queue equals the bus capacity, or
     * when the departure time has been reached (transition will only occurs if there is at least one
     * passenger forming the queue); the driver is waken up the second time by the operation enter
     * TheBus of the last passenger to enter the bus
     */
    PARKING_AT_THE_ARRIVAL_TERMINAL,

    /**
     * transition state
     */
    DRIVING_FORWARD,
    DRIVING_BACKWARD,

    /**
     * blocking state
     * the driver is waken up by the operation leaveTheBus of the last passenger to exit the bus
     */
    PARKING_AT_THE_DEPARTURE_TERMINAL


}