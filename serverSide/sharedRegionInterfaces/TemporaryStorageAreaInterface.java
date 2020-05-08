package serverSide.sharedRegionInterfaces;

import AuxTools.*;
import serverSide.Proxys.TemporaryStorageAreaProxy;
import serverSide.sharedRegions.*;


public class TemporaryStorageAreaInterface {
    private TemporaryStorageArea tsa;

    public TemporaryStorageAreaInterface(TemporaryStorageArea tsa){
        this.tsa = tsa;
    }

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