/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package clientSide.Stubs;

import AuxTools.Message;
import AuxTools.MessageType;
import AuxTools.SimulatorParam;
import clientSide.ClientCom;
import clientSide.Entities.Passenger;

/**
 * This class implements the Baggage Reclaim Office Stub. This file consists on the implementation of the
 * Baggage Reclaim Office's shared region functions, but in a message approach version. In each function, a connection is established and a message is sent to the
 * Baggage Reclaim Office Interface that will execute the proper function from the original shared region itself.
 */
public class BaggageReclaimOfficeStub {
	/**
	 * Baggage Reclaim Office server hostname
	 * @serialField serverHostName
	 */
	private String serverHostName;

	/**
	 * Baggage Reclaim Office server port
	 * @serialField serverPort
	 */
    private int serverPort;
	
	
	/**
	 * Baggage Reclaim Office Stub instantiation
	 */
    public BaggageReclaimOfficeStub(){
        this.serverHostName = SimulatorParam.baggageReclaimOfficeHostName;
        this.serverPort = SimulatorParam.baggageReclaimOfficePort;
    }
	public void reportMissingBags(int numMissingBags, int id) {
		//Open connection
		ClientCom con = new ClientCom (serverHostName, serverPort);
		Message inMessage, outMessage;
		Passenger p = (Passenger) Thread.currentThread();
		//Waits for connection
		while (!con.open ())                                    
		{ try
	        { p.sleep ((long) (10));
	        }
	        catch (InterruptedException e) {}
	    }
		
		//Reporting a missing a bag message, with the number of missing bags
		outMessage = new Message (MessageType.REPORTBAG, numMissingBags, id);
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
