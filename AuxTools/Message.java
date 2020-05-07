package AuxTools;

import java.io.Serializable;

import clientSide.Entities.BusDriverState;
import clientSide.Entities.PassengerState;
import clientSide.Entities.PorterState;

public class Message implements Serializable{
	/**
	 *  Chave de serialização
	 *    @serialField serialVersionUID
	 */

	private static final long serialVersionUID = 1001L;
   
	//Tipos de mensagens
	private MessageType msgType = MessageType.NO_MESSAGE;
   
   //Identifica��o do prof
   //private int passengerId;
   
   //Numero do voo
   private int flight = -1;
   
   //Mala
   private Bag bag = null;
   
   //Malas reportadas
   private int reportedBags = -1;
   
   private BusDriverState bds;
   
   private char[][] tripState;
   
   private int[] numOfBagsPerFlight;
   
   private MemStack<Bag> sBags[];
   
   private boolean moreBags;
   
   private int cntPassengerEnd;
   
   private int cntPassengersInBus;
   
   private PorterState ps;
   
   private int numOfBagsInteger;
   
   private int passengersInTransit;

   private int id;

	private int numBagsAtBeggining;
	
	private String destType;
	
	private PassengerState pgs;
	
	private int finalDest;
	
	private int passengersOnTheQueue;
	
	private int passengerOnTheBus;
	
	private int numOfBagsInConveyBelt;
	
	private int missingBagsTotal;
	
	private int numOfBagsAtStoreroom;
	
	private char indTripState;
	   
   public Message (MessageType type)
   {
      msgType = type;
   }
   
   public Message(MessageType type, boolean moreBags) {
	   msgType=type;
	   this.moreBags = moreBags;
   }
   
   public Message(MessageType type, MemStack<Bag> sBags[], int[] numOfBagsPerFlight, char[][] tripState) {
	   msgType = type;
	   this.tripState = tripState;
	   this.sBags = sBags;
	   this.numOfBagsPerFlight = numOfBagsPerFlight;
   }
   
   public Message(MessageType type, BusDriverState bds) {
	   msgType = type;
	   this.bds = bds;
   }
   
   public Message (MessageType type, int f)
   {
      msgType = type;
      if(msgType==MessageType.SENDCNTPASSENGERSEND) {
    	  this.cntPassengerEnd = f;
      }
      else if(msgType==MessageType.SENDCNTPASSENGERSINBUS) {
    	  this.cntPassengersInBus = f;
      }
      else if(msgType==MessageType.SETTINGPASSANGERSONTRANSIT) {
    	  this.passengersInTransit = f;
      }
      else if(msgType==MessageType.SETTINGPASSANGERFINALDESTINATION) {
    	  this.finalDest = f;
      }
      else if(type == MessageType.SETTINGNUMOFBAGSINTHECB) {
    	  this.numOfBagsInConveyBelt = f;
      }
      else if(type == MessageType.SETTINGNUMOFBAGSCOLLECTED) {
    	  this.id = f;
      }
      else if(type == MessageType.SETTINGLOSTBAGS) {
    	  this.missingBagsTotal = f;
      }
      else if(type == MessageType.SETTINGNUMBAGSTEMPAREA) {
    	  this.numOfBagsAtStoreroom = f;
      }
      else if(type == MessageType.ENTERINGTHEBUS || type == MessageType.TAKINGABUS ||
    		  type == MessageType.LEAVETHEBUS) {
    	  this.id = f;
      }
      else if(type == MessageType.GOINGCOLLECTABAG) {
    	  this.id = f;
      }
      else if(type == MessageType.SETTINGFLIGHTNUMBER) {
    	  this.flight = f;
      }
   }
   
   public Message(MessageType type, int f, int id, char t, int b) {
	   msgType = type;
	   this.flight = f;
	   this.id = id;
	   this.indTripState = t;
	   this.numOfBagsInteger = b;
   }
   
   public Message (MessageType type, Bag bag)
   {
      msgType = type;
      this.bag = bag;
   }
   
   
   public Message(MessageType type, PorterState ps) {
	   msgType = type;
	   this.ps = ps;
   }

   public Message(MessageType type, int f1, int f2) {
	   msgType = type;
	   if(type == MessageType.SETTINGNUMOFBAGSATPLANEHOLD) {
		   this.flight = f1;
		   this.numOfBagsInteger = f2;
	   }
	   else if(type == MessageType.SETNUMOFBAGSATTHEBEGINNING) {
		   this.id = f1;
		   this.numBagsAtBeggining = f2;
	   }
	   else if(type == MessageType.SETTINGPASSANGERSONTHEQUEUE) {
		   this.passengersOnTheQueue = f1;
		   this.id = f2;
	   }
	   else if(type == MessageType.SETTINGPASSANGERSONTHEBUS) {
		   this.passengerOnTheBus = f1;
		   this.id = f2;
	   }
	   else if(type == MessageType.GOINGHOME || type == MessageType.PREPARINGNEXTLEG) {
		   this.flight = f1;
		   this.id = f2;
	   }
	   else if(msgType==MessageType.REPORTBAG) {
		   reportedBags = f1;
		   this.id = f2;
	   }
   }
	
	public Message(MessageType type, int id, String destType) {
		msgType = type;
		this.id = id;
		this.destType = destType;
	}

	public Message(MessageType type, int id, PassengerState ps) {
		msgType = type;
		this.id = id;
		this.pgs = ps;
	}

	public MessageType getType (){
      return (msgType);
   }
   
   public int getReportedBags ()
   {
      return (reportedBags);
   }
   
   public int getFlight ()
   {
      return (flight);
   }
   
   public Bag bags() {
	   return bag;
   }
   
   public BusDriverState getBusDriverState() {
	   return this.bds;
   }
   
   public PorterState getPorterState() {
	   return this.ps;
   }
   
   public char[][] getTripState(){
	   return this.tripState;
   }
   
   public MemStack<Bag>[] getSBags(){
	   return this.sBags;
   }
   
   public int[] getNumOfBagsPerFlight() {
	   return this.numOfBagsPerFlight;
   }
   
   public boolean getMoreBags() {
	   return this.moreBags;
   }
   
   public int getCntPassengersEnd() {
	   return this.cntPassengerEnd;
   }
   
   public int getCntPassengersInBus() {
	   return this.cntPassengersInBus;
   }
   
   public int getNumOfBagsInteger() {
	   return this.numOfBagsInteger;
   }

	public int getPassengersInTransit() {
		return this.passengersInTransit;
	}
	
	public int getPassengerID() {
		return this.id;
	}
	
	public int getNumBagsAtBeggining(){
		return this.numBagsAtBeggining;
	}
	
	public String getDestType() {
		return this.destType;
	}
	
	public PassengerState getPassengerState() {
		return this.pgs;
	}
	
	public int getPassengersFinalDest() {
		return this.finalDest;
	}
	
	public int getPassengersOnTheQueue() {
		return this.passengersOnTheQueue;
	}
	
	public int getPassangersOnTheBus() {
		return this.passengerOnTheBus;
	}
	
	public int getNumOfBagsInConveyBelt() {
		return this.numOfBagsInConveyBelt;
	}
	
	public int getMissingBagsTotal() {
		return this.missingBagsTotal;
	}
	
	public int getNumOfBagsInTheTempArea() {
		return this.numOfBagsAtStoreroom;
	}
	
	public char getIndividualTripState() {
		return this.indTripState;
	}
	
	@Override
   public String toString ()
   {
      return ("aa");
   }
}
