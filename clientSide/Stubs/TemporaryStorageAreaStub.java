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
    	//Open connection
        ClientCom cc = new ClientCom(serverHostName,serverPort);
        Porter p = (Porter) Thread.currentThread();
        Message inmsg, outmsg; 
        
        while (!cc.open ())                                    
		{ try
	        { p.sleep ((long) (10));
	        }
	        catch (InterruptedException e) {}
	    }
        
        //Carrying bag to temporary storage area message
        outmsg = new Message(MessageType.CARRYBAGTOTEMPSTORE, bag);
        cc.writeObject(outmsg);
        inmsg = (Message) cc.readObject ();

        //Message OK
        if ((inmsg.getType () != MessageType.ACK))
        { System.out.println ("Thread " + p.getName () + ": Invalid type!");
          System.out.println (inmsg.toString ());
          System.exit (1);
        }
        //Close connection
        cc.close();
    }

    public void shutServer() {
    	//Open connection
    	ClientCom con = new ClientCom (serverHostName, serverPort);
		Message inMessage, outMessage;
		Thread p = (Thread) Thread.currentThread();
		//Waits for connection
		while (!con.open ())                                    
		{ try
	        { p.sleep ((long) (10));
	        }
	        catch (InterruptedException e) {}
	    }
		
		//Shut down server message
		outMessage = new Message (MessageType.SHUTDOWN);
		con.writeObject (outMessage);
		inMessage = (Message) con.readObject ();
		
		//Message OK
		if ((inMessage.getType () != MessageType.ACK))
        { System.out.println ("Thread " + p.getName () + ": Invalid type!");
          System.out.println (inMessage.toString ());
          System.exit (1);
        }
		//Close connection
		con.close();
    }
}