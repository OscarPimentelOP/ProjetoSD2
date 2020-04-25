package clientSide.Stubs;

import AuxTools.Message;
import AuxTools.MessageType;
import AuxTools.SimulatorParam;
import clientSide.ClientCom;
import clientSide.Entities.BusDriver;
import clientSide.Entities.BusDriverState;

public class RepoStub {
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
    
    public RepoStub(){
        this.serverHostName = SimulatorParam.repoHostName;
        this.serverPort = SimulatorParam.repoPort;
    }
    
    public void setBusDriverState(BusDriverState bsd) {
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
		
		//Taking a bus message
		outMessage = new Message (MessageType.SETBUSDRIVERSTATE, bsd);
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
