package br.unb.cic.comnet.distran.agents.broker.mab;

import java.util.Optional;

import br.unb.cic.comnet.distran.agents.broker.TranscoderInfo;
import jade.core.AID;
import jade.core.Agent;

public class EpsilonGreedyTranscodingAssignment extends AbstractEpsilonTranscodingAssignment {
	private static final long serialVersionUID = 1L;
	
	public EpsilonGreedyTranscodingAssignment(Agent a, double epsilon, long period) {
		super(a, epsilon, period);
	}

	@Override
	public Optional<AID> drawATranscoder() {
		if (getAgent().getTranscoders().isEmpty()) return Optional.empty();
		
		if (getRandom().nextDouble() > getEpsilon()) {
			return getMaxUtilityTranscoder().map(TranscoderInfo::getAID);
		}
		return Optional.of(draw().getAID());
	}
}
