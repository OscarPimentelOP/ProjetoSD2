package serverSide.sharedRegionInterfaces;

import AuxTools.Bag;
import AuxTools.Message;
import AuxTools.MessageException;
import AuxTools.MessageType;
import AuxTools.SharedException;
import AuxTools.SimulatorParam;
import serverSide.Proxys.ArrivalLoungeProxy;
import serverSide.sharedRegions.ArrivalLounge;

public class ArrivalLoungeInterface {
	
	private ArrivalLounge al;
	
	public ArrivalLoungeInterface (ArrivalLounge al)
	{
		this.al = al;
	}

	
	 public Message processAndReply (Message inMessage) throws MessageException{
		 Message outMessage = null;                           // response message
		 
		 //Validate messages
		 switch (inMessage.getType ()) {
		 case WHATSHOULDIDO : if ((inMessage.getFlight() < 0) || (inMessage.getFlight() > SimulatorParam.NUM_FLIGHTS))
             					throw new MessageException ("Number of flights invalid!", inMessage);
          					  break;
		 case TAKEAREST : break;
		 case TRYTOCOLLECTABAG : break;
		 case NOMOREBAGSTOCOLLECT : break;
		 case SENDPARAMS : break;
		 case SETENDOFWORKPORTER : break;
		 case SHUTDOWN : break;
		 default : throw new MessageException ("Message type invalid : ", inMessage);
		 }
		 
		 //Process messages
		 switch (inMessage.getType ()) {
		 case SENDPARAMS : al.setPlaneHold(inMessage.getSBags(), inMessage.getNumOfBagsPerFlight(), inMessage.getTripState());
		 				   outMessage = new Message(MessageType.SENDPARAMSACK);
		 				   break;
		 case WHATSHOULDIDO:try {
						char a = al.whatShouldIDo(inMessage.getFlight(), inMessage.getPassengerID(), inMessage.getIndividualTripState(), inMessage.getNumOfBagsInteger()); 
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
		 case TAKEAREST : char a = al.takeARest();
		 				  if(a=='E') {
		 					 outMessage = new Message(MessageType.ENDPORTER);
		 				  }
		 				  else {
		 					 outMessage = new Message(MessageType.KEEPWORKINGPORTER);
		 				  }
		 				  break;
		 case TRYTOCOLLECTABAG : Bag bag = al.tryToCollectABag();
								 if(bag==null) {
									 outMessage = new Message(MessageType.NOMOREBAGSATPLANEHOLD);
								 }
								 else {
									 outMessage = new Message(MessageType.BAGTOCOLLECT, bag);
								 }
								 break;
		 case NOMOREBAGSTOCOLLECT : al.noMoreBagsToCollect();
		 							outMessage = new Message(MessageType.ACK);
		 							break;
		 case SETENDOFWORKPORTER: al.setEndOfWork();
								  outMessage = new Message(MessageType.ACK);
								  break;
		 case SHUTDOWN : al.shutServer();
						 outMessage = new Message(MessageType.ACK);
						 (((ArrivalLoungeProxy) (Thread.currentThread ())).getScon ()).setTimeout (10);
						 break;
		 }
		 
		 
		 return (outMessage);
	 }
}
