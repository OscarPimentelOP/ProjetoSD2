/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package serverSide.Proxys;

import AuxTools.Message;
import AuxTools.MessageException;
import serverSide.ServerCom;
import serverSide.sharedRegionInterfaces.DepartureTerminalEntranceInterface;

/**
 * This class implements the Departure Terminal Entrance Proxy that is a service proxy agent thread
 *  that deals with the client request and executes the operations on the Arrival Terminal Entrance
 *  shared region on its behalf.
 */
public class DepartureTerminalEntranceProxy extends Thread{
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
	   *  Departure Terminal Entrance Interface
	   *    @serialField dteInter
	   */

	   private DepartureTerminalEntranceInterface dteInter;

	 
	  /**
	   *   Departure Terminal Entrance Proxy Instantiation
	   *    @param sconi Communication channel
	   *    @param dteInter  Departure Terminal Entrance Interface
	   */

	   public DepartureTerminalEntranceProxy (ServerCom sconi, DepartureTerminalEntranceInterface dteInter)
	   {
	      super ("Proxy_" + getProxyId ());

	      this.sconi = sconi;
	      this.dteInter = dteInter;
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
	    	  outMessage = dteInter.processAndReply (inMessage);  
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
	      Class<serverSide.Proxys.DepartureTerminalEntranceProxy> cl = null;  
	                                      
	      int proxyId;           

	      try
	      { cl = (Class<serverSide.Proxys.DepartureTerminalEntranceProxy>) Class.forName ("serverSide.Proxys.DepartureTerminalEntranceProxy");
	      }
	      catch (ClassNotFoundException e)
	      { System.out.println ("Proxy dte data type not found!");
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
