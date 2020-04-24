package Stubs;

import AuxTools.Message;
import AuxTools.MessageType;
import AuxTools.SimulatorParam;
import clientSide.ClientCom;
import clientSide.Entities.Passenger;
import clientSide.Entities.BusDriver;

public class ArrivalTerminalTransferQuayStub {
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
    
    public ArrivalTerminalTransferQuayStub(){
        this.serverHostName = SimulatorParam.arrivalTerminalTransferQuayAreaHostName;
        this.serverPort = SimulatorParam.arrivalTerminalTransferQuayAreaPort;
    }
    
    public void takeABus() {
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
		
		//What should i do message with the fligh number
		outMessage = new Message (MessageType.TAKINGABUS);
		con.writeObject (outMessage);
		inMessage = (Message) con.readObject ();
		
		if ((inMessage.getType () != MessageType.ACK))
        { System.out.println ("Thread " + p.getName () + ": Invalid type!");
          System.out.println (inMessage.toString ());
          System.exit (1);
        }
		con.close();
    }
    
    
    public void enterTheBus() {
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
		
		//What should i do message with the fligh number
		outMessage = new Message (MessageType.ENTERINGTHEBUS);
		con.writeObject (outMessage);
		inMessage = (Message) con.readObject ();
		
		if ((inMessage.getType () != MessageType.ACK))
        { System.out.println ("Thread " + p.getName () + ": Invalid type!");
          System.out.println (inMessage.toString ());
          System.exit (1);
        }
		con.close();
    }
    
    public char hasDaysWorkEnded() {
    	char hasdaysworkended = ' ';
    	ClientCom con = new ClientCom (serverHostName, serverPort);
		Message inMessage, outMessage;
		BusDriver b = (BusDriver) Thread.currentThread();
		//Waits for connection
		while (!con.open ())                                    
		{ try
	        { b.sleep ((long) (10));
	        }
	        catch (InterruptedException e) {}
	    }
		
		//What should i do message with the fligh number
		outMessage = new Message (MessageType.HASDAYSWORKENDED);
		con.writeObject (outMessage);
		inMessage = (Message) con.readObject ();
		
		if ((inMessage.getType () != MessageType.ACK))
        { System.out.println ("Thread " + b.getName () + ": Invalid type!");
          System.out.println (inMessage.toString ());
          System.exit (1);
        }
		
		if(inMessage.getType () == MessageType.ENDPORTER) {
			hasdaysworkended = 'E';
		}
		else {
			hasdaysworkended = 'W';
		}
		con.close();
		return hasdaysworkended;
    }
    
    public void announcingBusBoarding() {
    	ClientCom con = new ClientCom (serverHostName, serverPort);
		Message inMessage, outMessage;
		BusDriver b = (BusDriver) Thread.currentThread();
		//Waits for connection
		while (!con.open ())                                    
		{ try
	        { b.sleep ((long) (10));
	        }
	        catch (InterruptedException e) {}
	    }
		
		//What should i do message with the fligh number
		outMessage = new Message (MessageType.ANNOUNCEBUS);
		con.writeObject (outMessage);
		inMessage = (Message) con.readObject ();
		
		if ((inMessage.getType () != MessageType.ACK))
        { System.out.println ("Thread " + b.getName () + ": Invalid type!");
          System.out.println (inMessage.toString ());
          System.exit (1);
        }
		con.close();
    }
    
    public void parkTheBus() {
    	ClientCom con = new ClientCom (serverHostName, serverPort);
		Message inMessage, outMessage;
		BusDriver b = (BusDriver) Thread.currentThread();
		//Waits for connection
		while (!con.open ())                                    
		{ try
	        { b.sleep ((long) (10));
	        }
	        catch (InterruptedException e) {}
	    }
		
		//What should i do message with the fligh number
		outMessage = new Message (MessageType.PARKATARRIVAL);
		con.writeObject (outMessage);
		inMessage = (Message) con.readObject ();
		
		if ((inMessage.getType () != MessageType.ACK))
        { System.out.println ("Thread " + b.getName () + ": Invalid type!");
          System.out.println (inMessage.toString ());
          System.exit (1);
        }
		con.close();
    }
    
}
