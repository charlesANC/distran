package br.unb.cic.comnet.distran.agents.broker;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import br.com.tm.repfogagent.trm.components.InteractionTrustComponent;
import br.unb.cic.comnet.distran.agents.GeneralParameters;
import br.unb.cic.comnet.distran.agents.MessageProtocols;
import br.unb.cic.comnet.distran.player.Segment;
import br.unb.cic.comnet.distran.util.RandomService;
import br.unb.cic.comnet.distran.util.SerializationHelper;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.util.Logger;

public class DirectTrustTranscodingAssignment extends BrokerTickerBehaviour {
	private static final long serialVersionUID = 1L;
	
	Logger logger = Logger.getJADELogger(getClass().getName());

	public DirectTrustTranscodingAssignment(Agent a, long period) {
		super(a, period);
	}

	@Override
	protected void onTick() {
		evaluateTranscoders();
		
		getAgent().getNonAssignedSegments().stream().forEach(seg -> {
			Optional<AID> opTranscoder = drawATranscoder(getAgent().segmentCount() > 29);
			
			if (opTranscoder.isPresent()) {
				AID aid = opTranscoder.get();
				
				logger.log(Logger.INFO, "Assigning seg " + seg + " to " + aid);
				
				sendAssignmentMessage(seg, aid);
				seg.setSource(aid.getName());				
			} else {
				logger.log(Logger.INFO, "Has no transcoder to assign anything!");				
			}
		});
	}
	
	private Optional<AID> drawATranscoder(boolean useTrust) {
		return useTrust ? drawUsingTrust() : drawRandomly();
	}
	
	private Optional<AID> drawUsingTrust() {
		return Optional.ofNullable(draw());
	}
	
	public AID draw() {
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
	
	private void sendAssignmentMessage(Segment segment, AID transcoder) {
		ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
		msg.setProtocol(MessageProtocols.transcode.toString());
		msg.addReceiver(transcoder);
		msg.setContent(SerializationHelper.serialize(segment));
		getAgent().send(msg);
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
	
	private void evaluateTranscoders() {
		InteractionTrustComponent directTrust = new InteractionTrustComponent(0, 0);
		
		StringBuilder str = new StringBuilder("\r\n---\r\n");
		
		for(TranscoderInfo transInfo : getAgent().getTranscoders()) {
			if (!transInfo.getRatings().isEmpty()) {
				double trustworthy = directTrust.calculate(transInfo.getRatings(), transInfo.getRatings().size());
				transInfo.setTrustworthy(trustworthy);
				transInfo.setReliability(directTrust.reliability(transInfo.getRatings()));

				str.append("Trustworthy of " + transInfo.getAID().getName() + " is " + trustworthy + "\r\n");				
			}
		}
		
		str.append("\r\n---\r\n");
		logger.log(Logger.INFO, str.toString());		
	}
	
	private Random getRandom() {
		return RandomService.getInstance().getClassGenerator(this.getClass());
	}	
}
