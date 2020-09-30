package br.unb.cic.comnet.distran.agents.viewer;

import com.google.gson.reflect.TypeToken;

import br.unb.cic.comnet.distran.agents.MessageProtocols;
import br.unb.cic.comnet.distran.player.Segment;
import br.unb.cic.comnet.distran.util.SerializationHelper;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.Logger;

public class ReceivingSegmentBehaviour extends ViewerMessageProcessorBehaviour {
	private static final long serialVersionUID = 1L;
	
	private Logger log = Logger.getJADELogger(getClass().getName());
	
	private static MessageTemplate setupTemplate() {
		return MessageTemplate.and(
				MessageTemplate.MatchPerformative(ACLMessage.CONFIRM), 
				MessageTemplate.MatchProtocol(MessageProtocols.send_segment.toString()));
	}	

	public ReceivingSegmentBehaviour(int low, int high) {
		super(low, high, setupTemplate());
	}

	@Override
	public void doAction(ACLMessage msg) {
		Segment segment = convertContent(msg);
		
		log.log(Logger.INFO, "Receiving segment " + segment.getId() + "...");
		getAgent().getPlayer().receiveSegment(segment.getId(), segment.getLength());
	}
	
	private Segment convertContent(ACLMessage msg) {
		return SerializationHelper.unserialize(msg.getContent(), new TypeToken<Segment>(){});
	}

}
