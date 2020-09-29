package br.unb.cic.comnet.distran.agents.broker;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

public abstract class BrokerTickerBehaviour extends TickerBehaviour {
	private static final long serialVersionUID = 1L;
	
	public BrokerTickerBehaviour(Agent a, long period) {
		super(a, period);
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
