package br.unb.cic.comnet.distran.agents.broker;

import java.util.List;
import java.util.Optional;

import br.com.tm.repfogagent.trm.Rating;
import br.com.tm.repfogagent.trm.components.InteractionTrustComponent;
import br.unb.cic.comnet.distran.agents.MessageProtocols;
import br.unb.cic.comnet.distran.player.Segment;
import br.unb.cic.comnet.distran.util.SerializationHelper;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.util.Logger;

public abstract class AbstractTranscodingAssigment extends BrokerTickerBehaviour {
	private static final long serialVersionUID = 1L;
	
	Logger logger = Logger.getJADELogger(getClass().getName());

	public AbstractTranscodingAssigment(Agent a, long period) {
		super(a, period);
	}

	@Override
	protected void onTick() {
		evaluateTranscoders();
		
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
	
	public abstract Optional<AID> drawATranscoder();
	
	private void sendAssignmentMessage(Segment segment, AID transcoder) {
		ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
		msg.setProtocol(MessageProtocols.transcode.toString());
		msg.addReceiver(transcoder);
		msg.setContent(SerializationHelper.serialize(segment));
		getAgent().send(msg);
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
}
