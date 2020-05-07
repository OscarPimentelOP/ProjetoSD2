package serverSide.sharedRegionInterfaces;

import AuxTools.Message;
import AuxTools.MessageException;
import AuxTools.MessageType;
import AuxTools.SharedException;
import AuxTools.SimulatorParam;
import serverSide.sharedRegions.BaggageReclaimOffice;

public class BaggageReclaimOfficeInterface {
	private BaggageReclaimOffice bco;
	
	public BaggageReclaimOfficeInterface (BaggageReclaimOffice bco)
	{
		this.bco = bco;
	}
	
	public Message processAndReply (Message inMessage) throws MessageException{
		 Message outMessage = null;                           // response message
		 
		 //Validate messages
		 switch (inMessage.getType ()) {
		 case REPORTBAG : if ((inMessage.getReportedBags() < 0) || (inMessage.getReportedBags() > SimulatorParam.MAX_NUM_OF_BAGS))
			 				throw new MessageException ("Number of missing bags invalid!", inMessage);
			 			  break;
		 default : throw new MessageException ("Message type invalid : ", inMessage);
		 }
		 
		 //Process messages
		 switch (inMessage.getType ()) {
		 case REPORTBAG : try {
					   		bco.reportMissingBags(inMessage.getReportedBags(), inMessage.getPassengerID());
					   		outMessage = new Message(MessageType.ACK);
							break;
					   		
					   } catch (SharedException e) {
						   // TODO Auto-generated catch block
						   e.printStackTrace();
					   }
		 }
		 return (outMessage);
	}
}
