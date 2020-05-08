package serverSide.Proxys;

import AuxTools.Message;
import AuxTools.MessageException;
import serverSide.ServerCom;
import serverSide.sharedRegionInterfaces.ArrivalTerminalExitInterface;

public class ArrivalTerminalExitProxy extends Thread{
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

	   private ArrivalTerminalExitInterface ateInter;

	  /**
	   *  Instanciação do interface à barbearia.
	   *
	   *    @param sconi canal de comunicação
	   *    @param bShopInter interface à barbearia
	   */

	   public ArrivalTerminalExitProxy (ServerCom sconi, ArrivalTerminalExitInterface ateInter)
	   {
	      super ("Proxy_" + getProxyId ());

	      this.sconi = sconi;
	      this.ateInter = ateInter;
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
	   *  Geração do identificador da instanciação.
	   *
	   *    @return identificador da instanciação
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
