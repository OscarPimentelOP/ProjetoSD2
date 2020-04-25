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
        
        //VALIDAR MENSAGENS!!
        
        //Process messages
        switch (inMessage.getType ()) {
            case CARRYBAGTOTEMPSTORE: 
            try {
                bcp.carryItToAppropriateStore(inMessage.bags());
                outMessage = new Message(MessageType.ACK); 
            } catch (Exception e) {
                //TODO: handle exception
            }        

            break; 

            case GOINGCOLLECTABAG:
            try {
                res = bcp.goCollectABag();
                outMessage = res ? new Message(MessageType.BAGOK) : new Message(MessageType.BAGNOTOK);
            } catch (Exception e) {
                //TODO: handle exception
            }   
            break; 

        }
        return outMessage;
    }        

}