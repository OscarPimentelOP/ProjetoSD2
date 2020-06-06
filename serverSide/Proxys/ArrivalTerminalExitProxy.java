/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package serverSide.Proxys;

import AuxTools.Message;
import AuxTools.MessageException;
import serverSide.ServerCom;
import serverSide.sharedRegionInterfaces.ArrivalTerminalExitInterface;

/**
 * This class implements the Arrival Terminal Exit Proxy that 
 * 
 */
public class ArrivalTerminalExitProxy extends Thread{
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
	   *  Arrival Terminal Exit Interface
	   *    @serialField ateInter
	   */
	   private ArrivalTerminalExitInterface ateInter;

	  /**
	   *   Arrival Terminal Exit Proxy Instantiation
	   *    @param sconi Communication channel
	   *    @param ateInter  Arrival Terminal Exit Interface
	   */

	   public ArrivalTerminalExitProxy (ServerCom sconi, ArrivalTerminalExitInterface ateInter)
	   {
	      super ("Proxy_" + getProxyId ());

	      this.sconi = sconi;
	      this.ateInter = ateInter;
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
	    	  outMessage = ateInter.processAndReply (inMessage);        
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
	      Class<serverSide.Proxys.ArrivalTerminalExitProxy> cl = null;             
	                                                          
	      int proxyId;                         

	      try
	      { cl = (Class<serverSide.Proxys.ArrivalTerminalExitProxy>) Class.forName ("serverSide.Proxys.ArrivalTerminalExitProxy");
	      }
	      catch (ClassNotFoundException e)
	      { System.out.println ("Proxy ate data type not found!");
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
