package serverSide.Proxys;

import AuxTools.Message;
import AuxTools.MessageException;
import serverSide.ServerCom;
import serverSide.sharedRegionInterfaces.BaggageReclaimOfficeInterface;

public class BaggageReclaimOfficeProxy extends Thread {
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

	   private BaggageReclaimOfficeInterface broInter;

	  /**
	   *  Instanciação do interface à barbearia.
	   *
	   *    @param sconi canal de comunicação
	   *    @param bShopInter interface à barbearia
	   */

	   public BaggageReclaimOfficeProxy (ServerCom sconi, BaggageReclaimOfficeInterface broInter)
	   {
	      super ("Proxy_" + getProxyId ());

	      this.sconi = sconi;
	      this.broInter = broInter;
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
	   *  Geração do identificador da instanciação.
	   *
	   *    @return identificador da instanciação
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
