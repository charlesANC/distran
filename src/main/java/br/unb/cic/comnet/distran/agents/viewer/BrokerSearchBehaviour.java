package br.unb.cic.comnet.distran.agents.viewer;

import java.util.Set;

import br.unb.cic.comnet.distran.agents.services.BrokerageServiceDescriptor;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPAException;
import jade.util.Logger;

public class BrokerSearchBehaviour extends ViewerTickerBehaviour {
	private static final long serialVersionUID = 1L;
	
	Logger logger = Logger.getJADELogger(getClass().getName());

	public BrokerSearchBehaviour(Agent a, long period) {
		super(a, period);
	}

	@Override
	protected void onTick() {
		if (!getAgent().hasBroker()) {
			try {
				Set<AID> brokers = BrokerageServiceDescriptor.search(getAgent());
				if (!brokers.isEmpty()) {
					AID broker = brokers.iterator().next();
					logger.log(Logger.INFO, "Setting up broker " + broker);
					getAgent().setBroker(broker);
				} else {
					logger.log(Logger.INFO, "No broker has been found.");
				}
			} catch (FIPAException e) {
				e.printStackTrace();
				logger.log(Logger.WARNING, "Error trying to find brokers " + e.getMessage());
			}
		}
	}

}
