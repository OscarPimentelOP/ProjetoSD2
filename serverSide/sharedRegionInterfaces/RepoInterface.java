package serverSide.sharedRegionInterfaces;

import AuxTools.Message;
import AuxTools.MessageException;
import AuxTools.MessageType;
import serverSide.sharedRegions.Repo;

public class RepoInterface {
	private Repo repo;
	
	public RepoInterface (Repo repo)
	{
		this.repo = repo;
	}
	
	public Message processAndReply (Message inMessage) throws MessageException{
		 Message outMessage = null;                           // response message
		 
		 //Validate messages
		 switch (inMessage.getType ()) {
		 case SETBUSDRIVERSTATE : break;
		 default : throw new MessageException ("Message type invalid : ", inMessage);
		 }
		 
		 
		 //Process messages
		 switch (inMessage.getType ()) {
		 case SETBUSDRIVERSTATE : repo.setBusDriverState(inMessage.getBusDriverState());
		 						  outMessage = new Message(MessageType.ACK);
		 						  break;
		 }
		 return (outMessage);
	}
}
