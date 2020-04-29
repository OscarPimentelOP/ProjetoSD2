package serverSide.sharedRegionInterfaces;

import AuxTools.*;
import serverSide.sharedRegions.*;

public class DepartureTerminalTransferQuayInterface {
    private DepartureTerminalTransferQuay dttq;

    public DepartureTerminalTransferQuayInterface(DepartureTerminalTransferQuay dttq){
        this.dttq = dttq;
    }

    public Message processAndReply (Message inMessage) throws MessageException{
        
        Message outMessage = null;                           // response message
        
        //Validate messages
		switch (inMessage.getType ()) {
		case LEAVETHEBUS : break;
		case PARKATDEPARTURE : break;
		default : throw new MessageException ("Message type invalid : ", inMessage);
		}
        
        //Process messages
        switch (inMessage.getType ()) {
            case LEAVETHEBUS: 
            try {
                dttq.leaveTheBus();
                outMessage = new Message(MessageType.ACK); 
            } catch (Exception e) {
                //TODO: handle exception
            }
            break; 

            case PARKATDEPARTURE:
           try {
                dttq.parkTheBusAndLetPassOff();
                outMessage = new Message(MessageType.ACK); 
            } catch (Exception e) {
                //TODO: handle exception
            }
            break; 

        }
     
        return outMessage;  
    }


}