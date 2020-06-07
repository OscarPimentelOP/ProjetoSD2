#!/bin/bash

java - classpath out/ProjetoSD2/ serverSide.main.mainRepo &
java - classpath out/ProjetoSD2/ serverSide.main.mainArrivalLounge &
java - classpath out/ProjetoSD2/ serverSide.main.mainArrivalTerminalExit &
java - classpath out/ProjetoSD2/ serverSide.main.mainArrivalTerminalTransferQuay &
java - classpath out/ProjetoSD2/ serverSide.main.mainBaggageCollectionPoint &
java - classpath out/ProjetoSD2/ serverSide.main.mainBaggageReclaimOffice &
java - classpath out/ProjetoSD2/ serverSide.main.mainDepartureTerminalEntrance &
java - classpath out/ProjetoSD2/ serverSide.main.mainDepartureTerminalTransferQuay &
java - classpath out/ProjetoSD2/ serverSide.main.mainTemporaryStorageArea &
java - classpath out/ProjetoSD2/ clientSide.main.mainPorter &
java - classpath out/ProjetoSD2/ clientSide.main.mainBusDriver &
java - classpath out/ProjetoSD2/ clientSide.main.mainPassenger 
