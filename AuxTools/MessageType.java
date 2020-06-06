/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package AuxTools;


/**
 * This enumerate data type specifies the various messages that the clients and servers exchange between them in a Distributed solution
 * for the Airport Problem . Each message is tied to a function or an action crucial for the implementation.
 */
public enum MessageType {
	/**
	  * Initialization parameter
	  *    @serialField NO_MESSAGE
	  */

	NO_MESSAGE,
	

	/**
	  * The Passenger starts his actions upon arriving at the Arrival Lounge (client solicitation)
	  */
	WHATSHOULDIDO,
	

	/**
	  * The Passenger goes home (response).
	  */
	GOHOME,
	
	/**
	  * The Passenger goes to collect a bag (response).
	  */
	GOCOLLECTABAG,
	
	
	/**
	  * The Passenger takes the bus (response).
	  */
	TAKEABUS,
	

	/**
	  * The Porter wants to know if his day of work has ended (client solicitation).
	  */
	TAKEAREST, 

	/**
	  * The end of the day has come for the Porter (server response).
	  */
	ENDPORTER,
	
	
	/**
	  * The Porter must keep working, as the day has not ended yet (server response).
	  */
	KEEPWORKINGPORTER,
	
	
	/**
	  * The porter tries to get a bag (solicitation).
	  */
	TRYTOCOLLECTABAG,
	
	/**
	  * There are no more bags at the plane's hold (response).
	  */
	NOMOREBAGSATPLANEHOLD,
	

	/**
	  * The Porter still has bags to collect  (response).
	  */
	BAGTOCOLLECT,
	
	
	/**
	  * The porter notifies that there are no more bags to collect (solicitation).
	  */
	NOMOREBAGSTOCOLLECT,
	
	
	/**
	  * A message has been successly received and an acknowledge is sent (response).
	  */
	ACK,
	

	/**
	  * The Passenger wants to go home (solicitation).
	  */
	GOINGHOME,
	
	
	/**
	  * The Passenger wants to take a bus (solicitation).
	  */
	TAKINGABUS,
	
	
	/**
	  * The Passenger wants to enter the bus (solicitation).
	  */
	ENTERINGTHEBUS,
	
	
	/**
	  * The bus driver checks if he has more work to do (solicitation).
	  */
	HASDAYSWORKENDED,
	

	/**
	  * The Bus Driver work has ended (response).
	  */
	ENDBUSDRIVER,
	
	
	/**
	  * The Bus Driver needs to keep working (response).
	  */
	KEEPWORKINGBUSDRIVER,
	
	
	/**
	  * The Bus Driver tells the passengers they can enter, announcing the bus (solicitation).
	  */
	ANNOUNCEBUS,
	

	/**
	  * The Bus Driver tells that he has arrived at the arrival terminal (solicitation).
	  */
	PARKATARRIVAL,
	

	/**
	  * The Porter carries the bag for the baggage collection point (solicitation).
	  */
	CARRYBAGTOBAGPOINT,
	
	
	/**
	  * The Passenger goes to collect a bag at the convoy belt (solicitation).
	  */
	GOINGCOLLECTABAG,
	
	
	/**
	  * The Passenger has his bag at the convoy belt and takes it (response).
	  */
	BAGOK,
	
	
	/**
	  * The Passenger doesn't find his bag at the convoy belt (response).
	  */
	BAGNOTOK,
	
	
	/**
	  * The Passenger wants to report a missing bag (solicitation).
	  */
	REPORTBAG,
	
	
	/**
	  * The Passenger wants to prepare his next leg (solicitation).
	  */
	PREPARINGNEXTLEG,
	
	/**
	  * The Passenger wants to leave the bus (solicitation).
	  */
	LEAVETHEBUS,
	
	/**
	  * The Bus driver parks the bus at the departure terminal transfer quay (solicitation).
	  */
	PARKATDEPARTURE,
	
	/**
	  * The Bus Driver changes his state (solicitation).
	  */
	SETBUSDRIVERSTATE,
	
	/**
	  * The carries the bag to the Temporary storage area (solicitation).
	  */
	CARRYBAGTOTEMPSTORE,
	
	/**
	  * Bags and Trip States info sent to the mainPassenger (solicitation).
	  */
	SENDPARAMS,
	
