package serverSide.sharedRegionInterfaces;

import AuxTools.*;
import serverSide.sharedRegions.*;


public class TemporaryStorageAreaInterface {
    private TemporaryStorageArea tsa;

    public TemporaryStorageAreaInterface(TemporaryStorageArea tsa){
        this.tsa = tsa;
    }

    public Message processAndReply (Message inMessage) throws MessageException{
        
        Message outMessage = null;                           // response message
        
        //VALIDAR MENSAGENS!!
        
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

            
        }
        return outMessage;

        }        

}