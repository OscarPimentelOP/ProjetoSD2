package clientSide.main;

import clientSide.Stubs.*;
import clientSide.*;

import java.util.Random;

import AuxTools.*;
import clientSide.Entities.*;

public class mainPassenger {
    //Shared region stubs
    public static void main(String args[]) {
    	
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
    	
    	String serverHostName = SimulatorParam.mainPassengerName;
        int serverPortNumb = SimulatorParam.mainPassengerPort;  
    	
        ArrivalLoungeStub al = new ArrivalLoungeStub();
        ArrivalTerminalExitStub ate = new ArrivalTerminalExitStub();
        ArrivalTerminalTransferQuayStub attq = new ArrivalTerminalTransferQuayStub();
        DepartureTerminalTransferQuayStub dttq = new DepartureTerminalTransferQuayStub();
        DepartureTerminalEntranceStub dte = new DepartureTerminalEntranceStub();
        BaggageReclaimOfficeStub bro = new BaggageReclaimOfficeStub();
        BaggageCollectionPointStub bcp = new BaggageCollectionPointStub();
        
        Passenger passengers[] = new Passenger[SimulatorParam.NUM_PASSANGERS];

        ClientCom con;
        Message inMessage, outMessage;
        con = new ClientCom (serverHostName, serverPortNumb);
        while (!con.open ())
        { try
          { Thread.sleep ((long) (1000));
          }
          catch (InterruptedException e) {}
        }
        
        outMessage = new Message (MessageType.SENDPARAMS, sBags, totalNumOfBags, tripState);
        con.writeObject (outMessage);
        
        inMessage = (Message) con.readObject ();
        if (inMessage.getType() != MessageType.SENDPARAMSACK)
        { System.out.println("Simulation start: Invalid type!");
          System.out.println(inMessage.toString ());
          System.exit (1);
        }
        con.close ();
        
        for(int i = 0; i < SimulatorParam.NUM_FLIGHTS; i++){
            passengers[i] = new Passenger(PassengerState.AT_THE_DISEMBARKING_ZONE, i, numBags[i],
            		tripState[i], al, ate, attq, dttq, dte, bro, bcp);

        }

        for (Passenger p : passengers) {
            p.start();
        }

        for (Passenger p : passengers) {
            try {
                p.join();
            } catch (InterruptedException e) {
            }
        }
    
    }
}
