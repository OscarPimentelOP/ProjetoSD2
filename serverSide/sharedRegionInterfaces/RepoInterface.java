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
								   
		case SETPORTERSTATE : repo.setPorterState(inMessage.getPorterState());
		outMessage = new Message(MessageType.ACK);
		break;

		case DECNUMBAGSATPLANEHOLD : repo.decNumOfBagsAtPlaneHold();
		outMessage = new Message(MessageType.ACK);
		break;
		
		case SETTINGFLIGHTNUMBER : repo.setFlightNumber(inMessage.getFlight());
		outMessage = new Message(MessageType.ACK);
		break;

		case SETTINGNUMOFBAGSATPLANEHOLD : repo.setNumOfBagsAtPlaneHold(inMessage.getFlight(), inMessage.getNumOfBagsInteger());
		outMessage = new Message(MessageType.ACK);
		break;

		case SETTINGPASSANGERSONTRANSIT : repo.setPassengersTransit(inMessage.getPassengersInTransit());
		outMessage = new Message(MessageType.ACK);
		break;

		case SETTINGPASSANGERDEST : repo.setPassengerDestination(inMessage.getPassengerID(), inMessage.getDestType());
		outMessage = new Message(MessageType.ACK);
		break;

		case SETNUMOFBAGSATTHEBEGINNING : repo.setNumOfBagsAtTheBegining(inMessage.getPassengerID(), inMessage.getNumberOfBags());
		outMessage = new Message(MessageType.ACK);
		break;	

		case SETTINGPASSENGERSTATE : repo.setPassengerState(inMessage.getPassengerID(), inMessage.getPassengerState());
		outMessage = new Message(MessageType.ACK);
		break;	
		
		case SETTINGPASSANGERFINALDESTINATION : repo.setPassengersFinalDest(inMessage.getPassengersFinalDest());
		outMessage = new Message(MessageType.ACK);
		break;

		case SETTINGPASSANGERSONTHEQUEUE : repo.setPassengersOnTheQueue(inMessage.getPassengersOnTheQueue());
		outMessage = new Message(MessageType.ACK);
		break;

		case SETTINGPASSANGERSONTHEBUS : repo.setPassangersOnTheBus(inMessage.getPassangersOnTheBus());
		outMessage = new Message(MessageType.ACK);
		break;

		case SETTINGNUMOFBAGSINTHECB : repo.setNumOfBagsInTheConvoyBelt(inMessage.getNumOfBagsInTheConvoyBelt());
		outMessage = new Message(MessageType.ACK);
		break;

		case SETTINGNUMOFBAGSCOLLECTED : repo.setNumOfBagsCollected(inMessage.getNumOfBagsCollected());
		outMessage = new Message(MessageType.ACK);
		break;

		case SETTINGLOSTBAG : repo.setLostBags(inMessage.getLostBags());
		outMessage = new Message(MessageType.ACK);
		break;

		case SETTINGNUMBAGSTEMPAREA : repo.setNumOfBagsInTheTempArea(inMessage.getNumOfBagsInTheTempArea());
		outMessage = new Message(MessageType.ACK);
		break;

		 }
		 return (outMessage);
	}
}
