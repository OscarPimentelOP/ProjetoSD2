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
	      Message inMessage = null,                                      // mensagem de entrada
	              outMessage = null;                      // mensagem de saída

	      inMessage = (Message) sconi.readObject ();                     // ler pedido do cliente
	      try
	      { outMessage = ateInter.processAndReply (inMessage);         // processá-lo
	      }
	      catch (MessageException e)
	      { System.out.println ("Thread " + getName () + ": " + e.getMessage () + "!");
	      System.out.println (e.getMessageVal ().toString ());
	        System.exit (1);
	      }
	      sconi.writeObject (outMessage);                                // enviar resposta ao cliente
	      sconi.close ();                                                // fechar canal de comunicação
	   }

	  /**
	   *  Geração do identificador da instanciação.
	   *
	   *    @return identificador da instanciação
	   */
	   
	   private static int getProxyId ()
	   {
	      Class<serverSide.Proxys.ArrivalTerminalExitProxy> cl = null;             // representação do tipo de dados ClientProxy na máquina
	                                                           //   virtual de Java
	      int proxyId;                                         // identificador da instanciação

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
}
