package serverSide.sharedRegionInterfaces;

import AuxTools.Message;
import AuxTools.MessageException;
import AuxTools.MessageType;
import AuxTools.SimulatorParam;
import serverSide.Proxys.RepoProxy;
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
		 case SETTINGNUMOFBAGSATPLANEHOLD : if ((inMessage.getNumOfBagsInteger() < 0) || (inMessage.getNumOfBagsInteger() > (SimulatorParam.MAX_NUM_OF_BAGS*SimulatorParam.NUM_PASSANGERS)))
										   		 throw new MessageException ("Number of bags invalid!", inMessage);
											if ((inMessage.getFlight() < 0) || (inMessage.getFlight() > SimulatorParam.NUM_FLIGHTS))
												throw new MessageException ("Number of flights invalid!", inMessage);
										    break;
		 case SETTINGPASSANGERSONTRANSIT : if((inMessage.getPassengersInTransit() < 0) || (inMessage.getPassengersInTransit() > SimulatorParam.NUM_PASSANGERS*SimulatorParam.NUM_FLIGHTS))
			 									throw new MessageException ("Number of passengers in transit invalid!", inMessage);
			 								break;
		 case SETTINGPASSANGERDEST : if((inMessage.getPassengerID() < 0) || (inMessage.getPassengerID() >= SimulatorParam.NUM_PASSANGERS))
										throw new MessageException ("Passenger id invalid!", inMessage);
									 if((!inMessage.getDestType().equals("TRT")) && (!inMessage.getDestType().equals("FDT")))
											throw new MessageException ("Passenger destination invalid!", inMessage);
			 						break;
		 case SETNUMOFBAGSATTHEBEGINNING : if((inMessage.getPassengerID() < 0) || (inMessage.getPassengerID() >= SimulatorParam.NUM_PASSANGERS))
												throw new MessageException ("Passenger id invalid!", inMessage);
											if ((inMessage.getNumBagsAtBeggining() < 0) || (inMessage.getNumBagsAtBeggining() > (SimulatorParam.MAX_NUM_OF_BAGS*SimulatorParam.NUM_PASSANGERS)))
										   		throw new MessageException ("Number of bags at the beggining invalid!", inMessage);
											break;
		 case SETTINGPASSENGERSTATE : if((inMessage.getPassengerID() < 0) || (inMessage.getPassengerID() >= SimulatorParam.NUM_PASSANGERS))
										throw new MessageException ("Passenger id invalid!", inMessage);
									 break;
		 case SETTINGPASSANGERFINALDESTINATION : if((inMessage.getPassengersFinalDest() < 0) || (inMessage.getPassengersFinalDest() > SimulatorParam.NUM_PASSANGERS*SimulatorParam.NUM_FLIGHTS))
													throw new MessageException ("Number of passengers in final destination invalid!", inMessage);
												 break;
		 case SETTINGPASSANGERSONTHEQUEUE :  System.out.println(inMessage.getPassengerID()); 		
			 								if((inMessage.getPassengerID() < -1) || (inMessage.getPassengerID() >= SimulatorParam.NUM_PASSANGERS))
												throw new MessageException ("Passenger id invalid!", inMessage);						
			 								if((inMessage.getPassengersOnTheQueue() < 0) || (inMessage.getPassengersOnTheQueue() >= SimulatorParam.NUM_PASSANGERS))
										  		throw new MessageException ("Number of passengers on the queue invalid!", inMessage);
			 							  break;
		 case SETTINGPASSANGERSONTHEBUS : if((inMessage.getPassengerID() < -1) || (inMessage.getPassengerID() >= SimulatorParam.NUM_PASSANGERS))
										  		throw new MessageException ("Passenger id invalid!", inMessage);
										  if((inMessage.getPassangersOnTheBus() < 0) || (inMessage.getPassangersOnTheBus() >= SimulatorParam.BUS_CAPACITY))
										  		throw new MessageException ("Number of passengers on the queue invalid!", inMessage);
			 							  break;
		 case SETTINGNUMOFBAGSINTHECB : break;
		 case SETTINGNUMOFBAGSCOLLECTED : if((inMessage.getPassengerID() < 0) || (inMessage.getPassengerID() >= SimulatorParam.NUM_PASSANGERS))
									  		 throw new MessageException ("Passenger id invalid!", inMessage);
										  break;
		 case SETTINGLOSTBAGS : if ((inMessage.getMissingBagsTotal() < 0) || (inMessage.getMissingBagsTotal() > (SimulatorParam.MAX_NUM_OF_BAGS*SimulatorParam.NUM_PASSANGERS)))
		   							throw new MessageException ("Number of lost bags invalid!", inMessage);
			 					break;
		 case SETTINGNUMBAGSTEMPAREA : break;
		 case SHUTDOWN : break;
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
		case SHUTDOWN : repo.shutServer();
						 outMessage = new Message(MessageType.ACK);
						 (((RepoProxy) (Thread.currentThread ())).getScon ()).setTimeout (10);
						 break;
		 }
		 return (outMessage);
	}
}
