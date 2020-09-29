package br.unb.cic.comnet.distran.agents.viewer;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

public abstract class ViewerTickerBehaviour extends TickerBehaviour {
	private static final long serialVersionUID = 1L;

	public ViewerTickerBehaviour(Agent a, long period) {
		super(a, period);
	}
	
	@Override
	public Viewer getAgent() {
		if (Viewer.class.isAssignableFrom(myAgent.getClass())) {
			return (Viewer) myAgent;
		}
		// FIXME: Arrumar um melhor do que esse
		throw new RuntimeException("This is not a viewer.");
	}

}
