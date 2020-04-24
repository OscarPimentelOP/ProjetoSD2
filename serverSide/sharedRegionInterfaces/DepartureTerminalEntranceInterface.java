package serverSide.sharedRegionInterfaces;

import AuxTools.Message;
import AuxTools.MessageException;
import AuxTools.MessageType;
import AuxTools.SharedException;
import serverSide.sharedRegions.DepartureTerminalEntrance;

public class DepartureTerminalEntranceInterface {
	private DepartureTerminalEntrance dte;
	
	public DepartureTerminalEntranceInterface (DepartureTerminalEntrance dte)
	{
		this.dte = dte;
	}
	
	public Message processAndReply (Message inMessage) throws MessageException{
		 Message outMessage = null;                           // response message
		 
		 //VALIDAR MENSAGENS!!
		 
		 //Process messages
		 switch (inMessage.getType ()) {
		 case PREPARINGNEXTLEG : try {
					   		dte.prepareNextLeg(inMessage.getFlight());
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
