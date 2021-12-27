package br.unb.cic.comnet.distran.agents.broker;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.math3.distribution.NormalDistribution;

import br.unb.cic.comnet.distran.agents.GeneralParameters;
import jade.core.AID;
import jade.core.Agent;

public class ReNoSTwoTranscodingAssignment extends AbstractTranscodingAssigment {
	
	public ReNoSTwoTranscodingAssignment(Agent a, long period) {
		super(a, period);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public Optional<AID> drawATranscoder() {
		List<TranscoderInfo> tis = getTranscodersAboveThreshold();
		
		if (tis.isEmpty()) return Optional.empty();
		
		Double bestSample = Double.MIN_VALUE;
		AID bestAID = null;
		
		for(TranscoderInfo ti : tis) {
			if (ti.getTrustworthy() != null && ti.getReliability() != null) {
				Double sample = sampleFromNormal(ti.getTrustworthy(), ti.getReliability());
				if (sample >= bestSample) {
					bestSample = sample;
					bestAID = ti.getAID();
				}				
			}
		}
		
		return Optional.ofNullable(bestAID);
	}
	
	private Double sampleFromNormal(Double trustworthiness, Double reliability) {
		return new NormalDistribution(trustworthiness, 1 - reliability).sample();
	}
	
	private List<TranscoderInfo> getTranscodersAboveThreshold() {
		return getAgent().getTranscoders().stream()
				.filter(x -> x.getTrustworthy() >= GeneralParameters.getTrustThreshold())
					.sorted((x, y) -> y.getTrustworthy().compareTo(x.getTrustworthy()))
							.collect(Collectors.toList());
	}	
}
