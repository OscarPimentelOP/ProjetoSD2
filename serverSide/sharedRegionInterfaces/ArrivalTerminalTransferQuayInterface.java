/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package serverSide.sharedRegionInterfaces;

import AuxTools.Message;
import AuxTools.MessageException;
import AuxTools.MessageType;
import AuxTools.SharedException;
import serverSide.Proxys.ArrivalTerminalTransferQuayProxy;
import serverSide.main.mainArrivalTerminalTransferQuay;
import serverSide.sharedRegions.ArrivalTerminalTransferQuay;

/**
 * This class implements the Arrival Terminal Transfer Quay Interface that processes the
 * received messages and based on that received message executes the respective function from the actual
 * Arrival Terminal Transfer Quay. Furthermore, this Interface creates the reply message.
 */

public class ArrivalTerminalTransferQuayInterface {

	/**
	 * Arrival Terminal Transfer Quay shared region
     * @serialField attq
     */
	private ArrivalTerminalTransferQuay attq;
	

	/**
	 * Arrival Terminal Transfer Quay Interface instantiation
     * @param attq Arrival Terminal Trasfer Quay shared region
     */
	public ArrivalTerminalTransferQuayInterface (ArrivalTerminalTransferQuay attq)
	{
		this.attq = attq;
	}
	

	/**
	 * This function receives the incoming message and executes the correct function from the Arrival Terminal Transfer Quay shared region and then
	 * generates the reply message.
     * @param inMessage incoming message from the main
     */
	public Message processAndReply (Message inMessage) throws MessageException{
		 Message outMessage = null;                           // response message
		 
		 //Validate messages
		 switch (inMessage.getType ()) {
		 case TAKINGABUS : break;
		 case ENTERINGTHEBUS : break;
		 case HASDAYSWORKENDED : break;
		 case ANNOUNCEBUS : break;
		 case PARKATARRIVAL : break;
		 case SETENDOFWORKBUSDRIVER: break;
		 case READFROMBUS : break;
	 	 case DECCNTPASSENGERSINBUS: break;
		 case GETCNTPASSENGERSINBUS: break;
		 case SHUTDOWN : break;
		 default : throw new MessageException ("Message type invalid : ", inMessage);
		 }
		 
		 
		 //Process messages
		 switch (inMessage.getType ()) {
		 case TAKINGABUS : attq.takeABus(inMessage.getPassengerID());
						   outMessage = new Message(MessageType.ACK);
						   break;
		 case ENTERINGTHEBUS : try {
								attq.enterTheBus(inMessage.getPassengerID());
								outMessage = new Message(MessageType.ACK);
							} catch (SharedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						   break;
		 case HASDAYSWORKENDED : char a = attq.hasDaysWorkEnded();
		 						 if(a == 'E') {
		 							outMessage = new Message(MessageType.ENDBUSDRIVER);
		 						 }
		 						 else {
		 							outMessage = new Message(MessageType.KEEPWORKINGBUSDRIVER);
		 						 }
		 						 break;
		 case ANNOUNCEBUS : attq.announcingBusBoarding();
							outMessage = new Message(MessageType.ACK);
							break;
		 case PARKATARRIVAL : attq.parkTheBus();
							  outMessage = new Message(MessageType.ACK);
						      break;
		 case SETENDOFWORKBUSDRIVER: attq.setEndOfWord();
									 outMessage = new Message(MessageType.ACK);
									 break;
		 case READFROMBUS : attq.readFromBus();
							outMessage = new Message(MessageType.ACK);
							break;
		 case DECCNTPASSENGERSINBUS: attq.decCntPassengersInBus();
									 outMessage = new Message(MessageType.ACK);
									 break;
		 case GETCNTPASSENGERSINBUS: int cnt = attq.getCntPassengersInBus();
									 outMessage = new Message(MessageType.SENDCNTPASSENGERSINBUS, cnt);
									 break;
		 case SHUTDOWN : attq.shutServer();
						 outMessage = new Message(MessageType.ACK);
						 (((ArrivalTerminalTransferQuayProxy) (Thread.currentThread ())).getScon ()).setTimeout (10);
						 break;
		 }
		 return (outMessage);
	}
}
