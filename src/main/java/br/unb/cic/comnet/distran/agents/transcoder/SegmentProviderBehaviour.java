package br.unb.cic.comnet.distran.agents.transcoder;

import java.util.Optional;

import br.unb.cic.comnet.distran.agents.MessageProtocols;
import br.unb.cic.comnet.distran.player.Segment;
import br.unb.cic.comnet.distran.util.SerializationHelper;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.Logger;

public class SegmentProviderBehaviour extends TranscoderMsgProcessorBehaviour {
	private static final long serialVersionUID = 1L;
	
	Logger logger = Logger.getJADELogger(getClass().getName());
	
	private static MessageTemplate setupTemplate() {
		return MessageTemplate.and(
				MessageTemplate.MatchPerformative(ACLMessage.REQUEST), 
				MessageTemplate.MatchProtocol(MessageProtocols.send_segment.toString()));
	}	
	
	public SegmentProviderBehaviour(int low, int high) {
		super(low, high, setupTemplate());
	}
	
	@Override
	public void doAction(ACLMessage msg) {
		sendSegment(msg);
	}

	private void sendSegment(ACLMessage rcv) {
		Optional<Segment> opSegment = getAgent().getSegment(rcv.getContent());
		
		if (opSegment.isPresent()) {
			logger.log(Logger.INFO, "serving " + rcv.getContent());
			
			ACLMessage snd = new ACLMessage(ACLMessage.CONFIRM);
			snd.setProtocol(MessageProtocols.send_segment.toString());
			snd.addReceiver(rcv.getSender());
			snd.setContent(SerializationHelper.serialize(opSegment.get()));	
			myAgent.send(snd);			
		} else {
			logger.log(Logger.INFO, "Can not find segment " + rcv.getContent());
			
			ACLMessage snd = new ACLMessage(ACLMessage.DISCONFIRM);
			snd.setProtocol(MessageProtocols.send_segment.toString());			
			snd.addReceiver(rcv.getSender());
			myAgent.send(snd);				
		}
	}
	
}
