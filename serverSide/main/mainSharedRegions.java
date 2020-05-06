package serverSide.main;

import java.io.FileNotFoundException;
import serverSide.ServerCom;
import serverSide.Proxys.ArrivalLoungeProxy;
import serverSide.Proxys.ArrivalTerminalExitProxy;
import serverSide.Proxys.ArrivalTerminalTransferQuayProxy;
import serverSide.Proxys.BaggageCollectionPointProxy;
import serverSide.Proxys.BaggageReclaimOfficeProxy;
import serverSide.Proxys.DepartureTerminalEntranceProxy;
import serverSide.Proxys.DepartureTerminalTransferQuayProxy;
import serverSide.Proxys.RepoProxy;
import serverSide.Proxys.TemporaryStorageAreaProxy;
import serverSide.sharedRegionInterfaces.ArrivalLoungeInterface;
import serverSide.sharedRegionInterfaces.ArrivalTerminalExitInterface;
import serverSide.sharedRegionInterfaces.ArrivalTerminalTransferQuayInterface;
import serverSide.sharedRegionInterfaces.BaggageCollectionPointInterface;
import serverSide.sharedRegionInterfaces.BaggageReclaimOfficeInterface;
import serverSide.sharedRegionInterfaces.DepartureTerminalEntranceInterface;
import serverSide.sharedRegionInterfaces.DepartureTerminalTransferQuayInterface;
import serverSide.sharedRegionInterfaces.RepoInterface;
import serverSide.sharedRegionInterfaces.TemporaryStorageAreaInterface;
import serverSide.sharedRegions.ArrivalLounge;
import serverSide.sharedRegions.ArrivalTerminalExit;
import serverSide.sharedRegions.ArrivalTerminalTransferQuay;
import serverSide.sharedRegions.BaggageCollectionPoint;
import serverSide.sharedRegions.BaggageReclaimOffice;
import serverSide.sharedRegions.DepartureTerminalEntrance;
import serverSide.sharedRegions.DepartureTerminalTransferQuay;
import serverSide.sharedRegions.Repo;
import serverSide.sharedRegions.TemporaryStorageArea;

public class mainSharedRegions {
	/**
	   *  Número do port de escuta do serviço a ser prestado (4000, por defeito)
	   *
	   *    @serialField portNumb
	   */

	   private static final int portNumb = 4000;

	  /**
	   *  Programa principal.
	   */

	   public static void main (String [] args)
	   {     
	      //Connection and proxys
	      ServerCom scon, sconi;                              
	      RepoProxy repoProxy;
	      ArrivalLoungeProxy alProxy;
	      ArrivalTerminalExitProxy ateProxy;
	      ArrivalTerminalTransferQuayProxy attqProxy;
	      BaggageCollectionPointProxy bcpProxy;
	      BaggageReclaimOfficeProxy broProxy;
	      DepartureTerminalEntranceProxy dteProxy;
	      DepartureTerminalTransferQuayProxy dttqProxy;
	      TemporaryStorageAreaProxy tsaProxy;

	     /* Services */

	      scon = new ServerCom (portNumb);                    
	      scon.start ();                                     
	      Repo repo = null;
	      try {
	    	  repo = new Repo();
	      } catch (FileNotFoundException e) {
	    	  // TODO Auto-generated catch block
	    	  e.printStackTrace();
	      }                   
	      BaggageCollectionPoint bcp = new BaggageCollectionPoint(repo);
	      BaggageReclaimOffice bro = new BaggageReclaimOffice(repo);
	      TemporaryStorageArea tsa = new TemporaryStorageArea(repo);
	      ArrivalLounge al = new ArrivalLounge(bcp, repo);
	      ArrivalTerminalTransferQuay attq = new ArrivalTerminalTransferQuay(repo);
	      DepartureTerminalTransferQuay dttq = new DepartureTerminalTransferQuay(repo);
	      DepartureTerminalEntrance dte = new DepartureTerminalEntrance(al, attq, repo);
	      ArrivalTerminalExit ate = new ArrivalTerminalExit(al, attq, repo);
	      dte.setArrivalExit(ate);
	      ate.setDepartureEntrance(dte);
	      
	      RepoInterface repoInter = new RepoInterface (repo); 
	      ArrivalLoungeInterface alInter = new ArrivalLoungeInterface(al);
	      BaggageCollectionPointInterface bcpInter = new BaggageCollectionPointInterface(bcp);
	      BaggageReclaimOfficeInterface broInter = new BaggageReclaimOfficeInterface(bro);
	      TemporaryStorageAreaInterface tsaInter = new TemporaryStorageAreaInterface(tsa);
	      ArrivalTerminalTransferQuayInterface attqInter = new ArrivalTerminalTransferQuayInterface(attq);
	      DepartureTerminalTransferQuayInterface dttqInter = new DepartureTerminalTransferQuayInterface(dttq);
	      DepartureTerminalEntranceInterface dteInter = new DepartureTerminalEntranceInterface(dte);
	      ArrivalTerminalExitInterface ateInter = new ArrivalTerminalExitInterface(ate);
	      
	     


	     /* Process requests */

	      while (true)
	      { sconi = scon.accept ();
	      	repoProxy = new RepoProxy (sconi, repoInter);    
	        repoProxy.start ();
	      	alProxy = new ArrivalLoungeProxy(sconi,alInter);
	        alProxy.start();
	        attqProxy = new ArrivalTerminalTransferQuayProxy(sconi,attqInter);
	        attqProxy.start();
	        bcpProxy = new BaggageCollectionPointProxy(sconi,bcpInter);
	        bcpProxy.start();
	        ateProxy = new ArrivalTerminalExitProxy(sconi,ateInter);
	        ateProxy.start();
	        broProxy = new BaggageReclaimOfficeProxy(sconi,broInter);
	        broProxy.start();
	        dteProxy = new DepartureTerminalEntranceProxy(sconi,dteInter);
	        dteProxy.start();
	        dttqProxy = new DepartureTerminalTransferQuayProxy(sconi,dttqInter);
	        dttqProxy.start();
	        tsaProxy = new TemporaryStorageAreaProxy(sconi,tsaInter);
	        tsaProxy.start();
	      }
	   }
}
