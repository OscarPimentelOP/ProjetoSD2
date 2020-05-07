package serverSide.sharedRegionInterfaces;

import AuxTools.Message;
import AuxTools.MessageException;
import AuxTools.MessageType;
import AuxTools.SharedException;
import AuxTools.SimulatorParam;
import serverSide.sharedRegions.ArrivalTerminalExit;

public class ArrivalTerminalExitInterface {
	private ArrivalTerminalExit ate;
	
	public ArrivalTerminalExitInterface (ArrivalTerminalExit ate)
	{
		this.ate = ate;
	}
	
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
		 }
		 return (outMessage);
	}
}
