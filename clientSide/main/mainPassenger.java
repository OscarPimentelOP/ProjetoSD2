package clientSide.main;

import Stubs.*;
import clientSide.*;
import AuxTools.*;
import clientSide.Entities.*;

public class mainPassenger {
    //Shared region stubs
    public static void main(String args[]) {
        ArrivalLoungeStub al = new ArrivalLoungeStub();
        ArrivalTerminalExitStub ate = new ArrivalTerminalExitStub();
        ArrivalTerminalTransferQuayStub attq = new ArrivalTerminalTransferQuayStub();
        DepartureTerminalTransferQuayStub dttq = new DepartureTerminalTransferQuayStub();
        DepartureTerminalEntranceStub dte = new DepartureTerminalEntranceStub();
        BaggageReclaimOfficeStub bro = new BaggageReclaimOfficeStub();
        BaggageCollectionPointStub bcp = new BaggageCollectionPointStub();
        
        Passenger passengers[] = new Passenger[SimulatorParam.NUM_PASSANGERS];

        for(int i = 0; i < SimulatorParam.NUM_FLIGHTS; i++){
            passengers[i] = new Passenger(PassengerState.AT_THE_DISEMBARKING_ZONE, i, nb,
                   ts, al, ate, attq, dttq, dte, bro, bcp);

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
