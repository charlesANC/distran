package br.unb.cic.comnet.distran.agents.broker;

import jade.core.Agent;
import jade.util.Logger;

public class SegmentGenerator extends BrokerTickerBehaviour {
	private static final long serialVersionUID = 1L;
	
	Logger logger = Logger.getJADELogger(getClass().getName());

	public SegmentGenerator(Agent a, long period) {
		super(a, period);
	}

	@Override
	protected void onTick() {
		if (getAgent().getSegments().isEmpty()) {
			logger.log(Logger.INFO, "Starting with 10 segments");
			
			for(int i=0; i < 10; i++) {
				getAgent().addSegment("S" + i);					
			}
		} else {
			String segment = "S" + getAgent().getSegments().size();
			
			logger.log(Logger.INFO, "Adding new seg " + segment);			
			getAgent().addSegment(segment);			
		}
	}
}
