package serverSide.sharedRegionInterfaces;

import AuxTools.Message;
import AuxTools.MessageException;
import AuxTools.MessageType;
import AuxTools.SharedException;
import serverSide.sharedRegions.ArrivalTerminalTransferQuay;

public class ArrivalTerminalTransferQuayInterface {
	private ArrivalTerminalTransferQuay attq;
	
	public ArrivalTerminalTransferQuayInterface (ArrivalTerminalTransferQuay attq)
	{
		this.attq = attq;
	}
	
	public Message processAndReply (Message inMessage) throws MessageException{
		 Message outMessage = null;                           // response message
		 
		 //Validate messages
		 switch (inMessage.getType ()) {
		 case TAKINGABUS : break;
		 case ENTERINGTHEBUS : break;
		 case HASDAYSWORKENDED : break;
		 case ANNOUNCEBUS : break;
		 case PARKATARRIVAL : break;
		 default : throw new MessageException ("Message type invalid : ", inMessage);
		 }
		 
		 
		 //Process messages
		 switch (inMessage.getType ()) {
		 case TAKINGABUS : attq.takeABus();
						   outMessage = new Message(MessageType.ACK);
						   break;
		 case ENTERINGTHEBUS : try {
								attq.enterTheBus();
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
		 }
		 return (outMessage);
	}
}
