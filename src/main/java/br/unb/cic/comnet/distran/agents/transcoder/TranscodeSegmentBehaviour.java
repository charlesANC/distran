package br.unb.cic.comnet.distran.agents.transcoder;

import java.util.Random;

import br.unb.cic.comnet.distran.agents.GeneralParameters;
import br.unb.cic.comnet.distran.agents.MessageProtocols;
import br.unb.cic.comnet.distran.player.Segment;
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
		
		Segment segment = Segment.create(msg.getContent(), GeneralParameters.getDuration(), getAgent().getName());
		segment.setLength(500*1024 + 500 * Long.valueOf(new Random(250675).nextInt(1024)));
		
		getAgent().addSegment(segment);
		
		ACLMessage rsp = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
		rsp.addReceiver(msg.getSender());
		getAgent().send(rsp);
	}
}
