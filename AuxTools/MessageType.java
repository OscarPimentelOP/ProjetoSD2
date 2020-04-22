package AuxTools;

public enum MessageType {
	  /**
	   *  Mandar o porter dormir (operação pedida pelo cliente)
	   *    @serialField GOTOSLPPORTER
	   */

	GOTOSLPPORTER,

	  /**
	   *  Mandar o busdriver dormir (operação pedida pelo cliente)
	   *    @serialField GOTOSLPBUSDRIVER
	   */

	GOTOSLPBUSDRIVER,

	  /**
	   *  Alertar o thread porters do fim de operações (operação pedida pelo cliente)
	   *    @serialField ENDOPPORTER
	   */

	ENDOPPORTER,

	  /**
	   *  Alertar o thread bus driver do fim de operações (operação pedida pelo cliente)
	   *    @serialField ENDOPBUSDIRIVER
	   */

	ENDOPBUSDIRIVER,



	  /**
	   *  Ultimo passageiro acorda o porter (operação pedida pelo cliente)
	   *    @serialField WAKEUPPORTER
	   */

	WAKEUPPORTER,


	  /**
	   *  Primeiro passageiro acorda o busdriver (operação pedida pelo cliente)
	   *    @serialField WAKEUPBUSDRIVER
	   */

	WAKEUPBUSDRIVER,

	  /**
	   *  Enviar a identificação do cliente (resposta enviada pelo servidor)
	   *    @serialField CUSTID
	   */

	CUSTID,

	  /**
	   *  Porter tries to collect a bag (operação pedida pelo cliente)
	   *    @serialField TRYTOCOLLECTABAG
	   */

	TRYTOCOLLECTABAG,


	  /**
	   *  Porter carrega a mala para a temp store (operação pedida pelo cliente)
	   *    @serialField CARRYBAGTOTEMPSTORE
	   */

	CARRYBAGTOTEMPSTORE,

	  /**
	   *  Porter carrega a mala para a baggage collection (operação pedida pelo cliente)
	   *    @serialField CARRYBAGTOBAGCOLLECTION

	   */

	CARRYBAGTOBAGCOLLECTION,


	  /**
	   *  Porter avisa que n�o h� mais malas a recolher (operação pedida pelo cliente)
	   *    @serialField NOMOREBAGSTOCOLLECT

	   */

	NOMOREBAGSTOCOLLECT,

	  /**
	   *  Passageiro chegou ao arrival lounge (operação pedida pelo cliente)
	   *    @serialField HELLO

	   */

	HELLO,

	  /**
	   *  Passageiro chegou ao fim da viagem (operação pedida pelo cliente)
	   *    @serialField GOHOME

	   */

	GOHOME,

	  /**
	   *  Passageiro entra na fila de espera(operação pedida pelo cliente)
	   *    @serialField TAKEABUS

	   */

	TAKEABUS,

	  /**
	   *  Passageiro tenta entrar no autocarro(operação pedida pelo cliente)
	   *    @serialField ENTERTHEBUS

	   */

	ENTERTHEBUS,


	  /**
	   *  Passageiro tenta sair do autocarro(operação pedida pelo cliente)
	   *    @serialField LEAVETHEBUS

	   */

	LEAVETHEBUS,


	  /**
	   *  Passageiro chegou ao departure terminal (operação pedida pelo cliente)
	   *    @serialField PREPARENEXTLEG

	   */

	PREPARENEXTLEG,

	  /**
	   *  Ultimo passageiro sai do autorcarro (operação pedida pelo cliente)
	   *    @serialField EMPTYBUS

	   */

	EMPTYBUS,

	  /**
	   *  Passageiro vai buscar uma mala (operação pedida pelo cliente)
	   *    @serialField GOCOLLECTABAG

	   */

	GOCOLLECTABAG,

	  /**
	   *  Passageiro vai reportar malas em falta (operação pedida pelo cliente)
	   *    @serialField REPORTBAG

	   */

	REPORTBAG,

	  /**
	   *  Bus driver anuncia que os passageiros podem entrar (operação pedida pelo cliente)
	   *    @serialField  ANNOUNCEBUS

	   */

	ANNOUNCEBUS,


	  /**
	   *  Bus driver estacionou o autocarro no arrival terminal transfer quay (operação pedida pelo cliente)
	   *    @serialField PARKATARRIVAL

	   */

	PARKATARRIVAL,

	  /**
	   *  Bus driver estacionou o autocarro no departure terminal transfer quay (operação pedida pelo cliente)
	   *    @serialField PARKATDEPARTURE

	   */
	
	PARKATDEPARTURE,
	
	  /**
	   *  Set porter state (operação pedida pelo cliente)
	   *    @serialField SETPORTERSTATE

	   */

	SETPORTERSTATE,

	  /**
	   *  Set passanger state (operação pedida pelo cliente)
	   *    @serialField SETPORTERSTATE

	   */

	SETPASSANGERSTATE,

	  /**
	   *  Set bus driver state (operação pedida pelo cliente)
	   *    @serialField SETPORTERSTATE

	   */

	SETBUSDRIVERSTATE,

	  /**
	   *  Operação realizada com sucesso (resposta enviada pelo servidor)
	   *    @serialField ACK
	   */
	
	ACK,


	  /**
	   *  Terminação do ciclo de vida do porter (resposta enviada pelo servidor)
	   *    @serialField ENDPORTER
	   */

	ENDPORTER,

	  /**
	   *  Terminação do ciclo de vida do bus driver (resposta enviada pelo servidor)
	   *    @serialField ENDBUSDRIVER
	   */

	ENDBUSDRIVER,


	  /**
	   *  Mala a ser recolhida pelo porter (resposta enviada pelo servidor)
	   *    @serialField BAGTOCOLLECT
	   */

	BAGTOCOLLECT,

	  /**
	   *  O autocarro est� cheio (resposta enviada pelo servidor)
	   *    @serialField BUSFULL
	   */

	BUSFULL,

	  /**
	   *  O autocarro n�o est� estacionado (resposta enviada pelo servidor)
	   *    @serialField NOTPARKED
	   */

	NOTPARKED,

	  /**
	   *  Permiss�o para entrar no autocarro (resposta enviada pelo servidor)
	   *    @serialField ENTERINBUS
	   */

	ENTERINBUS,

	  /**
	   *  Permiss�o para sair do autocarro (resposta enviada pelo servidor)
	   *    @serialField GETOUTOFBUS
	   */

	GETOUTOFBUS,


	  /**
	   *  A mala est� no comboy belt (resposta enviada pelo servidor)
	   *    @serialField BAGOK
	   */

	BAGOK,


	  /**
	   *  A mala n�o est� no comboy belt (resposta enviada pelo servidor)
	   *    @serialField BAGNOTOK
	   */

	BAGNOTOK,

	  /**
	   *  Tapete rolante sem mais malas  (resposta enviada pelo servidor)
	   *    @serialField CONVOYBELTEMPTY
	   */

	CONVOYBELTEMPTY
}
