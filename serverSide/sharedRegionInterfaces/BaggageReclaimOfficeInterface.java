package serverSide.sharedRegionInterfaces;

import AuxTools.Message;
import AuxTools.MessageException;
import AuxTools.MessageType;
import AuxTools.SharedException;
import serverSide.sharedRegions.BaggageReclaimOffice;

public class BaggageReclaimOfficeInterface {
	private BaggageReclaimOffice bco;
	
	public BaggageReclaimOfficeInterface (BaggageReclaimOffice bco)
	{
		this.bco = bco;
	}
	
	public Message processAndReply (Message inMessage) throws MessageException{
		 Message outMessage = null;                           // response message
		 
		 //VALIDAR MENSAGENS!!
		 
		 //Process messages
		 switch (inMessage.getType ()) {
		 case REPORTBAG : try {
					   		bco.reportMissingBags(inMessage.getReportedBags());
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
