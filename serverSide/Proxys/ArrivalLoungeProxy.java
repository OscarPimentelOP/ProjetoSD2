package serverSide.Proxys;

import AuxTools.Message;
import AuxTools.MessageException;
import serverSide.ServerCom;
import serverSide.sharedRegionInterfaces.ArrivalLoungeInterface;

public class ArrivalLoungeProxy extends Thread {
	
	/**
	   *  Contador de threads lançados
	   *
	   *    @serialField nProxy
	   */

	   private static int nProxy;
	   
	   
	/**
	   *  Canal de comunicação
	   *
	   *    @serialField sconi
	   */

	   private ServerCom sconi;

	  /**
	   *  Interface à barbearia
	   *
	   *    @serialField bShopInter
	   */

	   private ArrivalLoungeInterface alInter;

	  /**
	   *  Instanciação do interface à barbearia.
	   *
	   *    @param sconi canal de comunicação
	   *    @param bShopInter interface à barbearia
	   */

	   public ArrivalLoungeProxy (ServerCom sconi, ArrivalLoungeInterface alInter)
	   {
	      super ("Proxy_" + getProxyId ());

	      this.sconi = sconi;
	      this.alInter = alInter;
	   }

	  /**
	   *  Ciclo de vida do thread agente prestador de serviço.
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
	   *  Geração do identificador da instanciação.
	   *
	   *    @return identificador da instanciação
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
