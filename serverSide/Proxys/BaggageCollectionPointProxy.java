/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package serverSide.Proxys;

import AuxTools.Message;
import AuxTools.MessageException;
import serverSide.ServerCom;
import serverSide.sharedRegionInterfaces.BaggageCollectionPointInterface;

/**
 * This class implements the Baggage Collection Point Proxy that 
 * 
 */
public class BaggageCollectionPointProxy extends Thread {
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
	   *  Baggage Collection Point Interface
	   *    @serialField bcpInter
	   */

	   private BaggageCollectionPointInterface bcpInter;

	  /**
	   *   Baggage Collection Point Proxy Instantiation
	   *    @param sconi Communication channel
	   *    @param bcpInter  Baggage Collection Point Interface
	   */

	   public BaggageCollectionPointProxy (ServerCom sconi, BaggageCollectionPointInterface bcpInter)
	   {
	      super ("Proxy_" + getProxyId ());	

	      this.sconi = sconi;
	      this.bcpInter = bcpInter;
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
	    	  outMessage = bcpInter.processAndReply (inMessage);   
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
	      Class<serverSide.Proxys.BaggageCollectionPointProxy> cl = null;       
	                                               
	      int proxyId;           

	      try
	      { cl = (Class<serverSide.Proxys.BaggageCollectionPointProxy>) Class.forName ("serverSide.Proxys.BaggageCollectionPointProxy");
	      }
	      catch (ClassNotFoundException e)
	      { System.out.println ("Proxy bcp data type not found!");
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
