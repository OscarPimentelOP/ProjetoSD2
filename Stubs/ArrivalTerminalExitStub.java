package Stubs;

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
        this.serverHostName = SimulatorParam.arrivalTerminalExitAreaHostName;
        this.serverPort = SimulatorParam.arrivalTerminalExitAreaPort;
    }
    
    public void goHome(int flight) {
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
		outMessage = new Message (MessageType.GOHOME, flight);
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
