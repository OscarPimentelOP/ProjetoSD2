/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package serverSide.sharedRegionInterfaces;

import AuxTools.*;
import serverSide.Proxys.TemporaryStorageAreaProxy;
import serverSide.sharedRegions.*;

/**
 * This class implements the Temporary Storage Area Interface that processes the
 * received messages and based on that received message executes the respective function from the actual
 *  Temporary Storage Area. Furthermore, this Interface creates the reply message.
 */
public class TemporaryStorageAreaInterface {

    /**
	 *  Temporary Storage Area shared region
     * @serialField tsa
     */
    private TemporaryStorageArea tsa;

    /**
	 *  Temporary Storage Area Interface instantiation
     * @param tsa Temporary Storage Area shared region
     */
    public TemporaryStorageAreaInterface(TemporaryStorageArea tsa){
        this.tsa = tsa;
    }

    /**
	 * This function receives the incoming message and executes the correct function from the Temporary Storage Area shared region and then
	 * generates the reply message.
     * @param inMessage incoming message from the main
     */
    public Message processAndReply (Message inMessage) throws MessageException{
        
        Message outMessage = null;                           // response message
        
        //Validate messages
      	switch (inMessage.getType ()) {
      	case CARRYBAGTOTEMPSTORE : if (inMessage.bags()==null)
      									throw new MessageException ("Bag cannot be null!", inMessage);
      							   break;
      	case SHUTDOWN : break;
      	default : 
      				throw new MessageException ("Message type invalid OLA : ", inMessage);
      	}
        
        //Process messages
        switch (inMessage.getType ()) {
            case CARRYBAGTOTEMPSTORE: 
            try {
                tsa.carryItToAppropriateStore(inMessage.bags());
                outMessage = new Message(MessageType.ACK);   
            } catch (Exception e) {
                //TODO: handle exception
            }
            break;
            case SHUTDOWN : tsa.shutServer();
			 outMessage = new Message(MessageType.ACK);
			 (((TemporaryStorageAreaProxy) (Thread.currentThread ())).getScon ()).setTimeout (10);
			 break;
        }
        return outMessage;

        }        

}