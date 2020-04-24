package Stubs;

import AuxTools.Message;
import AuxTools.MessageType;
import AuxTools.SimulatorParam;
import clientSide.ClientCom;
import clientSide.Entities.Passenger;

public class BaggageReclaimOfficeStub {
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
    
    public BaggageReclaimOfficeStub(){
        this.serverHostName = SimulatorParam.baggageReclaimOfficeAreaHostName;
        this.serverPort = SimulatorParam.baggageReclaimOfficeAreaPort;
    }
	public void reportMissingBags(int numMissingBags) {
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
		outMessage = new Message (MessageType.REPORTBAG, numMissingBags);
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
