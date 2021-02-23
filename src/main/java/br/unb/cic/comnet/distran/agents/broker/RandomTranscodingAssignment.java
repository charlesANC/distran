package br.unb.cic.comnet.distran.agents.broker;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import br.unb.cic.comnet.distran.agents.MessageProtocols;
import br.unb.cic.comnet.distran.player.Segment;
import br.unb.cic.comnet.distran.util.RandomService;
import br.unb.cic.comnet.distran.util.SerializationHelper;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.util.Logger;

public class RandomTranscodingAssignment extends BrokerTickerBehaviour {
	private static final long serialVersionUID = 1L;
	
	private Logger logger = Logger.getJADELogger(getClass().getName());

	public RandomTranscodingAssignment(Agent a, long period) {
		super(a, period);
	}

	@Override
	protected void onTick() {
		getAgent().getNonAssignedSegments().stream().forEach(seg -> {
			Optional<AID> opTranscoder = getRandomTranscoder();
			
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
	
	private Optional<AID> getRandomTranscoder() {
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
	
	private void sendAssignmentMessage(Segment segment, AID transcoder) {
		ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
		msg.setProtocol(MessageProtocols.transcode.toString());
		msg.addReceiver(transcoder);
		msg.setContent(SerializationHelper.serialize(segment));
		getAgent().send(msg);
	}
}
