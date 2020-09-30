package br.unb.cic.comnet.distran.agents.viewer;

import br.unb.cic.comnet.distran.player.SegmentInfo;
import jade.core.Agent;
import jade.util.Logger;

public class PlayerTickBehaviour extends ViewerTickerBehaviour {
	private static final long serialVersionUID = 1L;
	
	Logger log = Logger.getJADELogger(getClass().getName());

	public PlayerTickBehaviour(Agent a, long period) {
		super(a, period);
	}

	@Override
	protected void onTick() {
		log.log(Logger.INFO, "Ticking the player...");
		getAgent().getPlayer().playStep();
		
		double sum_max = 0;
		double sum_actual = 0;
		
		StringBuilder str = new StringBuilder();
		
		for(SegmentInfo seg : getAgent().getPlayer().getPlayedSegments()) {
			double max = seg.maxUtility();
			double actual = seg.utility(1000);
			
			sum_max += max;
			sum_actual += actual;
			
			str.append(seg + " Utility: " + actual + " Max Utility: " + max + "\r\n");
		}
		
		str.append("---\r\n");
		str.append("Max Utility: " + sum_max + "\r\n");
		str.append("Actual Utility: " + sum_actual + "\r\n");	
		
		log.log(Logger.INFO, str.toString());
	}
}
