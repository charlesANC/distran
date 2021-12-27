package br.unb.cic.comnet.distran.agents.broker.mab;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import br.unb.cic.comnet.distran.agents.broker.AbstractTranscodingAssigment;
import br.unb.cic.comnet.distran.agents.broker.TranscoderInfo;
import br.unb.cic.comnet.distran.util.RandomService;
import jade.core.Agent;

public abstract class AbstractEpsilonTranscodingAssignment extends AbstractTranscodingAssigment {
	private static final long serialVersionUID = 1L;
	
	private double epsilon;
	
	public double getEpsilon() {
		return epsilon;
	}
	
	public long getTurn() {
		return countAssignedSegments() + 1;
	}

	public AbstractEpsilonTranscodingAssignment(Agent a, double epsilon, long period) {
		super(a, period);
		this.epsilon = epsilon;
	}

	protected TranscoderInfo draw() {
		int index = Double.valueOf(getAgent().getTranscoders().size() * getRandom().nextDouble()).intValue();
		return new ArrayList<TranscoderInfo>(getAgent().getTranscoders()).get(index);
	}

	protected Optional<TranscoderInfo> getMaxUtilityTranscoder() {
		return getAgent().getTranscoders().stream().max((x, y) -> x.getTrustworthy().compareTo(y.getTrustworthy()));
	}
	
	protected Random getRandom() {
		return RandomService.getInstance().getClassGenerator(this.getClass());
	}
	
	private long countAssignedSegments() {
		return getAgent().segmentCount() - getAgent().getNonAssignedSegments().size();
	}	
}
