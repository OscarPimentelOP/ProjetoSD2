/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package serverSide.Proxys;

import AuxTools.Message;
import AuxTools.MessageException;
import serverSide.ServerCom;
import serverSide.sharedRegionInterfaces.TemporaryStorageAreaInterface;

/**
 * This class implements the Temporary Storage Area Proxy that is a service proxy agent thread
 *  that deals with the client request and executes the operations on the Temporary Storage Area
 *  shared region on its behalf.
 */
public class TemporaryStorageAreaProxy extends Thread {
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
	   *  Temporary Storage Area Interface
	   *    @serialField tsaInter
	   */

	   private TemporaryStorageAreaInterface tsaInter;

	  
	  /**
	   *   Temporary Storage Area Proxy Instantiation
	   *    @param sconi Communication channel
	   *    @param tsaInter  Temporary Storage Area Interface
	   */

	   public TemporaryStorageAreaProxy (ServerCom sconi, TemporaryStorageAreaInterface tsaInter)
	   {
	      super ("Proxy_" + getProxyId ());

	      this.sconi = sconi;
	      this.tsaInter = tsaInter;
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
	    	  outMessage = tsaInter.processAndReply (inMessage);
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
	      Class<serverSide.Proxys.TemporaryStorageAreaProxy> cl = null;      
	                                                         
	      int proxyId;                                        

	      try
	      { cl = (Class<serverSide.Proxys.TemporaryStorageAreaProxy>) Class.forName ("serverSide.Proxys.TemporaryStorageAreaProxy");
	      }
	      catch (ClassNotFoundException e)
	      { System.out.println ("Proxy tsa data type not found!");
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
