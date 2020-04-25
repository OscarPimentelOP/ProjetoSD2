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
	CARRYBAGTOTEMPSTORE
}
