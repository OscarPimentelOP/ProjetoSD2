package serverSide.sharedRegionInterfaces;

import AuxTools.*;
import serverSide.Proxys.DepartureTerminalTransferQuayProxy;
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
		case SHUTDOWN : break;
		default : throw new MessageException ("Message type invalid : ", inMessage);
		}
        
        //Process messages
        switch (inMessage.getType ()) {
            case LEAVETHEBUS: 
            try {
                dttq.leaveTheBus(inMessage.getPassengerID());
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
            case SHUTDOWN : dttq.shutServer();
							 outMessage = new Message(MessageType.ACK);
							 (((DepartureTerminalTransferQuayProxy) (Thread.currentThread ())).getScon ()).setTimeout (10);
							 break;
        }
     
        return outMessage;  
    }


}