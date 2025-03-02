/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package serverSide.Proxys;

import AuxTools.Message;
import AuxTools.MessageException;
import serverSide.ServerCom;
import serverSide.sharedRegionInterfaces.ArrivalLoungeInterface;

/**
 * This class implements the Arrival Lounge Proxy that is a service proxy agent thread
 *  that deals with the client request and executes the operations on the Arrival Lounge 
 *  shared region on its behalf.
 */
public class ArrivalLoungeProxy extends Thread {
	
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
	   *  Arrival Lounge Interface
	   *    @serialField alInter
	   */
	   private ArrivalLoungeInterface alInter;

	  /**
	   *   Arrival Lounge Proxy Instantiation
	   *    @param sconi Communication channel
	   *    @param alInter Arrival Lounge Interface
	   */

	   public ArrivalLoungeProxy (ServerCom sconi, ArrivalLoungeInterface alInter)
	   {
	      super ("Proxy_" + getProxyId ());

	      this.sconi = sconi;
	      this.alInter = alInter;
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
	    	  outMessage = alInter.processAndReply (inMessage);
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
	      Class<serverSide.Proxys.ArrivalLoungeProxy> cl = null;           
	                                                  
	      int proxyId;                                

	      try
	      { cl = (Class<serverSide.Proxys.ArrivalLoungeProxy>) Class.forName ("serverSide.Proxys.ArrivalLoungeProxy");
	      }
	      catch (ClassNotFoundException e)
	      { System.out.println ("Proxy al data type not found!");
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
