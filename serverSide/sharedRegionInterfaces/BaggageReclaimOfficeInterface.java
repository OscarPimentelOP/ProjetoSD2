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
import serverSide.Proxys.BaggageReclaimOfficeProxy;
import serverSide.sharedRegions.BaggageReclaimOffice;

/**
 * This class implements the Baggage Reclaim Office Interface that processes the
 * received messages and based on that received message executes the respective function from the actual
 *  Baggage Reclaim Office. Furthermore, this Interface creates the reply message.
 */
public class BaggageReclaimOfficeInterface {

	 /**
	 *  Baggage Reclaim Office shared region
     * @serialField bro
     */
	private BaggageReclaimOffice bro;
	

	/**
	 *  Baggage Reclaim Office Interface instantiation
     * @param bro Baggage Reclaim Office shared region
     */
	public BaggageReclaimOfficeInterface (BaggageReclaimOffice bro)
	{
		this.bro = bro;
	}
	
	/**
	 * This function receives the incoming message and executes the correct function from the Baggage Reclaim Office shared region and then
	 * generates the reply message.
     * @param inMessage incoming message from the main
     */
	public Message processAndReply (Message inMessage) throws MessageException{
		 Message outMessage = null;                           // response message
		 
		 //Validate messages
		 switch (inMessage.getType ()) {
		 case REPORTBAG : if ((inMessage.getReportedBags() < 0) || (inMessage.getReportedBags() > SimulatorParam.MAX_NUM_OF_BAGS))
			 				throw new MessageException ("Number of missing bags invalid!", inMessage);
			 			  break;
		 case SHUTDOWN : break;
		 default : throw new MessageException ("Message type invalid : ", inMessage);
		 }
		 
		 //Process messages
		 switch (inMessage.getType ()) {
		 case REPORTBAG : try {
					   		bro.reportMissingBags(inMessage.getReportedBags(), inMessage.getPassengerID());
					   		outMessage = new Message(MessageType.ACK);
							break;
					   		
					   } catch (SharedException e) {
						   // TODO Auto-generated catch block
						   e.printStackTrace();
					   }
		 case SHUTDOWN : bro.shutServer();
						 outMessage = new Message(MessageType.ACK);
						 (((BaggageReclaimOfficeProxy) (Thread.currentThread ())).getScon ()).setTimeout (10);
						 break;
		 }
		 return (outMessage);
	}
}
