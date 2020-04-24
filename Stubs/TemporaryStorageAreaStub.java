package Stubs;

import AuxTools.*;
import clientSide.*;

/**
 * This stub class represents the Temporary Storage Area, needed in the client side,
 *  converts a method's call into a message and communicates with the server side.
 */
public class TemporaryStorageAreaStub {
    /**
	 * Temporary Storage Area server hostname
	 * @serialField serverHostName
	 */
	private String serverHostName;

	/**
	 * Temporary Storage Area server port
	 * @serialField serverPort
	 */
    private int serverPort;
    
    public TemporaryStorageAreaStub(){
        this.serverHostName = SimulatorParam.temporaryStorageAreaHostName;
        this.serverPort = SimulatorParam.temporaryStorageAreaPort;

    }


    public void carryItToAppropriateStore(Bag bag){
        Porter p = (Porter) Thread.currentThread();

        Message m = new Message(MessageType.CARRYBAGTOTEMPSTORE, bag);

        ClientCom cc = new ClientCom(serverHostName,serverPort);
        cc.open(); 
        cc.writeObject(m);

        m=(Message) cc.readObject();

        
    }




}