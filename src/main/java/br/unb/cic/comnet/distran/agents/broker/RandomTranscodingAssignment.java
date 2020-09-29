package br.unb.cic.comnet.distran.agents.broker;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import br.unb.cic.comnet.distran.agents.MessageProtocols;
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
		notAssignedSegments().stream().forEach(seg -> {
			Optional<AID> opTranscoder = getRandomTranscoder();
			
			if (opTranscoder.isPresent()) {
				AID aid = opTranscoder.get();
				
				logger.log(Logger.INFO, "Assigning seg " + seg + " to " + aid);
				
				sendAssignmentMessage(seg, aid);
				getAgent().addAssignment(seg, aid.getName());				
			} else {
				logger.log(Logger.INFO, "Has no transcoder to assign anything!");				
			}
		});
	}
	
	private Set<String> notAssignedSegments() {
		Set<String> notAssigned = getAgent().getSegments();
		notAssigned.removeAll(getAgent().getAssignments().keySet());
		return notAssigned;
	}
	
	private Optional<AID> getRandomTranscoder() {
		List<AID> transcoders =  new ArrayList<AID>(getAgent().getTranscoders());
		
		if (transcoders.isEmpty()) return Optional.empty();
			
		int rand = new Random().nextInt(transcoders.size());
		return Optional.ofNullable(transcoders.get(rand));
	}
	
	private void sendAssignmentMessage(String segment, AID transcoder) {
		ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
		msg.setProtocol(MessageProtocols.transcode.toString());
		msg.addReceiver(transcoder);
		msg.setContent(segment);
		getAgent().send(msg);
	}
}
