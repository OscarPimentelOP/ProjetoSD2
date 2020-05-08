package clientSide.Stubs;

import AuxTools.Bag;
import AuxTools.Message;
import AuxTools.MessageType;
import AuxTools.SimulatorParam;
import clientSide.ClientCom;
import clientSide.Entities.Passenger;
import clientSide.Entities.Porter;

public class ArrivalLoungeStub {
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
    
    public ArrivalLoungeStub(){
        this.serverHostName = SimulatorParam.arrivalLoungeHostName;
        this.serverPort = SimulatorParam.arrivalLoungePort;
    }
    
    public char whatShouldIDo(int flight, int id, char tripState, int numBags) {
		char whatshouldido = ' ';
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
		
		//What should i do message with the flight number
		outMessage = new Message (MessageType.WHATSHOULDIDO, flight, id, tripState, numBags);
		con.writeObject (outMessage);
		inMessage = (Message) con.readObject ();
		
		if ((inMessage.getType () != MessageType.GOHOME) && (inMessage.getType () != MessageType.GOCOLLECTABAG) && (inMessage.getType () != MessageType.TAKEABUS))
        { System.out.println ("Thread " + p.getName () + ": Invalid type!");
          System.out.println (inMessage.toString ());
          System.exit (1);
        }
		
		switch(inMessage.getType ()) {
		//Passenger goes home
		case GOHOME : whatshouldido = 'H';
				      break;
		//Passenger will take a bus
		case TAKEABUS : whatshouldido = 'T';
						break;
		//Passenger will collect a bag at convoy belt
		case GOCOLLECTABAG : whatshouldido = 'B';
							 break;
		}
		//Close connection
		con.close ();
		return whatshouldido;
    }
    
    public char takeARest() {
    	char takearest = ' ';
    	//Open connection
		ClientCom con = new ClientCom (serverHostName, serverPort);
		Message inMessage, outMessage;
		Porter p = (Porter) Thread.currentThread();
		//Waits for connection
		while (!con.open ())                                    
		{ try
	        { p.sleep ((long) (10));
	        }
	        catch (InterruptedException e) {}
	    }
		
		//Take a rest message
		outMessage = new Message (MessageType.TAKEAREST);
		con.writeObject (outMessage);
		inMessage = (Message) con.readObject ();
		
		if ((inMessage.getType () != MessageType.ENDPORTER) && (inMessage.getType () != MessageType.KEEPWORKINGPORTER))
        { System.out.println ("Thread " + p.getName () + ": Invalid type!");
          System.out.println (inMessage.toString ());
          System.exit (1);
        }
		
		//Porter end operations
		if(inMessage.getType () == MessageType.ENDPORTER) {
			takearest = 'E';
		}
		//Porter keeps working
		else {
			takearest = 'W';
		}
		//Close connection
		con.close ();
		return takearest;
    }
    
    public Bag tryToCollectABag(){
    	Bag bag = null;
    	//Open connection
		ClientCom con = new ClientCom (serverHostName, serverPort);
		Message inMessage, outMessage;
		Porter p = (Porter) Thread.currentThread();
		//Waits for connection
		while (!con.open ())                                    
		{ try
	        { p.sleep ((long) (10));
	        }
	        catch (InterruptedException e) {}
	    }
		
		//Try to collect a bag message
		outMessage = new Message (MessageType.TRYTOCOLLECTABAG);
		con.writeObject (outMessage);
		inMessage = (Message) con.readObject ();
		
		if ((inMessage.getType () != MessageType.NOMOREBAGSATPLANEHOLD) && (inMessage.getType () != MessageType.BAGTOCOLLECT))
        { System.out.println ("Thread " + p.getName () + ": Invalid type!");
          System.out.println (inMessage.toString ());
          System.exit (1);
        }
		
		//Bag at the plane hold
		if(inMessage.getType () == MessageType.BAGTOCOLLECT) {
			bag = inMessage.bags();
		}
		//No more bags at the plane hold
		else {
			bag = null;
		}
		//Close connection
		con.close ();
		return bag;
    }
    
    public void noMoreBagsToCollect() {
    	//Open connection
    	ClientCom con = new ClientCom (serverHostName, serverPort);
		Message inMessage, outMessage;
		Porter p = (Porter) Thread.currentThread();
		//Waits for connection
		while (!con.open ())                                    
		{ try
	        { p.sleep ((long) (10));
	        }
	        catch (InterruptedException e) {}
	    }
		
		//Taking a bus message
		outMessage = new Message (MessageType.NOMOREBAGSTOCOLLECT);
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
    
    public void setEndOfWork() {
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
		
		//Set end of work porter message
		outMessage = new Message (MessageType.SETENDOFWORKPORTER);
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
