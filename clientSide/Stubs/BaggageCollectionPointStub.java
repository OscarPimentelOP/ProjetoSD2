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
        ClientCom cc = new ClientCom(serverHostName,serverPort);
        Porter p = (Porter) Thread.currentThread();
        Message inmsg, outmsg; 

        while (!cc.open ())                                    
		{ try
	        { p.sleep ((long) (10));
	        }
	        catch (InterruptedException e) {}
	    }

        outmsg = new Message(MessageType.CARRYBAGTOBAGPOINT, bag);
        cc.writeObject(outmsg);
        inmsg = (Message) cc.readObject ();


        if ((inmsg.getType () != MessageType.ACK))
        { System.out.println ("Thread " + p.getName () + ": Invalid type!");
          System.out.println (inmsg.toString ());
          System.exit (1);
        }


        cc.close();
        
    }


    public boolean goCollectABag(int id){
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

        outmsg = new Message(MessageType.GOINGCOLLECTABAG, id);
        cc.writeObject(outmsg);
        inmsg = (Message) cc.readObject ();


        if ((inmsg.getType () != MessageType.BAGOK && inmsg.getType () != MessageType.BAGNOTOK)) // ||?
        { System.out.println ("Thread " + p.getName () + ": Invalid type!");
          System.out.println (inmsg.toString ());
          System.exit (1);
        }

        hasBag = (inmsg.getType () == MessageType.BAGOK) ? true : false;
        cc.close();

        return hasBag;
    }
    
    public void setMoreBags(boolean moreBags) {
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

        outmsg = new Message(MessageType.SETMOREBAGS, moreBags);
        cc.writeObject(outmsg);
        inmsg = (Message) cc.readObject ();


        if ((inmsg.getType () != MessageType.ACK)) 
        { System.out.println ("Thread " + t.getName () + ": Invalid type!");
          System.out.println (inmsg.toString ());
          System.exit (1);
        }

        cc.close();
    }

}