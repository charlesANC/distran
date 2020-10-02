package br.unb.cic.comnet.distran.agents.broker;

import java.util.List;

import com.google.gson.reflect.TypeToken;

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
		List<UtilityFeedback> feedbacks = unserializeContent(msg);
		
		//printUtility(msg.getSender().getName(), feedbacks);
		processFeedbacks(feedbacks);
		
		ACLMessage rsp = new ACLMessage(ACLMessage.REQUEST);
		rsp.setProtocol(MessageProtocols.request_playlist.toString());
		rsp.addReceiver(msg.getSender());			
		rsp.setContent(SerializationHelper.serialize(getAgent().getPlaylist()));
		getAgent().send(rsp);			
	}
	
	private void processFeedbacks(List<UtilityFeedback> feedbacks) {
		feedbacks.stream().forEach(x -> getAgent().addTranscoderRating(x));
	}
	
	private void printUtility(String sender, List<UtilityFeedback> feedbacks) {
		StringBuilder str = new StringBuilder();
		
		str.append("\r\n");
		str.append("Utility received from " + sender + ":\r\n");
		
		for(UtilityFeedback feedback : feedbacks) {
			
			
			str.append(feedback + "\r\n");
		}
		str.append("\r\n");	
		
		logger.log(Logger.INFO, str.toString());
	}

	private List<UtilityFeedback> unserializeContent(ACLMessage msg) {
		return SerializationHelper
				.unserialize(msg.getContent(), new TypeToken<List<UtilityFeedback>>(){});
	}
}
