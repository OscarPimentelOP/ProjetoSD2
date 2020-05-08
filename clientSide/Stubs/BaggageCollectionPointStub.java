package clientSide.Stubs;

import AuxTools.*;
import clientSide.Entities.*;
import clientSide.*;


public class BaggageCollectionPointStub {

     /**
	 * BaggageCollectionPoint server hostname
	 * @serialField serverHostName
	 */
	private String serverHostName;

	/**
	 * BaggageCollectionPoint server port
	 * @serialField serverPort
	 */
    private int serverPort;


    public BaggageCollectionPointStub(){
        this.serverHostName = SimulatorParam.baggageCollectionPointHostName;
        this.serverPort = SimulatorParam.baggageCollectionPointPort;

    }

    public void carryItToAppropriateStore(Bag bag){
    	//Open connection
        ClientCom cc = new ClientCom(serverHostName,serverPort);
        Porter p = (Porter) Thread.currentThread();
        Message inmsg, outmsg; 

        while (!cc.open ())                                    
		{ try
	        { p.sleep ((long) (10));
	        }
	        catch (InterruptedException e) {}
	    }

        //Carrying bag to baggage collection point message
        outmsg = new Message(MessageType.CARRYBAGTOBAGPOINT, bag);
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


    public boolean goCollectABag(int id){
    	//Open connection
        ClientCom cc = new ClientCom(serverHostName,serverPort);
        Passenger p = (Passenger) Thread.currentThread();
        Message inmsg, outmsg; 
        boolean hasBag;

        while (!cc.open ())                                    
		{ try
	        { p.sleep ((long) (10));
	        }
	        catch (InterruptedException e) {}
	    }

        //Passenger collecting a bag message
        outmsg = new Message(MessageType.GOINGCOLLECTABAG, id);
        cc.writeObject(outmsg);
        inmsg = (Message) cc.readObject ();


        if ((inmsg.getType () != MessageType.BAGOK && inmsg.getType () != MessageType.BAGNOTOK)) // ||?
        { System.out.println ("Thread " + p.getName () + ": Invalid type!");
          System.out.println (inmsg.toString ());
          System.exit (1);
        }
        
        //Bag at the convoy belt or missing
        hasBag = (inmsg.getType () == MessageType.BAGOK) ? true : false;
        //Close connection
        cc.close();

        return hasBag;
    }
    
    public void setMoreBags(boolean moreBags) {
    	//Open connection
    	ClientCom cc = new ClientCom(serverHostName,serverPort);
        Thread t = Thread.currentThread();
        Message inmsg, outmsg; 
        boolean hasBag;

        while (!cc.open ())                                    
		{ try
	        { t.sleep ((long) (10));
	        }
	        catch (InterruptedException e) {}
	    }

        //Porter warns passengers that the plane hold is empty message
        outmsg = new Message(MessageType.SETMOREBAGS, moreBags);
        cc.writeObject(outmsg);
        inmsg = (Message) cc.readObject ();

        //Message OK
        if ((inmsg.getType () != MessageType.ACK)) 
        { System.out.println ("Thread " + t.getName () + ": Invalid type!");
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