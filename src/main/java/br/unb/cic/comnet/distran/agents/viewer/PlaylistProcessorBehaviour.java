package br.unb.cic.comnet.distran.agents.viewer;

import java.util.List;

import com.google.gson.reflect.TypeToken;

import br.unb.cic.comnet.distran.agents.MessageProtocols;
import br.unb.cic.comnet.distran.player.Segment;
import br.unb.cic.comnet.distran.util.SerializationHelper;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.Logger;

public class PlaylistProcessorBehaviour extends ViewerMessageProcessorBehaviour {
	private static final long serialVersionUID = 1L;
	
	Logger logger = Logger.getJADELogger(getClass().getName());

	private static MessageTemplate setupTemplate() {
		return MessageTemplate.and(
				MessageTemplate.MatchPerformative(ACLMessage.REQUEST), 
				MessageTemplate.MatchProtocol(MessageProtocols.request_playlist.toString()));
	}	

	public PlaylistProcessorBehaviour(int low, int high) {
		super(low, high, setupTemplate());
	}

	@Override
	public void doAction(ACLMessage msg) {
		List<Segment> playlist = convertContent(msg);
		logger.log(Logger.INFO, playlist.toString());		
		getAgent().getPlayer().updateSegmentsSource(playlist);
	}

	private List<Segment> convertContent(ACLMessage msg)  {
		return SerializationHelper.unserialize(msg.getContent(), new TypeToken<List<Segment>>(){});
	}
}
