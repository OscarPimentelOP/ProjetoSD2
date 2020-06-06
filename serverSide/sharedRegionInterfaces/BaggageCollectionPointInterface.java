/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package serverSide.sharedRegionInterfaces;
import AuxTools.*;
import serverSide.Proxys.BaggageCollectionPointProxy;
import serverSide.sharedRegions.*;


/**
 * This class implements the Baggage Collection Point Interface that processes the
 * received messages and based on that received message executes the respective function from the actual
 *  Baggage Collection Point. Furthermore, this Interface creates the reply message.
 */
public class BaggageCollectionPointInterface {

    /**
	 *  Baggage Collection Point shared region
     * @serialField bcp
     */
    private BaggageCollectionPoint bcp;

    /**
	 *  Baggage Collection Point Interface instantiation
     * @param bcp Baggage Collection Point shared region
     */
    public BaggageCollectionPointInterface(BaggageCollectionPoint bcp){
        this.bcp = bcp;
    }

    /**
	 * This function receives the incoming message and executes the correct function from the Baggage Collection Point shared region and then
	 * generates the reply message.
     * @param inMessage incoming message from the main
     */
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
			case SHUTDOWN : break;
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
            case SHUTDOWN : bcp.shutServer();
							 outMessage = new Message(MessageType.ACK);
							 (((BaggageCollectionPointProxy) (Thread.currentThread ())).getScon ()).setTimeout (10);
							 break;
        }
        return outMessage;
    }        

}