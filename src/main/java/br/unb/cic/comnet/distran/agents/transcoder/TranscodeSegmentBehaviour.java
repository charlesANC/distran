package br.unb.cic.comnet.distran.agents.transcoder;

import java.util.Random;

import com.google.gson.reflect.TypeToken;

import br.unb.cic.comnet.distran.agents.MessageProtocols;
import br.unb.cic.comnet.distran.player.Segment;
import br.unb.cic.comnet.distran.util.SerializationHelper;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class TranscodeSegmentBehaviour extends TranscoderMsgProcessorBehaviour {
	private static final long serialVersionUID = 1L;
	
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
		logMessage(getAgent().getName() + ": Adding segment " + msg.getContent());
		
		Segment segment = convertContent(msg);
		segment.setLength(500*1024 + 500 * Long.valueOf(new Random(25067520).nextInt(1024)));
		segment.setSource(getAgent().getName());
		
		getAgent().addSegment(segment);
		
		ACLMessage rsp = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
		rsp.addReceiver(msg.getSender());
		getAgent().send(rsp);
	}

	private Segment convertContent(ACLMessage msg) {
		return SerializationHelper.unserialize(msg.getContent(), new TypeToken<Segment>(){});
	}
}
