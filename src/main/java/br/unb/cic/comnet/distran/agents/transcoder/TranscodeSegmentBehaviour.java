package br.unb.cic.comnet.distran.agents.transcoder;

import java.time.LocalDateTime;

import br.unb.cic.comnet.distran.agents.MessageProtocols;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.Logger;

public class TranscodeSegmentBehaviour extends TranscoderMsgProcessorBehaviour {
	private static final long serialVersionUID = 1L;
	
	Logger logger = Logger.getJADELogger(getClass().getName());

	private static MessageTemplate setupTemplate() {
		return MessageTemplate.and(
				MessageTemplate.MatchPerformative(ACLMessage.PROPOSE), 
				MessageTemplate.MatchProtocol(MessageProtocols.transcode.toString()));
	}	

	public TranscodeSegmentBehaviour(int low, int high) {
		super(low, high, setupTemplate());
	}

	@Override
	public void doAction(ACLMessage msg) {
		logger.log(Logger.INFO, getAgent().getName() + ": Adding segment " + msg.getContent());
		
		getAgent().addSegment(msg.getContent(), LocalDateTime.now().toString());
		
		ACLMessage rsp = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
		rsp.addReceiver(msg.getSender());
		getAgent().send(rsp);
	}
}
