/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package serverSide.Proxys;

import AuxTools.Message;
import AuxTools.MessageException;
import serverSide.ServerCom;
import serverSide.sharedRegionInterfaces.ArrivalTerminalTransferQuayInterface;

/**
 * This class implements the Arrival Terminal Transfer Quay Proxy that 
 * 
 */
public class ArrivalTerminalTransferQuayProxy extends Thread {
	/**
	   *  Launched threads counter
	   *  @serialField nProxy
	   */

	   private static int nProxy;
	   
	   
	/**
	   *  Communication channel
	   *    @serialField sconi
	   */

	   private ServerCom sconi;

	  /**
	   *  Arrival Terminal Transfer Quay Proxy Interface
	   *    @serialField attqInter
	   */

	   private ArrivalTerminalTransferQuayInterface attqInter;

	  /**
	   *   Arrival Terminal Transfer Quay Proxy Instantiation
	   *    @param sconi Communication channel
	   *    @param attqInter  Arrival Terminal Transfer Quay Interface
	   */

	   public ArrivalTerminalTransferQuayProxy (ServerCom sconi, ArrivalTerminalTransferQuayInterface attqInter)
	   {
	      super ("Proxy_" + getProxyId ());

	      this.sconi = sconi;
	      this.attqInter = attqInter;
	   }

	  /**
	   *  Service provider agent thread life cycle. 
	   */

	   @Override
	   public void run ()
	   {
		  //Input message
	      Message inMessage = null,   
	    		  //Output message
	              outMessage = null;                     

	      //Read client message
	      inMessage = (Message) sconi.readObject ();                
	      try
	      { 
	    	  //Process message
	    	  outMessage = attqInter.processAndReply (inMessage);       
	      }
	      catch (MessageException e)
	      { System.out.println ("Thread " + getName () + ": " + e.getMessage () + "!");
	      System.out.println (e.getMessageVal ().toString ());
	        System.exit (1);
	      }
	      //Send message to client
	      sconi.writeObject (outMessage); 
	      //Close communication channel
	      sconi.close ();                                
	   }

	 /**
	   * Instantiation ID generation
	   *    @return Instantiation ID
	   */
	   
	   private static int getProxyId ()
	   {
	      Class<serverSide.Proxys.ArrivalTerminalTransferQuayProxy> cl = null;         
	                                                    
	      int proxyId;                               

	      try
	      { cl = (Class<serverSide.Proxys.ArrivalTerminalTransferQuayProxy>) Class.forName ("serverSide.Proxys.ArrivalTerminalTransferQuayProxy");
	      }
	      catch (ClassNotFoundException e)
	      { System.out.println ("Proxy attq data type not found!");
	        e.printStackTrace ();
	        System.exit (1);
	      }

	      synchronized (cl)
	      { proxyId = nProxy;
	        nProxy += 1;
	      }

	      return proxyId;
	   }
	   
	   public ServerCom getScon ()
	   {
	      return sconi;
	   }
}
