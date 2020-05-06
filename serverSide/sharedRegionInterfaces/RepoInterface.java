package serverSide.sharedRegionInterfaces;

import AuxTools.Message;
import AuxTools.MessageException;
import AuxTools.MessageType;
import AuxTools.SimulatorParam;
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
		 case SETPORTERSTATE : break;
		 case DECNUMBAGSATPLANEHOLD : break;
		 case SETTINGFLIGHTNUMBER : if ((inMessage.getFlight() < 0) || (inMessage.getFlight() > SimulatorParam.NUM_FLIGHTS))
								   		 throw new MessageException ("Number of flights invalid!", inMessage);
								    break;
		 case SETTINGNUMOFBAGSATPLANEHOLD : if ((inMessage.getNumOfBagsInteger() < 0) || (inMessage.getNumOfBagsInteger() > SimulatorParam.MAX_NUM_OF_BAGS))
										   		 throw new MessageException ("Number of bags invalid!", inMessage);
											if ((inMessage.getFlight() < 0) || (inMessage.getFlight() > SimulatorParam.NUM_FLIGHTS))
												throw new MessageException ("Number of flights invalid!", inMessage);
										    break;
		 //TERMINAR DAQUI PARA A FRENTE
		 case SETTINGPASSANGERSONTRANSIT : break;
		 case SETTINGPASSANGERDEST : break;
		 case SETNUMOFBAGSATTHEBEGINNING : break;
		 case SETTINGPASSENGERSTATE : break;
		 case SETTINGPASSANGERFINALDESTINATION : break;
		 case SETTINGPASSANGERSONTHEQUEUE : break;
		 case SETTINGPASSANGERSONTHEBUS : break;
		 case SETTINGNUMOFBAGSINTHECB : break;
		 case SETTINGNUMOFBAGSCOLLECTED : break;
		 case SETTINGNUMBAGSTEMPAREA : break;
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

		case SETNUMOFBAGSATTHEBEGINNING : repo.setNumOfBagsAtTheBegining(inMessage.getPassengerID(), inMessage.getNumBagsAtBeggining());
		outMessage = new Message(MessageType.ACK);
		break;	

		case SETTINGPASSENGERSTATE : repo.setPassengerState(inMessage.getPassengerID(), inMessage.getPassengerState());
		outMessage = new Message(MessageType.ACK);
		break;	
		
		case SETTINGPASSANGERFINALDESTINATION : repo.setPassengersFinalDest(inMessage.getPassengersFinalDest());
		outMessage = new Message(MessageType.ACK);
		break;

		case SETTINGPASSANGERSONTHEQUEUE : repo.setPassengersOnTheQueue(inMessage.getPassengersOnTheQueue(), inMessage.getPassengerID());
		outMessage = new Message(MessageType.ACK);
		break;

		case SETTINGPASSANGERSONTHEBUS : repo.setPassangersOnTheBus(inMessage.getPassangersOnTheBus(), inMessage.getPassengerID());
		outMessage = new Message(MessageType.ACK);
		break;

		case SETTINGNUMOFBAGSINTHECB : repo.setNumOfBagsInTheConvoyBelt(inMessage.getNumOfBagsInConveyBelt());
		outMessage = new Message(MessageType.ACK);
		break;

		case SETTINGNUMOFBAGSCOLLECTED : repo.setNumOfBagsCollected(inMessage.getPassengerID());
		outMessage = new Message(MessageType.ACK);
		break;

		case SETTINGLOSTBAGS : repo.setLostBags(inMessage.getMissingBagsTotal());
		outMessage = new Message(MessageType.ACK);
		break;

		case SETTINGNUMBAGSTEMPAREA : repo.setNumOfBagsInTheTempArea(inMessage.getNumOfBagsInTheTempArea());
		outMessage = new Message(MessageType.ACK);
		break;

		 }
		 return (outMessage);
	}
}
