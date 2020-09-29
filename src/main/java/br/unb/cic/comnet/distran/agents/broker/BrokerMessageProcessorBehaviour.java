package br.unb.cic.comnet.distran.agents.broker;

import br.unb.cic.comnet.distran.behaviours.MessageProcessorBehaviour;
import jade.lang.acl.MessageTemplate;

public abstract class BrokerMessageProcessorBehaviour extends MessageProcessorBehaviour {
	private static final long serialVersionUID = 1L;

	public BrokerMessageProcessorBehaviour(int low, int high, MessageTemplate template) {
		super(low, high, template);
	}
	
	@Override
	public Broker getAgent() {
		if (Broker.class.isAssignableFrom(myAgent.getClass())) {
			return (Broker) myAgent;
		}
		// FIXME: preciso achar uma exceção melhor do que essa.
		throw new RuntimeException("Agent is not a broker.");		
	}	
}
