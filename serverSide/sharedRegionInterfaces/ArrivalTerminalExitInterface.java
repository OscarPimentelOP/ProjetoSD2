package serverSide.sharedRegionInterfaces;

import AuxTools.Message;
import AuxTools.MessageException;
import AuxTools.MessageType;
import AuxTools.SharedException;
import serverSide.sharedRegions.ArrivalTerminalExit;

public class ArrivalTerminalExitInterface {
	private ArrivalTerminalExit ate;
	
	public ArrivalTerminalExitInterface (ArrivalTerminalExit ate)
	{
		this.ate = ate;
	}
	
	public Message processAndReply (Message inMessage) throws MessageException{
		 Message outMessage = null;                           // response message
		 
		 //VALIDAR MENSAGENS!!
		 
		 //Process messages
		 switch (inMessage.getType ()) {
		 case GOINGHOME : try {
					   		ate.goHome(inMessage.getFlight());
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
