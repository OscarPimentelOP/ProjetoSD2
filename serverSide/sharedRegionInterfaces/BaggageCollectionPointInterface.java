package serverSide.sharedRegionInterfaces;
import AuxTools.*;
import serverSide.sharedRegions.*;

public class BaggageCollectionPointInterface {
    private BaggageCollectionPoint bcp;

    public BaggageCollectionPointInterface(BaggageCollectionPoint bcp){
        this.bcp = bcp;
    }


    public Message processAndReply (Message inMessage) throws MessageException{
        
        Message outMessage = null; 
        boolean res;                          // response message
        
        //Validate messages
	    switch (inMessage.getType ()) {
			case CARRYBAGTOBAGPOINT : if (inMessage.bags()==null)
											throw new MessageException ("Bag cannot be null!", inMessage);
									   break;
			case GOINGCOLLECTABAG : break;
			case SETMOREBAGS: break;
			default : throw new MessageException ("Message type invalid : ", inMessage);
		}
        
        //Process messages
        switch (inMessage.getType ()) {
            case CARRYBAGTOBAGPOINT: 
            try {
                bcp.carryItToAppropriateStore(inMessage.bags());
                outMessage = new Message(MessageType.ACK); 
            } catch (Exception e) {
                //TODO: handle exception
            }        

            break; 

            case GOINGCOLLECTABAG:
            try {
                res = bcp.goCollectABag(inMessage.getPassengerID());
                outMessage = res ? new Message(MessageType.BAGOK) : new Message(MessageType.BAGNOTOK);
            } catch (Exception e) {
                //TODO: handle exception
            }   
            break; 
            case SETMOREBAGS: bcp.setMoreBags(inMessage.getMoreBags());
            				  outMessage = new Message(MessageType.ACK); 
            				  break;
        }
        return outMessage;
    }        

}