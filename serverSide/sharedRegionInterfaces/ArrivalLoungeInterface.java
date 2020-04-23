package serverSide.sharedRegionInterfaces;

import AuxTools.Message;
import AuxTools.MessageException;
import AuxTools.MessageType;
import AuxTools.SharedException;
import serverSide.sharedRegions.ArrivalLounge;

public class ArrivalLoungeInterface {
	
	private ArrivalLounge al;
	
	public ArrivalLoungeInterface (ArrivalLounge al)
	{
		this.al = al;
	}

	
	 public Message processAndReply (Message inMessage) throws MessageException{
		 Message outMessage = null;                           // response message
		 
		 //VALIDAR MENSAGENS!!
		 
		 //Process messages
		 switch (inMessage.getType ()) {
		 case WHATSHOULDIDO:try {
						char a = al.whatShouldIDo(inMessage.getFlight()); 
						switch(a) {
						case 'H' : outMessage = new Message(MessageType.GOHOME);
								   break;
						case 'T' : outMessage = new Message(MessageType.TAKEABUS);
								   break;
						case 'B' : outMessage = new Message(MessageType.GOCOLLECTABAG);
								   break;
						}
					} catch (SharedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		 			break;
		 }
		 
		 return (outMessage);
	 }
}
