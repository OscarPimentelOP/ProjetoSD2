package serverSide.sharedRegionInterfaces;

import AuxTools.Message;
import AuxTools.MessageException;
import AuxTools.MessageType;
import AuxTools.SharedException;
import AuxTools.SimulatorParam;
import serverSide.Proxys.BaggageReclaimOfficeProxy;
import serverSide.sharedRegions.BaggageReclaimOffice;

public class BaggageReclaimOfficeInterface {
	private BaggageReclaimOffice bro;
	
	public BaggageReclaimOfficeInterface (BaggageReclaimOffice bro)
	{
		this.bro = bro;
	}
	
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
