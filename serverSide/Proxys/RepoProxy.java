/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package serverSide.Proxys;

import AuxTools.Message;
import AuxTools.MessageException;
import serverSide.ServerCom;
import serverSide.sharedRegionInterfaces.RepoInterface;

/**
 * This class implements the Repository Proxy that 
 * 
 */
public class RepoProxy extends Thread {
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
	   *  Repository Interface
	   *    @serialField repoInter
	   */

	   private RepoInterface repoInter;

	 
	  /**
	   *    Repository Proxy Instantiation
	   *    @param sconi Communication channel
	   *    @param repoInter  Repository Interface
	   */

	   public RepoProxy (ServerCom sconi, RepoInterface repoInter)
	   {
	      super ("Proxy_" + getProxyId ());

	      this.sconi = sconi;
	      this.repoInter = repoInter;
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
	    	  outMessage = repoInter.processAndReply (inMessage);
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
	      Class<serverSide.Proxys.RepoProxy> cl = null;  
	                        
	      int proxyId;         

	      try
	      { cl = (Class<serverSide.Proxys.RepoProxy>) Class.forName ("serverSide.Proxys.RepoProxy");
	      }
	      catch (ClassNotFoundException e)
	      { System.out.println ("Proxy repo data type not found!");
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
