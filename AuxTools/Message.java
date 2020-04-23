package AuxTools;

import java.io.Serializable;

public class Message implements Serializable{
	/**
   *  Chave de serializaÃ§Ã£o
   *    @serialField serialVersionUID
   */

   private static final long serialVersionUID = 1001L;
   
   //Tipos de mensagens
   private MessageType msgType = MessageType.NO_MESSAGE;
   
   //Identificação do prof
   //private int passengerId;
   
   //Numero do voo
   private int flight = -1;
   
   //Mala
   private Bag bag = null;
   
   //Malas reportadas
   private int reportedBags = -1;
   
   public Message (MessageType type)
   {
      msgType = type;
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
   
   @Override
   public String toString ()
   {
      return ("Type = " + msgType +
    		  "\nId Flight = " + Integer.toString(this.flight) +
              "\nId ReportedBags = " + Integer.toString(this.reportedBags) +
              "\nNome Bag = " + bag.toString());
   }
}
