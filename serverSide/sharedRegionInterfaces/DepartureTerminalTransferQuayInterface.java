/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package serverSide.sharedRegionInterfaces;

import AuxTools.*;
import serverSide.Proxys.DepartureTerminalTransferQuayProxy;
import serverSide.sharedRegions.*;

/**
 * This class implements the Departure Terminal Transfer Quay Interface that processes the
 * received messages and based on that received message executes the respective function from the actual
 *  Departure Terminal Transfer Quay. Furthermore, this Interface creates the reply message.
 */
public class DepartureTerminalTransferQuayInterface {

    /**
	 *  Departure Terminal Transfer Quay shared region
     * @serialField dttq
     */
    private DepartureTerminalTransferQuay dttq;

    /**
	 *  Departure Terminal Transfer Quay Interface instantiation
     * @param dttq Departure Terminal Transfer Quay shared region
     */
    public DepartureTerminalTransferQuayInterface(DepartureTerminalTransferQuay dttq){
        this.dttq = dttq;
    }

    /**
	 * This function receives the incoming message and executes the correct function from the Departure Terminal Transfer Quay shared region and then
	 * generates the reply message.
     * @param inMessage incoming message from the main
     */
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