package serverSide.sharedRegionInterfaces;

import AuxTools.Message;
import AuxTools.MessageException;
import AuxTools.MessageType;
import AuxTools.SharedException;
import AuxTools.SimulatorParam;
import serverSide.sharedRegions.DepartureTerminalEntrance;

public class DepartureTerminalEntranceInterface {
	private DepartureTerminalEntrance dte;
	
	public DepartureTerminalEntranceInterface (DepartureTerminalEntrance dte)
	{
		this.dte = dte;
	}
	
	public Message processAndReply (Message inMessage) throws MessageException{
		 Message outMessage = null;                           // response message
		 
		//Validate messages
		 switch (inMessage.getType ()) {
		 case PREPARINGNEXTLEG : if ((inMessage.getFlight() < 0) || (inMessage.getFlight() > SimulatorParam.NUM_FLIGHTS))
									throw new MessageException ("Number of flights invalid!", inMessage);
						  		 break;
		 default : throw new MessageException ("Message type invalid : ", inMessage);
		 }
		 
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
		 case WAKEUPALLD : dte.wakeUpAll();
						   outMessage = new Message(MessageType.ACK);
					       break;
		 case SETTIMETOWAKEUPTOFALSED : dte.setTimeToWakeUpToFalse();
										outMessage = new Message(MessageType.ACK);
									    break;
		 }
		 return (outMessage);
	}
}
