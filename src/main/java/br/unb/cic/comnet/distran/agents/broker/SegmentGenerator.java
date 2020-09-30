package br.unb.cic.comnet.distran.agents.broker;

import br.unb.cic.comnet.distran.player.Segment;
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
		if (getAgent().hasNoSegments()) {
			logger.log(Logger.INFO, "Starting with 10 segments");
			
			for(int i=0; i < 10; i++) {
				getAgent().addSegment(Segment.create("S" + i, 2000L));					
			}
		} else {
			String id = "S" + getAgent().getPlaylist().size();
			
			logger.log(Logger.INFO, "Adding new seg " + id);			
			getAgent().addSegment(Segment.create(id, 2000L));			
		}
	}
}
