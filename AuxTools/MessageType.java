package AuxTools;

public enum MessageType {
	/**
	  * Initialization parameter
	  *    @serialField NO_MESSAGE
	  */

	NO_MESSAGE,
	
	//O passageiro quer saber o que fazer(solicitação)
	WHATSHOULDIDO,
	
	//O passageiro vai para casa (reposta)
	GOHOME,
	
	//O passageiro vai buscar uma mala(respota)
	GOCOLLECTABAG,
	
	//O passageiro tem que ir apanhar o autocarro(resposta)
	TAKEABUS,
	
	//Porter quer saber se chegou ao final do dia (solicitação do cliente)
	TAKEAREST, 

	//Porter chegou ao final do dia (Resposta do servidor)
	ENDPORTER,
	
	//Porter continua a trabalhar (resposta)
	KEEPWORKINGPORTER,
	
	//Porter vai tentar buscar uma mala (solicitação)
	TRYTOCOLLECTABAG,
	
	//Não há mais malas no porão (resposta)
	NOMOREBAGSATPLANEHOLD,
	
	//A mala existe (resposta)
	BAGTOCOLLECT,
	
	//O porter avisa que ja não há mais a recolher(solicitação)
	NOMOREBAGSTOCOLLECT,
	
	//A mensagem foi recebida com sucesso
	ACK,
	
	//O passageiro quer ir para casa(solicitação)
	GOINGHOME,
	
	//O passageiro quer apanhar um autocarro(solicitação)
	TAKINGABUS,
	
	//O passageiro quer entrar no autocarro(solicitação)
	ENTERINGTHEBUS,
	
	//O bus driver quer se continua a trabalhar (solicitação)
	HASDAYSWORKENDED,
	
	//O bus driver chegou ao final do dia (resposta)
	ENDBUSDRIVER,
	
	//O bus driver continua a trabalhar (resposta)
	KEEPWORKINGBUSDRIVER,
	
	//O bus driver anuncia que podem entrar (solicitação)
	ANNOUNCEBUS,
	
	//O bus driver avisa que chegou ao terminal (solicitação)
	PARKATARRIVAL,
	
	//O porter carrega a mala para o baggage collection point(solicitação)
	CARRYBAGTOBAGPOINT,
	
	//O passageiro vai buscar uma mala ao convoy belt (solicitação)
	GOINGCOLLECTABAG,
	
	//O passageiro tira a mala do convoy belt(resposta)
	BAGOK,
	
	//O passageiro não encontra a mala no convoy belt (resposta)
	BAGNOTOK,
	
	//O passageiro vai reportar uma mala (solicitação)
	REPORTBAG,
	
	//O passageiro vai preparar a proxima viagem (Solicitação)
	PREPARINGNEXTLEG,
	
	//O passageiro quer sair do autocarro(solicitação)
	LEAVETHEBUS,
	
	//O bus driver estaciona no departure terminal transfer quay (Solicitação)
	PARKATDEPARTURE,
	
	//O bus driver muda de estado (solicitação)
	SETBUSDRIVERSTATE,
	
	//O porter carrega a mala para a temporary storage area (solicitação)
	CARRYBAGTOTEMPSTORE,
	
	//Envio de parametros para a main do passenger com as malas e tripstates (solicitação)
	SENDPARAMS,
	
	//Parametros recebidos (resposta)
	SENDPARAMSACK,
	
	//Mensagem do al para o bcp para informar que não há mais malas (solicitação)
	SETMOREBAGS,
	
	//Passageiro acorda os passageiros do departure (solicitação)
	WAKEUPALLD,
	 
	//Passageiro dá set do time to wake up no departure (solicitação)
	SETTIMETOWAKEUPTOFALSED,
	
	//Passageiro dá set no fim do dia do porter (solicitação)
	SETENDOFWORKPORTER,
	
	//Passageiro dá set no fim do dia do bus driver (solicitação)
	SETENDOFWORKBUSDRIVER,
	
	//Incrementar o numero de passageiros que chegaram ao final (solicitação)
	INCCNTPASSENGERSEND,
	
	//Pedido para receber o numero de passageiros que chegaram ao final (solicitação)
	GETCNTPASSENGERSEND,
	
	//Envio do numero de passageiros que chegaram ao final (resposta)
	SENDCNTPASSENGERSEND,
	
	//decrementar o numero de passageiros que chegaram ao final (solicitação)
	DECCNTPASSENGERSEND,
	
	//Passageiro acorda os passageiros do arrival (solicitação)
	WAKEUPALLA,
	 
	//Passageiro dá set do time to wake up no arrival (solicitação)
	SETTIMETOWAKEUPTOFALSEA,
	
	//Passageiro sai do autocarro (solicitação)
	READFROMBUS,
	
	//decrementar o numero de passageiros no autocarro (solicitação)
	DECCNTPASSENGERSINBUS,
	
	//Pedido para receber o numero de passageiros no autocarro (solicitação)
	GETCNTPASSENGERSINBUS,
	
	//Envio do numero de passageiros que chegaram no autocarro (resposta)
	SENDCNTPASSENGERSINBUS,
}
