package serverSide.Proxys;

import AuxTools.Message;
import AuxTools.MessageException;
import serverSide.ServerCom;
import serverSide.sharedRegionInterfaces.ArrivalTerminalTransferQuayInterface;

public class ArrivalTerminalTransferQuayProxy extends Thread {
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

	   private ArrivalTerminalTransferQuayInterface attqInter;

	  /**
	   *  Instanciação do interface à barbearia.
	   *
	   *    @param sconi canal de comunicação
	   *    @param bShopInter interface à barbearia
	   */

	   public ArrivalTerminalTransferQuayProxy (ServerCom sconi, ArrivalTerminalTransferQuayInterface attqInter)
	   {
	      super ("Proxy_" + getProxyId ());

	      this.sconi = sconi;
	      this.attqInter = attqInter;
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
	    	  outMessage = attqInter.processAndReply (inMessage);       
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
	      Class<serverSide.Proxys.ArrivalTerminalTransferQuayProxy> cl = null;         
	                                                    
	      int proxyId;                               

	      try
	      { cl = (Class<serverSide.Proxys.ArrivalTerminalTransferQuayProxy>) Class.forName ("serverSide.Proxys.ArrivalTerminalTransferQuayProxy");
	      }
	      catch (ClassNotFoundException e)
	      { System.out.println ("Proxy attq data type not found!");
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
