package clientSide.Stubs;

import AuxTools.*;
import clientSide.*;
import clientSide.Entities.*;

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
        ClientCom cc = new ClientCom(serverHostName,serverPort);
        Porter p = (Porter) Thread.currentThread();
        Message inmsg, outmsg; 

        outmsg = new Message(MessageType.CARRYBAGTOTEMPSTORE, bag);
        cc.writeObject(outmsg);
        inmsg = (Message) cc.readObject ();

      
        

        if ((inmsg.getType () != MessageType.ACK))
        { System.out.println ("Thread " + p.getName () + ": Invalid type!");
          System.out.println (inmsg.toString ());
          System.exit (1);
        }


        cc.close();
        
    }




}