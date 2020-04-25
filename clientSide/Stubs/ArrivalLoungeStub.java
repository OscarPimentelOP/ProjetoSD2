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
    
    public char whatShouldIDo(int flight) {
		char whatshouldido = ' ';
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
		outMessage = new Message (MessageType.WHATSHOULDIDO, flight);
		con.writeObject (outMessage);
		inMessage = (Message) con.readObject ();
		
		if ((inMessage.getType () != MessageType.GOHOME) && (inMessage.getType () != MessageType.GOCOLLECTABAG) && (inMessage.getType () != MessageType.TAKEABUS))
        { System.out.println ("Thread " + p.getName () + ": Invalid type!");
          System.out.println (inMessage.toString ());
          System.exit (1);
        }
		
		switch(inMessage.getType ()) {
		case GOHOME : whatshouldido = 'H';
				      break;
		case TAKEABUS : whatshouldido = 'T';
						break;
		case GOCOLLECTABAG : whatshouldido = 'B';
							 break;
		}
		con.close ();
		return whatshouldido;
    }
    
    public char takeARest() {
    	char takearest = ' ';
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
		
		if(inMessage.getType () == MessageType.ENDPORTER) {
			takearest = 'E';
		}
		else {
			takearest = 'W';
		}
		con.close ();
		return takearest;
    }
    
    public Bag tryToCollectABag(){
    	Bag bag = null;
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
		
		if(inMessage.getType () == MessageType.BAGTOCOLLECT) {
			bag = inMessage.bags();
		}
		else {
			bag = null;
		}
		con.close ();
		return bag;
    }
    
    public void noMoreBagsToCollect() {
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
		
		if ((inMessage.getType () != MessageType.ACK))
        { System.out.println ("Thread " + p.getName () + ": Invalid type!");
          System.out.println (inMessage.toString ());
          System.exit (1);
        }
		con.close();
    }
}
