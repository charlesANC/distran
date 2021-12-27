package br.unb.cic.comnet.distran.agents.broker.mab;

import jade.core.Agent;

public class DecreasingEpsilonTranscodingAssignment extends EpsilonGreedyTranscodingAssignment {
	private static final long serialVersionUID = 1L;
	
	@Override
	public double getEpsilon() {
		long turn = getTurn();
		return super.getEpsilon() * 7 * (Math.log(turn) / turn);
	}
	
	public DecreasingEpsilonTranscodingAssignment(Agent a, double epsilon, long period) {
		super(a, epsilon, period);
	}	

}
