package br.unb.cic.comnet.distran.agents.viewer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.unb.cic.comnet.distran.agents.MessageProtocols;
import br.unb.cic.comnet.distran.util.SerializeHelper;
import jade.core.AID;
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
		Map<String, String> playlist = convertContent(msg);
		logger.log(Logger.INFO, playlist.toString());		
		
		if (playlist.isEmpty()) return;
		
		List<String> segments = new ArrayList<String>(playlist.keySet());
		
		String nextSegment = "S" + getAgent().nextSegment();
		while (nextSegment.compareTo(segments.get(0)) < 0) {
			nextSegment = "S" + getAgent().nextSegment();
		}
		
		if (!playlist.containsKey(nextSegment)) return;
		
		ACLMessage rqst = new ACLMessage(ACLMessage.REQUEST);
		rqst.addReceiver(new AID(playlist.get(nextSegment), true));
		rqst.setProtocol(MessageProtocols.send_segment.toString());
		rqst.setContent(nextSegment);
		getAgent().send(rqst);
	}

	private Map<String, String> convertContent(ACLMessage msg)  {
		try {
			return SerializeHelper.unserialize(msg.getContent());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.log(Logger.WARNING, "Error during unserialization: " + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.log(Logger.WARNING, "Error during unserialization: " + e.getMessage());			
		}	
		return new HashMap<String, String>();
	}
	
	

}
