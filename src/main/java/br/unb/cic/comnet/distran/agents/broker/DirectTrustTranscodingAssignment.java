package br.unb.cic.comnet.distran.agents.broker;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import br.unb.cic.comnet.distran.agents.MessageProtocols;
import br.unb.cic.comnet.distran.player.Segment;
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
		getAgent().getNonAssignedSegments().stream().forEach(seg -> {
			Optional<AID> opTranscoder = drawATranscoder();
			
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
	
	private Optional<AID> drawATranscoder() {
		if (getAgent().getTranscoders().isEmpty()) {
			return Optional.empty();
		}
		
		List<AID> transcoders = sortTranscodersByTrust();
		
		if (transcoders.size() == 1) {
			return Optional.of(transcoders.get(0));
		}
		
		int drawValue = new Random().nextInt(100);		
		
		if (drawValue < 60) {
			return Optional.of(transcoders.get(0));
		}
		
		return Optional.of(transcoders.get(1));
	}

	private List<AID> sortTranscodersByTrust() {
		return getAgent().getTranscoders().stream()
				.sorted((x, y) -> { return y.getTrustworthy().compareTo(x.getTrustworthy());})
					.map(TranscoderInfo::getAID)
						.collect(Collectors.toList());
	}
	
	private void sendAssignmentMessage(Segment segment, AID transcoder) {
		ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
		msg.setProtocol(MessageProtocols.transcode.toString());
		msg.addReceiver(transcoder);
		msg.setContent(segment.getId());
		getAgent().send(msg);
	}

}
