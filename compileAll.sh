#!/bin/bash

javac AuxTools/Message.java
echo "Compiling Shared Regions' Mains............."
javac serverSide/main/mainRepo.java
javac serverSide/main/mainArrivalLounge.java
javac serverSide/main/mainArrivalTerminalExit.java 
javac serverSide/main/mainArrivalTerminalTransferQuay.java 
javac serverSide/main.mainBaggageCollectionPoint.java 
javac serverSide/main/mainBaggageReclaimOffice.java 
javac serverSide/main/mainDepartureTerminalEntrance.java 
javac serverSide/main/mainDepartureTerminalTransferQuay.java 
javac serverSide/main/mainTemporaryStorageArea.java 
echo "Compiling Entities' Mains..................."
javac clientSide/main/mainPorter.java  
javac clientSide/main/mainBusDriver.java 
javac clientSide/main/mainPassenger.java 