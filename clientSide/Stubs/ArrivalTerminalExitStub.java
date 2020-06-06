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
 * This class implements the Arrival Terminal Exit Stub. This file consists on the implementation of the
 * Arrival Terminal Exit's shared region functions, but in a message approach version. In each function, a connection is established and a message is sent to the
 * Arrival Terminal Exit Interface that will execute the proper function from the original shared region itself.
 */
public class ArrivalTerminalExitStub {
	/**
	 * Arrival Terminal Exit server hostname
	 * @serialField serverHostName
	 */
	private String serverHostName;

	/**
	 * Arrival Terminal Exit server port
	 * @serialField serverPort
	 */
    private int serverPort;
	
	
	/**
	 * Arrival Terminal Exit Stub instantiation
	 */
    public ArrivalTerminalExitStub(){
        this.serverHostName = SimulatorParam.arrivalTerminalExitHostName;
        this.serverPort = SimulatorParam.arrivalTerminalExitPort;
    }
    
    public void goHome(int flight, int id) {
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
		
		//Passenger going home message, with the flight number
		outMessage = new Message (MessageType.GOINGHOME, flight, id);
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
    
    public void incCntPassengersEnd() {
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
		
		//Increment passengers at the end of the journey message
		outMessage = new Message (MessageType.INCCNTPASSENGERSEND);
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
    
    public int getCntPassengersEnd() {
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
		
		//Get passengers at the end of the journey message
		outMessage = new Message (MessageType.GETCNTPASSENGERSEND);
		con.writeObject (outMessage);
		inMessage = (Message) con.readObject ();
		
		if ((inMessage.getType () != MessageType.SENDCNTPASSENGERSEND))
        { System.out.println ("Thread " + p.getName () + ": Invalid type!");
          System.out.println (inMessage.toString ());
          System.exit (1);
        }
		
		//Message with the passenger count
		int cnt = inMessage.getCntPassengersEnd();
		//Close connection
		con.close();
		return cnt;
    }
    
    public void decCntPassengersEnd() {
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
		
		//Decrement passengers at the end of the journey message
		outMessage = new Message (MessageType.DECCNTPASSENGERSEND);
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
		outMessage = new Message (MessageType.WAKEUPALLA);
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
		
		//End of a flight message
		outMessage = new Message (MessageType.SETTIMETOWAKEUPTOFALSEA);
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
