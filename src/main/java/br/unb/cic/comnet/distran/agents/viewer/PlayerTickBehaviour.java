package br.unb.cic.comnet.distran.agents.viewer;

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
	}
}
