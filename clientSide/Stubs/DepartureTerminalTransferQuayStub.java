package clientSide.Stubs;

import AuxTools.*;
import clientSide.Entities.*;
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
        ClientCom cc = new ClientCom(serverHostName,serverPort);
        
        Passenger p = (Passenger) Thread.currentThread();

        Message inmsg, outmsg; 
        
        
        outmsg = new Message(MessageType.LEAVETHEBUS);
        cc.writeObject(outmsg);
        inmsg = (Message) cc.readObject ();
        cc.open(); 
        

        if ((inmsg.getType () != MessageType.ACK))
        { System.out.println ("Thread " + p.getName () + ": Invalid type!");
          System.out.println (inmsg.toString ());
          System.exit (1);
        }

        cc.close();


    }

    public void parkTheBusAndLetPassOff(){
        ClientCom cc = new ClientCom(serverHostName,serverPort);
        
        BusDriver b = (BusDriver) Thread.currentThread();

        Message inmsg, outmsg; 
        
        
        outmsg = new Message(MessageType.PARKATDEPARTURE);
        cc.writeObject(outmsg);
        inmsg = (Message) cc.readObject ();
        cc.open(); 
        

        if ((inmsg.getType () != MessageType.ACK))
        { System.out.println ("Thread " + b.getName () + ": Invalid type!");
          System.out.println (inmsg.toString ());
          System.exit (1);
        }

        cc.close();


    }



}