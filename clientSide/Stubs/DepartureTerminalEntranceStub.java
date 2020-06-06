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
 * This class implements the Departure Terminal Entrance Stub. This file consists on the implementation of the
 * Departure Terminal Entrance's shared region functions, but in a message approach version. In each function, a connection is established and a message is sent to the
 * Departure Terminal Entrance Interface that will execute the proper function from the original shared region itself.
 */
public class DepartureTerminalEntranceStub {
	/**
	 * Departure Terminal Entrance server hostname
	 * @serialField serverHostName
	 */
	private String serverHostName;

	/**
	 * Departure Terminal Entrance server port
	 * @serialField serverPort
	 */
    private int serverPort;
	
	/**
	 * Departure Terminal Entrance Stub instantiation
	 */
    public DepartureTerminalEntranceStub(){
        this.serverHostName = SimulatorParam.departureTerminalEntranceHostName;
        this.serverPort = SimulatorParam.departureTerminalEntrancePort;
    }
	
	public void prepareNextLeg(int flight, int id) {
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
		
		//Preparing next leg message, with the flight number
		outMessage = new Message (MessageType.PREPARINGNEXTLEG, flight, id);
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
	
	public void wakeUpAll() {
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
		
		//Wake up all passengers at this terminal message
		outMessage = new Message (MessageType.WAKEUPALLD);
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
	
	public void setTimeToWakeUpToFalse() {
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
		
		//Set end of the flight message
		outMessage = new Message (MessageType.SETTIMETOWAKEUPTOFALSED);
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
