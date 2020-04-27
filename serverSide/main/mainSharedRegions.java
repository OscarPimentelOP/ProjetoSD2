package serverSide.main;

import java.io.FileNotFoundException;
import java.util.Random;

import AuxTools.Bag;
import AuxTools.MemException;
import AuxTools.MemStack;
import AuxTools.SimulatorParam;
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
		   Random rand = new Random();


	        /**
	         * Number of bags per passenger and flight
	         */
	        int numBags[][] = new int[SimulatorParam.NUM_PASSANGERS][SimulatorParam.NUM_FLIGHTS];


	        /**
	         * Trip state per passenger and flight
	         */
	        char tripState[][] = new char[SimulatorParam.NUM_PASSANGERS][SimulatorParam.NUM_FLIGHTS];


	        /**
	         * Number of bags that have been lost per passenger and flight
	         */
	        int numBagsLost[][] = new int[SimulatorParam.NUM_PASSANGERS][SimulatorParam.NUM_FLIGHTS];

	        for (int i = 0; i < SimulatorParam.NUM_PASSANGERS; i++) {

	            //Initialize the number of bags per passenger and flight with probabilities
	            for (int b = 0; b < SimulatorParam.NUM_FLIGHTS; b++) {
	                int randint = rand.nextInt(101);
	                if (randint <= SimulatorParam.PROB_OF_0_BAGS) {
	                    numBags[i][b] = 0;
	                } else if (randint > SimulatorParam.PROB_OF_2_BAGS) {
	                    numBags[i][b] = 2;
	                } else {
	                    numBags[i][b] = 1;
	                }
	            }
	            /**
	             * Initialize the number of bags that have been lost
	             per passenger and flight with probabilities
	             */
	            for (int b = 0; b < SimulatorParam.NUM_FLIGHTS; b++) {
	                int randint = rand.nextInt(101);
	                if (randint <= SimulatorParam.PROB_LOSE_0_BAGS) {
	                    numBagsLost[i][b] = 0;
	                } else if (randint > SimulatorParam.PROB_LOSE_2_BAGS) {
	                    numBagsLost[i][b] = 2;
	                } else {
	                    numBagsLost[i][b] = 1;
	                }
	            }
			
				
				/*
				Initialize the trip state per passenger and flight
				(final destination-> F, transit -> T)
				*/
	            for (int t = 0; t < SimulatorParam.NUM_FLIGHTS; t++) {
	                int randint = rand.nextInt(2);
	                if (randint == 0) {
	                    tripState[i][t] = 'T';
	                } else {
	                    tripState[i][t] = 'F';
	                }
	            }
	        }


	        //Number of bags that are found (numBags - numBagsLost)
	        int numBagsFound[][] = new int[SimulatorParam.NUM_PASSANGERS][SimulatorParam.NUM_FLIGHTS];
	        //Total number of bags in the storage per flight
	        int totalNumOfBags[] = new int[SimulatorParam.NUM_FLIGHTS];
	        //Initialize number of bags that are found
	        for (int i = 0; i < SimulatorParam.NUM_PASSANGERS; i++) {
	            for (int b = 0; b < SimulatorParam.NUM_FLIGHTS; b++) {
	                numBagsFound[i][b] = numBags[i][b] - numBagsLost[i][b];
	                if (numBagsFound[i][b] < 0) numBagsFound[i][b] = 0;
	                totalNumOfBags[b] += numBagsFound[i][b];
	            }
	        }


	        //Array of stacks that represent the storage of bags per flight
	        MemStack[] sBags = new MemStack[SimulatorParam.NUM_FLIGHTS];
	        int bagId = 0;
	        for (int b = 0; b < SimulatorParam.NUM_FLIGHTS; b++) {
	            //Bags in the storage to pass to the arrival lounge per flight
	            Bag bagsToArrivalLounge[] = new Bag[totalNumOfBags[b]];
	            try {
	                sBags[b] = new MemStack<Bag>(bagsToArrivalLounge);
	                for (int i = 0; i < SimulatorParam.NUM_PASSANGERS; i++) {
	                    for (int j = 0; j < numBagsFound[i][b]; j++) {
	                        sBags[b].write(new Bag(bagId, i, tripState[i][0]));
	                        bagId++;
	                    }
	                }
	            } catch (MemException e1) {
	                System.out.println("The stack bounds were violated");
	                System.out.println("Error in AirportVConc()");
	            }
	        }
		 
	        
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
	      ArrivalLounge al = new ArrivalLounge(sBags, totalNumOfBags, tripState, bcp, repo);
	      ArrivalTerminalTransferQuay attq = new ArrivalTerminalTransferQuay(repo);
	      DepartureTerminalTransferQuay dttq = new DepartureTerminalTransferQuay(repo);
	      DepartureTerminalEntrance dte = new DepartureTerminalEntrance(al, attq, repo);
	      ArrivalTerminalExit ate = new ArrivalTerminalExit(al, attq, repo);
	      dte.setArrivalExit(ate);
	      ate.setDepartureEntrance(dte);
	      dttq.setArrivalTerminalTransferQuay(attq);
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
	        ateProxy = new ArrivalTerminalExitProxy(sconi,ateInter);
	        ateProxy.start();
	        attqProxy = new ArrivalTerminalTransferQuayProxy(sconi,attqInter);
	        attqProxy.start();
	        bcpProxy = new BaggageCollectionPointProxy(sconi,bcpInter);
	        bcpProxy.start();
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
