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
	  * The Passenger goes home (server response).
	  */
	GOHOME,
	
	/**
	  * The Passenger goes to collect a bag (server response).
	  */
	GOCOLLECTABAG,
	
	
	/**
	  * The Passenger takes the bus (server response).
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
	  * The porter tries to get a bag (client solicitation).
	  */
	TRYTOCOLLECTABAG,
	
	/**
	  * There are no more bags at the plane's hold (server response).
	  */
	NOMOREBAGSATPLANEHOLD,
	

	/**
	  * The Porter still has bags to collect  (server response).
	  */
	BAGTOCOLLECT,
	
	
	/**
	  * The porter notifies that there are no more bags to collect (client solicitation).
	  */
	NOMOREBAGSTOCOLLECT,
	
	
	/**
	  * A message has been successly received and an acknowledge is sent (server response).
	  */
	ACK,
	

	/**
	  * The Passenger wants to go home (client solicitation).
	  */
	GOINGHOME,
	
	
	/**
	  * The Passenger wants to take a bus (client solicitation).
	  */
	TAKINGABUS,
	
	
	/**
	  * The Passenger wants to enter the bus (client solicitation).
	  */
	ENTERINGTHEBUS,
	
	
	/**
	  * The bus driver checks if he has more work to do (client solicitation).
	  */
	HASDAYSWORKENDED,
	

	/**
	  * The Bus Driver work has ended (server response).
	  */
	ENDBUSDRIVER,
	
	
	/**
	  * The Bus Driver needs to keep working (server response).
	  */
	KEEPWORKINGBUSDRIVER,
	
	
	/**
	  * The Bus Driver tells the passengers they can enter, announcing the bus (client solicitation).
	  */
	ANNOUNCEBUS,
	

	/**
	  * The Bus Driver tells that he has arrived at the arrival terminal (client solicitation).
	  */
	PARKATARRIVAL,
	

	/**
	  * The Porter carries the bag for the baggage collection point (client solicitation).
	  */
	CARRYBAGTOBAGPOINT,
	
	
	/**
	  * The Passenger goes to collect a bag at the convoy belt (client solicitation).
	  */
	GOINGCOLLECTABAG,
	
	
	/**
	  * The Passenger has his bag at the convoy belt and takes it (server response).
	  */
	BAGOK,
	
	
	/**
	  * The Passenger doesn't find his bag at the convoy belt (server response).
	  */
	BAGNOTOK,
	
	
	/**
	  * The Passenger wants to report a missing bag (client solicitation).
	  */
	REPORTBAG,
	
	
	/**
	  * The Passenger wants to prepare his next leg (client solicitation).
	  */
	PREPARINGNEXTLEG,
	
	/**
	  * The Passenger wants to leave the bus (client solicitation).
	  */
	LEAVETHEBUS,
	
	/**
	  * The Bus driver parks the bus at the departure terminal transfer quay (client solicitation).
	  */
	PARKATDEPARTURE,
	
	/**
	  * The Bus Driver changes his state (client solicitation).
	  */
	SETBUSDRIVERSTATE,
	
	/**
	  * The carries the bag to the Temporary storage area (client solicitation).
	  */
	CARRYBAGTOTEMPSTORE,
	
	/**
	  * Bags and Trip States info sent to the mainPassenger (client solicitation).
	  */
	SENDPARAMS,
	
	/**
	  * The Bags and Trip States info has been correctly received  (server response).
	  */
	SENDPARAMSACK,
	
	/**
	  * Message from Arrival Lounge to the Baggage Collection Point to inform that there are no more bags (client solicitation).
	  */
	SETMOREBAGS,
	
	/**
	  * Passenger wakes up the passengers from the departure (client solicitation).
	  */
	WAKEUPALLD,
	 
	/**
	  * Passenger sets it's time to wake up at the departure (client solicitation).
	  */
	SETTIMETOWAKEUPTOFALSED,
	
	/**
	  * Passenger informs the Porter that his day has ended (client solicitation).
	  */
	SETENDOFWORKPORTER,
	
	/**
	  * The Passenger informs the Bus Driver his day of work has ended (client solicitation).
	  */
	SETENDOFWORKBUSDRIVER,
	
	/**
	  * Increase the number of passengers at the end when one reaches (client solicitation).
	  */
	INCCNTPASSENGERSEND,
	
	/**
	  * Solitication to receive the number of passengers that have made it to the very end (client solicitation).
	  */
	GETCNTPASSENGERSEND,
	
	/**
	  * Send the number of passengers that made it to the very end (server response).
	  */
	SENDCNTPASSENGERSEND,
	
	/**
	  * Decrease the number of passengers at the very end (client solicitation).
	  */
	DECCNTPASSENGERSEND,
	
	/**
	  * The Passenger wakes up the other passengers at the arrival (client solicitation).
	  */
	WAKEUPALLA,
	 
	/**
	  * The Passenger sets it's time to wake up at the arrival (client solicitation).
	  */
	SETTIMETOWAKEUPTOFALSEA,
	
	/**
	  * The Passenger wants to leave the bus (client solicitation).
	  */
	READFROMBUS,
	
	/**
	  * Decrease the number of passengers in the bus when one leaves (client solicitation).
	  */
	DECCNTPASSENGERSINBUS,
	
	/**
	  * Solicitation to get the number of passengers currently inside the bus (client solicitation).
	  */
	GETCNTPASSENGERSINBUS,
	
	/**
	  * Send the number of passengers that arrived inside the bus (server response).
	  */
	SENDCNTPASSENGERSINBUS, 

	/**
	  * Sets the Porter state (client solicitation). 
	  */
	SETPORTERSTATE, 
	
	/**
	  * Decrease the number of bags currently at the plane's hold when one is collected (client solicitation).
	  */
	DECNUMBAGSATPLANEHOLD, 
	
	/**
	  * Sets the flight number (client solicitation).
	  */
	SETTINGFLIGHTNUMBER, 
	
	/**
	  * Sets the number of bags currently at the plane's hold (client solicitation).
	  */
	SETTINGNUMOFBAGSATPLANEHOLD, 
	
	/**
	  * Sets the number of passengers that do not have the airport as their final destination (client solicitation).
	  */
	SETTINGPASSANGERSONTRANSIT, 
	
	/**
	  * Sets the passengers destiny (client solicitation)
	  */
	SETTINGPASSANGERDEST, 
	
	/**
	  * Sets the number of bags at the beggining at the operations (client solicitation).
	  */
	SETNUMOFBAGSATTHEBEGINNING, 
	
	/**
	  * Sets a state for a passenger. (client solicitation)
	  */
	SETTINGPASSENGERSTATE, 
	
	/**
	  * Sets the number of passengers that have the airport as their final destination (client solicitation).
	  */
	SETTINGPASSANGERFINALDESTINATION, 
	
	/**
	  * Sets the number of passengers on the queue waiting for a bus (client solicitation).
	  */
	SETTINGPASSANGERSONTHEQUEUE, 
	
	/**
	  * Sets the number of passengers on the bus (client solicitation).
	  */
	SETTINGPASSANGERSONTHEBUS, 
	
	/**
	  * Sets the number of bags on the convoy belt (client solicitation).
	  */
	SETTINGNUMOFBAGSINTHECB, 
	
	/**
	  * Sets the number of bags that have been collected (client solicitation).
	  */
	SETTINGNUMOFBAGSCOLLECTED, 
	
	/**
	  * Sets the number of bags that have been lost (client solicitation).
	  */
	SETTINGLOSTBAGS, 
	
	/**
	  * Sets the number of bags currently at the temporary storage area (client solicitation). 
	  */
	SETTINGNUMBAGSTEMPAREA,
	
	/**
	  * Shuts down the server (client solicitation).
	  */
	SHUTDOWN,
}
