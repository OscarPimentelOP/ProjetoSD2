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
import serverSide.Proxys.DepartureTerminalEntranceProxy;
import serverSide.sharedRegions.DepartureTerminalEntrance;

/**
 * This class implements the Departure Terminal Entrance Interface that processes the
 * received messages and based on that received message executes the respective function from the actual
 *  Departure Terminal Entrance. Furthermore, this Interface creates the reply message.
 */
public class DepartureTerminalEntranceInterface {

	/**
	 *  Departure Terminal Entrance shared region
     * @serialField dte
     */
	private DepartureTerminalEntrance dte;
	

	/**
	 *  Departure Terminal Entrance Interface instantiation
     * @param dte Departure Terminal Entrance shared region
     */
	public DepartureTerminalEntranceInterface (DepartureTerminalEntrance dte)
	{
		this.dte = dte;
	}
	
	/**
	 * This function receives the incoming message and executes the correct function from the Departure Terminal Entrance shared region and then
	 * generates the reply message.
     * @param inMessage incoming message from the main
     */
	public Message processAndReply (Message inMessage) throws MessageException{
		 Message outMessage = null;                           // response message
		 
		//Validate messages
		 switch (inMessage.getType ()) {
		 case PREPARINGNEXTLEG : if ((inMessage.getFlight() < 0) || (inMessage.getFlight() > SimulatorParam.NUM_FLIGHTS))
									throw new MessageException ("Number of flights invalid!", inMessage);
						  		 break;
		 case WAKEUPALLD : break;
		 case SETTIMETOWAKEUPTOFALSED : break;
		 case SHUTDOWN : break;
		 default : throw new MessageException ("Message type invalid : ", inMessage);
		 }
		 
		 //Process messages
		 switch (inMessage.getType ()) {
		 case PREPARINGNEXTLEG : try {
					   		dte.prepareNextLeg(inMessage.getFlight(), inMessage.getPassengerID());
					   		outMessage = new Message(MessageType.ACK);
							break;
					   		
					   } catch (SharedException e) {
						   // TODO Auto-generated catch block
						   e.printStackTrace();
					   }
		 case WAKEUPALLD : dte.wakeUpAll();
						   outMessage = new Message(MessageType.ACK);
					       break;
		 case SETTIMETOWAKEUPTOFALSED : dte.setTimeToWakeUpToFalse();
										outMessage = new Message(MessageType.ACK);
									    break;
		 case SHUTDOWN : dte.shutServer();
						 outMessage = new Message(MessageType.ACK);
						 (((DepartureTerminalEntranceProxy) (Thread.currentThread ())).getScon ()).setTimeout (10);
						 break;
		 }
		 return (outMessage);
	}
}
