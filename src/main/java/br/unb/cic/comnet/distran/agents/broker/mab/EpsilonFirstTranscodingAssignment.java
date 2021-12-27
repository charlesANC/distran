package br.unb.cic.comnet.distran.agents.broker.mab;

import java.util.Optional;

import br.unb.cic.comnet.distran.agents.broker.TranscoderInfo;
import jade.core.AID;
import jade.core.Agent;

public class EpsilonFirstTranscodingAssignment extends EpsilonGreedyTranscodingAssignment {
	private static final long serialVersionUID = 1L;	
	
	private long n;
	
	public EpsilonFirstTranscodingAssignment(Agent a, double epsilon, long n, long period) {
		super(a, epsilon, period);
		this.n = n;
	}

	@Override
	public Optional<AID> drawATranscoder() {
		if (getAgent().getTranscoders().isEmpty()) return Optional.empty();
		
		long turn = getTurn() % n;
		if (turn > getEpsilon() * n) {
			return getMaxUtilityTranscoder().map(TranscoderInfo::getAID);
		}
		return Optional.of(draw().getAID());
	}
}
