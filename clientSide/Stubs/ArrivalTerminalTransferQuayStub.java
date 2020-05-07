package clientSide.Stubs;

import AuxTools.MemException;
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
        this.serverHostName = SimulatorParam.arrivalTerminalTransferQuayHostName;
        this.serverPort = SimulatorParam.arrivalTerminalTransferQuayPort;
    }
    
    public void takeABus(int id) {
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
		
		//Taking a bus message
		outMessage = new Message (MessageType.TAKINGABUS, id);
		con.writeObject (outMessage);
		inMessage = (Message) con.readObject ();
		
		if ((inMessage.getType () != MessageType.ACK))
        { System.out.println ("Thread " + p.getName () + ": Invalid type!");
          System.out.println (inMessage.toString ());
          System.exit (1);
        }
		con.close();
    }
    
    
    public void enterTheBus(int id) {
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
		
		//Entering the bus
		outMessage = new Message (MessageType.ENTERINGTHEBUS, id);
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
		
		//Has days work ended message
		outMessage = new Message (MessageType.HASDAYSWORKENDED);
		con.writeObject (outMessage);
		inMessage = (Message) con.readObject ();
		
		if ((inMessage.getType () == MessageType.ENDBUSDRIVER && inMessage.getType () == MessageType.KEEPWORKINGBUSDRIVER))
        { System.out.println ("Thread " + b.getName () + ": Invalid type!");
          System.out.println (inMessage.toString ());
          System.exit (1);
        }
		
		if(inMessage.getType () == MessageType.ENDBUSDRIVER) {
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
		
		//Announcing the bus message
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
		
		//Parking the bus on the arrival terminal message
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
    
    public void setEndOfWork() {
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
		
		//Parking the bus on the arrival terminal message
		outMessage = new Message (MessageType.SETENDOFWORKBUSDRIVER);
		con.writeObject (outMessage);
		inMessage = (Message) con.readObject ();
		
		if ((inMessage.getType () != MessageType.ACK))
        { System.out.println ("Thread " + p.getName () + ": Invalid type!");
          System.out.println (inMessage.toString ());
          System.exit (1);
        }
		con.close();
    }
    
    public void readFromBus() {
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
		
		//Parking the bus on the arrival terminal message
		outMessage = new Message (MessageType.READFROMBUS);
		con.writeObject (outMessage);
		inMessage = (Message) con.readObject ();
		
		if ((inMessage.getType () != MessageType.ACK))
        { System.out.println ("Thread " + p.getName () + ": Invalid type!");
          System.out.println (inMessage.toString ());
          System.exit (1);
        }
		con.close();
    }
    
    public void decCntPassengersInBus() {
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
		
		//Parking the bus on the arrival terminal message
		outMessage = new Message (MessageType.DECCNTPASSENGERSINBUS);
		con.writeObject (outMessage);
		inMessage = (Message) con.readObject ();
		
		if ((inMessage.getType () != MessageType.ACK))
        { System.out.println ("Thread " + p.getName () + ": Invalid type!");
          System.out.println (inMessage.toString ());
          System.exit (1);
        }
		con.close();
    }
    
    public int getCntPassengersInBus() {
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
		
		//Parking the bus on the arrival terminal message
		outMessage = new Message (MessageType.GETCNTPASSENGERSINBUS);
		con.writeObject (outMessage);
		inMessage = (Message) con.readObject ();
		
		if ((inMessage.getType () != MessageType.SENDCNTPASSENGERSINBUS))
        { System.out.println ("Thread " + p.getName () + ": Invalid type!");
          System.out.println (inMessage.toString ());
          System.exit (1);
        }
		
		int cnt = inMessage.getCntPassengersInBus();
		
		con.close();
		
		return cnt;
    }
}
