/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package serverSide.Proxys;

import AuxTools.Message;
import AuxTools.MessageException;
import serverSide.ServerCom;
import serverSide.sharedRegionInterfaces.DepartureTerminalTransferQuayInterface;

/**
 * This class implements the Departure Terminal Transfer Quay Proxy that 
 * 
 */
public class DepartureTerminalTransferQuayProxy extends Thread {
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
	   *  Departure Terminal Transfer Quay Interface
	   *    @serialField dttqInter
	   */

	   private DepartureTerminalTransferQuayInterface dttqInter;

	 
	  /**
	   *  Departure Terminal Transfer Quay Proxy Instantiation
	   *    @param sconi Communication channel
	   *    @param dttqInter  Departure Terminal Transfer Quay Interface
	   */

	   public DepartureTerminalTransferQuayProxy (ServerCom sconi, DepartureTerminalTransferQuayInterface dttqInter)
	   {
	      super ("Proxy_" + getProxyId ());

	      this.sconi = sconi;
	      this.dttqInter = dttqInter;
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
	    	  outMessage = dttqInter.processAndReply (inMessage); 
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
	      Class<serverSide.Proxys.DepartureTerminalTransferQuayProxy> cl = null; 
	                                
	      int proxyId;      

	      try
	      { cl = (Class<serverSide.Proxys.DepartureTerminalTransferQuayProxy>) Class.forName ("serverSide.Proxys.DepartureTerminalTransferQuayProxy");
	      }
	      catch (ClassNotFoundException e)
	      { System.out.println ("Proxy dttq data type not found!");
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
