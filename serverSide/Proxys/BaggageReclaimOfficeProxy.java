/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package serverSide.Proxys;

import AuxTools.Message;
import AuxTools.MessageException;
import serverSide.ServerCom;
import serverSide.sharedRegionInterfaces.BaggageReclaimOfficeInterface;

/**
 * This class implements the Baggage Reclaim Office that 
 * 
 */
public class BaggageReclaimOfficeProxy extends Thread {
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
	   *  Baggage Reclaim Office Interface
	   *    @serialField broInter
	   */

	   private BaggageReclaimOfficeInterface broInter;

	  /**
	   *   Baggage Reclaim Office Proxy Instantiation
	   *    @param sconi Communication channel
	   *    @param broInter  Baggage Reclaim Office Interface
	   */

	   public BaggageReclaimOfficeProxy (ServerCom sconi, BaggageReclaimOfficeInterface broInter)
	   {
	      super ("Proxy_" + getProxyId ());

	      this.sconi = sconi;
	      this.broInter = broInter;
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
	    	  outMessage = broInter.processAndReply (inMessage);    
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
	      Class<serverSide.Proxys.BaggageReclaimOfficeProxy> cl = null;  
	                                         
	      int proxyId;           

	      try
	      { cl = (Class<serverSide.Proxys.BaggageReclaimOfficeProxy>) Class.forName ("serverSide.Proxys.BaggageReclaimOfficeProxy");
	      }
	      catch (ClassNotFoundException e)
	      { System.out.println ("Proxy bro data type not found!");
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
