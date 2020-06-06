/**
 * @author Óscar Pimentel, nºmec 80247
 * @author Rafael Maio, nºmec 84909
 */

package AuxTools;


/**
 * This enumerate data type specifies the various messages that the clients and servers exchange between them in a Distributed solution
 * for the Airport Problem . Each message is tied to a function or an action crucial for the implementation.
 */
public enum MessageType {
	/**
	  * Initialization parameter
	  *    @serialField NO_MESSAGE
	  */

	NO_MESSAGE,
	
	//O passageiro quer saber o que fazer(solicita��o)
	/**
	  * The Passenger starts his actions upon arriving at the Arrival Lounge (solicitation)
	  */
	WHATSHOULDIDO,
	
	//O passageiro vai para casa (reposta)
	/**
	  * The Passenger goes home (response)
	  */
	GOHOME,
	
	//O passageiro vai buscar uma mala(respota)
	GOCOLLECTABAG,
	
	//O passageiro tem que ir apanhar o autocarro(resposta)
	TAKEABUS,
	
	//Porter quer saber se chegou ao final do dia (solicita��o do cliente)
	TAKEAREST, 

	//Porter chegou ao final do dia (Resposta do servidor)
	ENDPORTER,
	
	//Porter continua a trabalhar (resposta)
	KEEPWORKINGPORTER,
	
	//Porter vai tentar buscar uma mala (solicita��o)
	TRYTOCOLLECTABAG,
	
	//N�o h� mais malas no por�o (resposta)
	NOMOREBAGSATPLANEHOLD,
	
	//A mala existe (resposta)
	BAGTOCOLLECT,
	
	//O porter avisa que ja n�o h� mais a recolher(solicita��o)
	NOMOREBAGSTOCOLLECT,
	
	//A mensagem foi recebida com sucesso
	ACK,
	
	//O passageiro quer ir para casa(solicita��o)
	GOINGHOME,
	
	//O passageiro quer apanhar um autocarro(solicita��o)
	TAKINGABUS,
	
	//O passageiro quer entrar no autocarro(solicita��o)
	ENTERINGTHEBUS,
	
	//O bus driver quer se continua a trabalhar (solicita��o)
	HASDAYSWORKENDED,
	
	//O bus driver chegou ao final do dia (resposta)
	ENDBUSDRIVER,
	
	//O bus driver continua a trabalhar (resposta)
	KEEPWORKINGBUSDRIVER,
	
	//O bus driver anuncia que podem entrar (solicita��o)
	ANNOUNCEBUS,
	
	//O bus driver avisa que chegou ao terminal (solicita��o)
	PARKATARRIVAL,
	
	//O porter carrega a mala para o baggage collection point(solicita��o)
	CARRYBAGTOBAGPOINT,
	
	//O passageiro vai buscar uma mala ao convoy belt (solicita��o)
	GOINGCOLLECTABAG,
	
	//O passageiro tira a mala do convoy belt(resposta)
	BAGOK,
	
	//O passageiro n�o encontra a mala no convoy belt (resposta)
	BAGNOTOK,
	
	//O passageiro vai reportar uma mala (solicita��o)
	REPORTBAG,
	
	//O passageiro vai preparar a proxima viagem (Solicita��o)
	PREPARINGNEXTLEG,
	
	//O passageiro quer sair do autocarro(solicita��o)
	LEAVETHEBUS,
	
	//O bus driver estaciona no departure terminal transfer quay (Solicita��o)
	PARKATDEPARTURE,
	
	//O bus driver muda de estado (solicita��o)
	SETBUSDRIVERSTATE,
	
	//O porter carrega a mala para a temporary storage area (solicita��o)
	CARRYBAGTOTEMPSTORE,
	
	//Envio de parametros para a main do passenger com as malas e tripstates (solicita��o)
	SENDPARAMS,
	
	//Parametros recebidos (resposta)
	SENDPARAMSACK,
	
	//Mensagem do al para o bcp para informar que n�o h� mais malas (solicita��o)
	SETMOREBAGS,
	
	//Passageiro acorda os passageiros do departure (solicita��o)
	WAKEUPALLD,
	 
	//Passageiro d� set do time to wake up no departure (solicita��o)
	SETTIMETOWAKEUPTOFALSED,
	
	//Passageiro d� set no fim do dia do porter (solicita��o)
	SETENDOFWORKPORTER,
	
	//Passageiro d� set no fim do dia do bus driver (solicita��o)
	SETENDOFWORKBUSDRIVER,
	
	//Incrementar o numero de passageiros que chegaram ao final (solicita��o)
	INCCNTPASSENGERSEND,
	
	//Pedido para receber o numero de passageiros que chegaram ao final (solicita��o)
	GETCNTPASSENGERSEND,
	
	//Envio do numero de passageiros que chegaram ao final (resposta)
	SENDCNTPASSENGERSEND,
	
	//decrementar o numero de passageiros que chegaram ao final (solicita��o)
	DECCNTPASSENGERSEND,
	
	//Passageiro acorda os passageiros do arrival (solicita��o)
	WAKEUPALLA,
	 
	//Passageiro d� set do time to wake up no arrival (solicita��o)
	SETTIMETOWAKEUPTOFALSEA,
	
	//Passageiro sai do autocarro (solicita��o)
	READFROMBUS,
	
	//decrementar o numero de passageiros no autocarro (solicita��o)
	DECCNTPASSENGERSINBUS,
	
	//Pedido para receber o numero de passageiros no autocarro (solicita��o)
	GETCNTPASSENGERSINBUS,
	
	//Envio do numero de passageiros que chegaram no autocarro (resposta)
	SENDCNTPASSENGERSINBUS, 
	
	SETPORTERSTATE, 
	
	DECNUMBAGSATPLANEHOLD, 
	
	SETTINGFLIGHTNUMBER, 
	
	SETTINGNUMOFBAGSATPLANEHOLD, 
	
	SETTINGPASSANGERSONTRANSIT, 
	
	SETTINGPASSANGERDEST, 
	
	SETNUMOFBAGSATTHEBEGINNING, 
	
	SETTINGPASSENGERSTATE, 
	
	SETTINGPASSANGERFINALDESTINATION, 
	
	SETTINGPASSANGERSONTHEQUEUE, 
	
	SETTINGPASSANGERSONTHEBUS, 
	
	SETTINGNUMOFBAGSINTHECB, 
	
	SETTINGNUMOFBAGSCOLLECTED, 
	
	SETTINGLOSTBAGS, 
	
	SETTINGNUMBAGSTEMPAREA,
	
	SHUTDOWN,
}
