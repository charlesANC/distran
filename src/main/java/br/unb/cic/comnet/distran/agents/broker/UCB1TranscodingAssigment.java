package br.unb.cic.comnet.distran.agents.broker;

import java.util.Optional;

import jade.core.AID;
import jade.core.Agent;

public class UCB1TranscodingAssigment extends AbstractTranscodingAssigment {

	private static final long serialVersionUID = 1L;

	public UCB1TranscodingAssigment(Agent a, long period) {
		super(a, period);
	}

	@Override
	public Optional<AID> drawATranscoder() {
		double maxUpperBound = 0D;
		AID maxTranscoder = null;
		for(TranscoderInfo transcoder : getAgent().getTranscoders()) {
			
			if (countAssignedTo(transcoder) == 0) {
				return Optional.of(transcoder.getAID());
			}
			
			double upperBound = calculateUpperBound(transcoder);
			if (upperBound > maxUpperBound) {
				maxUpperBound = upperBound;
				maxTranscoder = transcoder.getAID();
			}
		}
		return Optional.ofNullable(maxTranscoder);
	}
	
	private double calculateUpperBound(TranscoderInfo transcoder) {
		return transcoder.getTrustworthy() + Math.sqrt(2 * Math.log(countAssignedSegments() / countAssignedTo(transcoder)));
	}
	
	private long countAssignedSegments() {
		return getAgent().segmentCount() - getAgent().getNonAssignedSegments().size();
	}
	
	private long countAssignedTo(TranscoderInfo transcoder) {
		return getAgent().getPlaylist().stream()
				.filter(x -> x.getSource().equals(transcoder.getAID().getName()))
				.count();
	}

}
