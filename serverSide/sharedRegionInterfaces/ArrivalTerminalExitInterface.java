/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package serverSide.sharedRegionInterfaces;

import AuxTools.Message;
import AuxTools.MessageException;
import AuxTools.MessageType;
import AuxTools.SharedException;
import AuxTools.SimulatorParam;
import serverSide.Proxys.ArrivalTerminalExitProxy;
import serverSide.sharedRegions.ArrivalTerminalExit;

/**
 * This class implements the Arrival Terminal Exit Interface that processes the
 * received messages and based on that received message executes the respective function from the actual
 * Arrival Terminal Exit. Furthermore, this Interface creates the reply message.
 */

public class ArrivalTerminalExitInterface {

	/**
	 * Arrival Terminal Exit shared region
     * @serialField ate
     */
	private ArrivalTerminalExit ate;
	
	/**
	 * Arrival Terminal Exit Interface instantiation
     * @param ate Arrival Terminal Exit shared region
     */
	public ArrivalTerminalExitInterface (ArrivalTerminalExit ate)
	{
		this.ate = ate;
	}
	
	/**
	 * This function receives the incoming message and executes the correct function from the Arrival Terminal Exit shared region and then
	 * generates the reply message.
     * @param inMessage incoming message from the main
     */
	public Message processAndReply (Message inMessage) throws MessageException{
		 Message outMessage = null;                           // response message
		 
		 //Validate messages
		 switch (inMessage.getType ()) {
		 case GOINGHOME : if ((inMessage.getFlight() < 0) || (inMessage.getFlight() > SimulatorParam.NUM_FLIGHTS))
							throw new MessageException ("Number of flights invalid!", inMessage);
						  break;
		 case INCCNTPASSENGERSEND : break;
		 case GETCNTPASSENGERSEND : break;
		 case DECCNTPASSENGERSEND : break;
		 case WAKEUPALLA : break;
		 case SETTIMETOWAKEUPTOFALSEA : break;
		 case SHUTDOWN : break;
		 default : throw new MessageException ("Message type invalid : ", inMessage);
		 }
		 
		 //Process messages
		 switch (inMessage.getType ()) {
		 case GOINGHOME : try {
					   		ate.goHome(inMessage.getFlight(), inMessage.getPassengerID());
					   		outMessage = new Message(MessageType.ACK);
							break;
					   		
					   } catch (SharedException e) {
						   // TODO Auto-generated catch block
						   e.printStackTrace();
					   }
		 case INCCNTPASSENGERSEND : ate.incCntPassengersEnd();
		 							outMessage = new Message(MessageType.ACK);
		 							break;
		 case GETCNTPASSENGERSEND : int cnt = ate.getCntPassengersEnd();
									outMessage = new Message(MessageType.SENDCNTPASSENGERSEND, cnt);
									break;
		 case DECCNTPASSENGERSEND : ate.decCntPassengersEnd();
		 							outMessage = new Message(MessageType.ACK);
		 							break;
		 case WAKEUPALLA : ate.wakeUpAll();
						   outMessage = new Message(MessageType.ACK);
					       break;
		case SETTIMETOWAKEUPTOFALSEA : ate.setTimeToWakeUpToFalse();
									   outMessage = new Message(MessageType.ACK);
									   break;
		case SHUTDOWN : ate.shutServer();
						 outMessage = new Message(MessageType.ACK);
						 (((ArrivalTerminalExitProxy) (Thread.currentThread ())).getScon ()).setTimeout (10);
						 break;
		 }
		 return (outMessage);
	}
}
