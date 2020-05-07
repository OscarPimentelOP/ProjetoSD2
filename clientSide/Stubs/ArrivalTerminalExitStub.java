package clientSide.Stubs;

import AuxTools.Message;
import AuxTools.MessageType;
import AuxTools.SimulatorParam;
import clientSide.ClientCom;
import clientSide.Entities.Passenger;

public class ArrivalTerminalExitStub {
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
    
    public ArrivalTerminalExitStub(){
        this.serverHostName = SimulatorParam.arrivalTerminalExitHostName;
        this.serverPort = SimulatorParam.arrivalTerminalExitPort;
    }
    
    public void goHome(int flight, int id) {
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
		
		if ((inMessage.getType () != MessageType.ACK))
        { System.out.println ("Thread " + p.getName () + ": Invalid type!");
          System.out.println (inMessage.toString ());
          System.exit (1);
        }
		con.close();
    }
    
    public void incCntPassengersEnd() {
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
		
		//Preparing next leg message, with the flight number
		outMessage = new Message (MessageType.INCCNTPASSENGERSEND);
		con.writeObject (outMessage);
		inMessage = (Message) con.readObject ();
		
		if ((inMessage.getType () != MessageType.ACK))
        { System.out.println ("Thread " + p.getName () + ": Invalid type!");
          System.out.println (inMessage.toString ());
          System.exit (1);
        }
		con.close();
    }
    
    public int getCntPassengersEnd() {
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
		
		//Preparing next leg message, with the flight number
		outMessage = new Message (MessageType.GETCNTPASSENGERSEND);
		con.writeObject (outMessage);
		inMessage = (Message) con.readObject ();
		
		if ((inMessage.getType () != MessageType.SENDCNTPASSENGERSEND))
        { System.out.println ("Thread " + p.getName () + ": Invalid type!");
          System.out.println (inMessage.toString ());
          System.exit (1);
        }
		int cnt = inMessage.getCntPassengersEnd();
		con.close();
		return cnt;
    }
    
    public void decCntPassengersEnd() {
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
		
		//Preparing next leg message, with the flight number
		outMessage = new Message (MessageType.DECCNTPASSENGERSEND);
		con.writeObject (outMessage);
		inMessage = (Message) con.readObject ();
		
		if ((inMessage.getType () != MessageType.ACK))
        { System.out.println ("Thread " + p.getName () + ": Invalid type!");
          System.out.println (inMessage.toString ());
          System.exit (1);
        }
		con.close();
    }
    
    public void wakeUpAll() {
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
		
		//Preparing next leg message, with the flight number
		outMessage = new Message (MessageType.WAKEUPALLA);
		con.writeObject (outMessage);
		inMessage = (Message) con.readObject ();
		
		if ((inMessage.getType () != MessageType.ACK))
        { System.out.println ("Thread " + p.getName () + ": Invalid type!");
          System.out.println (inMessage.toString ());
          System.exit (1);
        }
		con.close();
    }
	
	public void setTimeToWakeUpToFalse() {
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
		
		//Preparing next leg message, with the flight number
		outMessage = new Message (MessageType.SETTIMETOWAKEUPTOFALSEA);
		con.writeObject (outMessage);
		inMessage = (Message) con.readObject ();
		
		if ((inMessage.getType () != MessageType.ACK))
        { System.out.println ("Thread " + p.getName () + ": Invalid type!");
          System.out.println (inMessage.toString ());
          System.exit (1);
        }
		con.close();
	}
}
