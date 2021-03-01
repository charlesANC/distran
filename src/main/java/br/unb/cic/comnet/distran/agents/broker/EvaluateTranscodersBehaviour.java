package br.unb.cic.comnet.distran.agents.broker;

import java.util.ArrayList;
import java.util.List;

import br.com.tm.repfogagent.trm.Rating;
import br.com.tm.repfogagent.trm.components.InteractionTrustComponent;
import jade.core.Agent;
import jade.util.Logger;

public class EvaluateTranscodersBehaviour extends BrokerTickerBehaviour {
	private static final long serialVersionUID = 1L;
	
	private Logger logger = Logger.getJADELogger(getClass().getName());

	public EvaluateTranscodersBehaviour(Agent a, long period) {
		super(a, period);
	}

	@Override
	protected void onTick() {
		InteractionTrustComponent directTrust = new InteractionTrustComponent(0, 0);
		
		StringBuilder str = new StringBuilder("\r\n---\r\n");
		
		for(TranscoderInfo transInfo : getAgent().getTranscoders()) {
			if (!transInfo.getRatings().isEmpty()) {
				List<Rating> ratings = new ArrayList<>();
				transInfo.getRatings().values().forEach(ratings::addAll);
				
				double trustworthy = directTrust.calculate(ratings, ratings.size());
				transInfo.setTrustworthy(trustworthy);
				transInfo.setReliability(directTrust.reliability(ratings));

				str.append("Trustworthy of " + transInfo.getAID().getName() + " is " + trustworthy + "\r\n");				
			}
		}
		
		str.append("\r\n---\r\n");
		logger.log(Logger.INFO, str.toString());		
	}

}
