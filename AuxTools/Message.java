package AuxTools;

import java.io.Serializable;

import clientSide.Entities.BusDriverState;

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
   
   public Message (MessageType type)
   {
      msgType = type;
   }
   
   public Message(MessageType type, BusDriverState bds) {
	   msgType = type;
	   this.bds = bds;
   }
   
   public Message (MessageType type, int f)
   {
      msgType = type;
      if(msgType==MessageType.REPORTBAG) {
    	  reportedBags = f;
      }
      else {
    	  flight=f;
      }
   }
   
   public Message (MessageType type, Bag bag)
   {
      msgType = type;
      this.bag = bag;
   }
   
   
   public MessageType getType ()
   {
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
   
   @Override
   public String toString ()
   {
      return ("Type = " + msgType +
    		  "\nId Flight = " + Integer.toString(this.flight) +
              "\nId ReportedBags = " + Integer.toString(this.reportedBags) +
              "\nNome Bag = " + bag.toString());
   }
}
