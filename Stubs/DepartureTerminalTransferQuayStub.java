package Stubs;

import AuxTools.*;
import clientSide.*;


/**
 * This stub class represents the Departue terminal transfer quay, needed in the client side,
 *  converts a method's call into a message and communicates with the server side.
 */
public class DepartureTerminalTransferQuayStub {

    /**
	 * Departue terminal transfer quay server hostname
	 * @serialField serverHostName
	 */
	private String serverHostName;

	/**
	 * Departue terminal transfer quaya server port
	 * @serialField serverPort
	 */
    private int serverPort;

    public DepartureTerminalTransferQuayStub(){

        this.serverHostName = SimulatorParam.temporaryStorageAreaHostName;
        this.serverPort = SimulatorParam.temporaryStorageAreaPort;
    }


    public void leaveTheBus(){
        Passenger p = (Passenger) Thread.currentThread();

        Message m = new Message(MessageType.LEAVETHEBUS);

        ClientCom cc = new ClientCom(serverHostName,serverPort);
        cc.open(); 
        cc.writeObject(m);

        m=(Message) cc.readObject();


    }



}