package br.unb.cic.comnet.distran.agents.broker;

import br.unb.cic.comnet.distran.agents.MessageProtocols;
import br.unb.cic.comnet.distran.util.SerializationHelper;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.Logger;

public class PlaylistProviderBehaviour extends BrokerMessageProcessorBehaviour {
	private static final long serialVersionUID = 1L;
	
	Logger logger = Logger.getJADELogger(getClass().getName());
	
	private static MessageTemplate setupTemplate() {
		return MessageTemplate.and(
				MessageTemplate.MatchPerformative(ACLMessage.REQUEST), 
				MessageTemplate.MatchProtocol(MessageProtocols.request_playlist.toString()));		
	}
	
	public PlaylistProviderBehaviour(int low, int high) {
		super(low, high, setupTemplate());
	}	

	@Override
	public void doAction(ACLMessage msg) {
		ACLMessage rsp = new ACLMessage(ACLMessage.REQUEST);
		rsp.setProtocol(MessageProtocols.request_playlist.toString());
		rsp.addReceiver(msg.getSender());			
		rsp.setContent(SerializationHelper.serialize(getAgent().getPlaylist()));
		getAgent().send(rsp);			
	}
}