	/**
	  * The Bags and Trip States info has been correctly received  (response).
	  */
	SENDPARAMSACK,
	
	/**
	  * Message from Arrival Lounge to the Baggage Collection Point to inform that there are no more bags (solicitation).
	  */
	SETMOREBAGS,
	
	/**
	  * Passenger wakes up the passengers from the departure (solicitation).
	  */
	WAKEUPALLD,
	 
	/**
	  * Passenger sets it's time to wake up at the departure (solicitation).
	  */
	SETTIMETOWAKEUPTOFALSED,
	
	/**
	  * Passenger informs the Porter that his day has ended (solicitation).
	  */
	SETENDOFWORKPORTER,
	
	/**
	  * The Passenger informs the Bus Driver his day of work has ended (solicitation).
	  */
	SETENDOFWORKBUSDRIVER,
	
	/**
	  * Increase the number of passengers at the end when one reaches (solicitation).
	  */
	INCCNTPASSENGERSEND,
	
	/**
	  * Solitication to receive the number of passengers that have made it to the very end (solicitation).
	  */
	GETCNTPASSENGERSEND,
	
	/**
	  * Send the number of passengers that made it to the very end (response).
	  */
	SENDCNTPASSENGERSEND,
	
	/**
	  * Decrease the number of passengers at the very end (solicitation).
	  */
	DECCNTPASSENGERSEND,
	
	/**
	  * The Passenger wakes up the other passengers at the arrival (solicitation).
	  */
	WAKEUPALLA,
	 
	/**
	  * The Passenger sets it's time to wake up at the arrival (solicitation).
	  */
	SETTIMETOWAKEUPTOFALSEA,
	
	/**
	  * The Passenger wants to leave the bus (solicitation).
	  */
	READFROMBUS,
	
	/**
	  * Decrease the number of passengers in the bus when one leaves (solicitation).
	  */
	DECCNTPASSENGERSINBUS,
	
	/**
	  * Solicitation to get the number of passengers currently inside the bus (solicitation).
	  */
	GETCNTPASSENGERSINBUS,
	
	/**
	  * Send the number of passengers that arrived inside the bus (response).
	  */
	SENDCNTPASSENGERSINBUS, 

	/**
	  * Sets the Porter state.
	  */
	SETPORTERSTATE, 
	
	/**
	  * Decrease the number of bags currently at the plane's hold when one is collected.
	  */
	DECNUMBAGSATPLANEHOLD, 
	
	/**
	  * Sets the flight number.
	  */
	SETTINGFLIGHTNUMBER, 
	
	/**
	  * Sets the number of bags currently at the plane's hold.
	  */
	SETTINGNUMOFBAGSATPLANEHOLD, 
	
	/**
	  * ????????????????
	  */
	SETTINGPASSANGERSONTRANSIT, 
	
	/**
	  * Sets the passengers destiny (?)
	  */
	SETTINGPASSANGERDEST, 
	
	/**
	  * Sets the number of bags at the beggining at the operations.
	  */
	SETNUMOFBAGSATTHEBEGINNING, 
	
	/**
	  * Sets a state for a passenger. (?)
	  */
	SETTINGPASSENGERSTATE, 
	
	/**
	  * Sets the passenger to have that airport at final destination.
	  */
	SETTINGPASSANGERFINALDESTINATION, 
	
	/**
	  * Sets the number of passengers on the queue waiting for a bus.
	  */
	SETTINGPASSANGERSONTHEQUEUE, 
	
	/**
	  * Sets the number of passengers on the bus.
	  */
	SETTINGPASSANGERSONTHEBUS, 
	
	/**
	  * Sets the number of bags on the convoy belt.
	  */
	SETTINGNUMOFBAGSINTHECB, 
	
	/**
	  * Sets the number of bags that have been collected.
	  */
	SETTINGNUMOFBAGSCOLLECTED, 
	
	/**
	  * Sets the number of bags that have been lost.
	  */
	SETTINGLOSTBAGS, 
	
	/**
	  * Sets the number of bags currently at the temporary storage area. 
	  */
	SETTINGNUMBAGSTEMPAREA,
	
	/**
	  * Shuts down the server.
	  */
	SHUTDOWN,
}
