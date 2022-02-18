package br.unb.cic.comnet.distran.agents.broker;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import br.unb.cic.comnet.distran.agents.GeneralParameters;
import br.unb.cic.comnet.distran.util.RandomService;
import jade.core.AID;
import jade.core.Agent;
import jade.util.Logger;

public class ReNosTranscodingAssignment extends AbstractTranscodingAssigment {
	private static final long serialVersionUID = 1L;
	
	Logger logger = Logger.getJADELogger(getClass().getName());

	public ReNosTranscodingAssignment(Agent a, long period) {
		super(a, period);
	}

	@Override
	public Optional<AID> drawATranscoder() {
		return getAgent().segmentCount() > 25 ? drawUsingTrust() : drawRandomly();
	}
	
	private Optional<AID> drawUsingTrust() {
		return Optional.ofNullable(draw());
	}
	
	private AID draw() {
		List<TranscoderInfo> tis = getTranscodersAboveThreshold();
		
		if (tis.isEmpty()) return null;
		
		Map<TranscoderInfo, List<Long>> ranges = calculateRanges(tis, 100);
		int move = getRandom().nextInt(100);
		for(TranscoderInfo ti : tis) {
			if (ranges.get(ti).get(0) <= move && ranges.get(ti).get(1) >= move) {
				return ti.getAID();
			}
		}
		
		return tis.get(0).getAID();
	}
	
	private Map<TranscoderInfo, List<Long>> calculateRanges(List<TranscoderInfo> transcoders, int max) {
		Double sumOfTrust = transcoders.stream()
				.collect(Collectors.summingDouble(TranscoderInfo::getTrustworthy));
		
		long inf = 0;
		
		Map<TranscoderInfo, List<Long>> ranges = new HashMap<TranscoderInfo, List<Long>>();
		
		for(TranscoderInfo ti : transcoders) {
			long sup = inf + Math.round(max * (ti.getTrustworthy() / sumOfTrust));
			ranges.put(ti, Arrays.asList(inf, sup));
			inf = sup + 1;
		}
		
		return ranges;
	}
	
	private List<TranscoderInfo> getTranscodersAboveThreshold() {
		return getAgent().getTranscoders().stream()
				.filter(x -> x.getTrustworthy() >= GeneralParameters.getTrustThreshold())
					.sorted((x, y) -> y.getTrustworthy().compareTo(x.getTrustworthy()))
							.collect(Collectors.toList());
	}
	
	private Optional<AID> drawRandomly() {
		List<AID> transcoders =  
				getAgent().getTranscoders().stream()
					.map(x -> x.getAID())
					.collect(Collectors.toList());
		
		if (transcoders.isEmpty()) return Optional.empty();
			
		int rand = getRandom().nextInt(transcoders.size());
		return Optional.ofNullable(transcoders.get(rand));
	}
	
	private Random getRandom() {
		return RandomService.getInstance().getClassGenerator(this.getClass());
	}	
}
