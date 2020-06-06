/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package clientSide.Stubs;

import AuxTools.*;
import clientSide.Entities.*;
import clientSide.*;


/**
 * This class implements the Departure Terminal Transfer Quay Stub. This file consists on the implementation of the
 * Departure Terminal Transfer Quay's shared region functions, but in a message approach version. In each function, a connection is established and a message is sent to the
 * Departure Terminal Transfer Quay Interface that will execute the proper function from the original shared region itself.
 */
public class DepartureTerminalTransferQuayStub {

    /**
	 * Departure Terminal Transfer Quay server hostname
	 * @serialField serverHostName
	 */
	private String serverHostName;

	/**
	 * Departure Terminal Transfer Quay server port
	 * @serialField serverPort
	 */
    private int serverPort;

    /**
	 * Departure Terminal Transfer Quay Stub instantiation
	 */
    public DepartureTerminalTransferQuayStub(){

        this.serverHostName = SimulatorParam.departureTerminalTransferQuayHostName;
        this.serverPort = SimulatorParam.departureTerminalTransferQuayPort;
    }


    public void leaveTheBus(int id){
    	//Open connection
        ClientCom cc = new ClientCom(serverHostName,serverPort);
        
        Passenger p = (Passenger) Thread.currentThread();

        Message inmsg, outmsg; 
        
        while (!cc.open ())                                    
		{ try
	        { p.sleep ((long) (10));
	        }
	        catch (InterruptedException e) {}
	    }
        
        //Leave the bus message
        outmsg = new Message(MessageType.LEAVETHEBUS, id);
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

    public void parkTheBusAndLetPassOff(){
    	//Open connection
        ClientCom cc = new ClientCom(serverHostName,serverPort);
        
        BusDriver b = (BusDriver) Thread.currentThread();

        Message inmsg, outmsg; 
        
        while (!cc.open ())                                    
		{ try
	        { b.sleep ((long) (10));
	        }
	        catch (InterruptedException e) {}
	    }
        
        //Park the bus and let the passengers off message
        outmsg = new Message(MessageType.PARKATDEPARTURE);
        cc.writeObject(outmsg);
        inmsg = (Message) cc.readObject ();
        
        //Message OK
        if ((inmsg.getType () != MessageType.ACK))
        { System.out.println ("Thread " + b.getName () + ": Invalid type!");
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