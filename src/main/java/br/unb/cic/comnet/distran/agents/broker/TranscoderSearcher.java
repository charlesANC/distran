package br.unb.cic.comnet.distran.agents.broker;

import br.unb.cic.comnet.distran.agents.services.TranscodingServiceDescriptor;
import jade.core.Agent;
import jade.domain.FIPAException;
import jade.util.Logger;

public class TranscoderSearcher extends BrokerTickerBehaviour {
	private static final long serialVersionUID = 1L;
	
	Logger logger = Logger.getJADELogger(getClass().getName());

	public TranscoderSearcher(Agent a, long period) {
		super(a, period);
	}

	@Override
	protected void onTick() {
		logger.log(Logger.INFO, "Searching new transcoders");
		
		try {
			getAgent().addTranscoders(TranscodingServiceDescriptor.search(getAgent()));
		} catch (FIPAException e) {
			e.printStackTrace();
			System.out.println("Cannot search for new transcoders!");
		}
	}

}
