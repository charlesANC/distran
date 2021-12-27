package br.unb.cic.comnet.distran.agents.viewer.untrustable;

import java.util.ArrayList;
import java.util.List;

import br.unb.cic.comnet.distran.agents.MessageProtocols;
import br.unb.cic.comnet.distran.agents.broker.UtilityFeedback;
import br.unb.cic.comnet.distran.agents.viewer.ViewerTickerBehaviour;
import br.unb.cic.comnet.distran.player.SegmentInfo;
import br.unb.cic.comnet.distran.util.SerializationHelper;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.util.Logger;

public class UntrustablePlaylistRequestingBehaviour extends ViewerTickerBehaviour {
	private static final long serialVersionUID = 1L;
	
	private Logger log = Logger.getJADELogger(getClass().getName());
	
	private String partnerTranscoderId;
	
	public UntrustablePlaylistRequestingBehaviour(Agent a, long period, String partnerTranscoderId) {
		super(a, period);
		this.partnerTranscoderId = partnerTranscoderId;
	}

	@Override
	protected void onTick() {
		if (getAgent().hasBroker()) {
			log.log(Logger.INFO, "Requesting new playlist");
			
			ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
			msg.addReceiver(getAgent().getBroker());
			msg.setProtocol(MessageProtocols.request_playlist.toString());
			msg.setContent(SerializationHelper.serialize(getUtilityFeedback()));
			getAgent().send(msg);				
		}
	}
	
	private List<UtilityFeedback> getUtilityFeedback() {
		List<UtilityFeedback> feedback = new ArrayList<>();
		
		for(SegmentInfo info : getAgent().getPlayer().getPlayedSegments()) {
			double utility;
			double standardUtility;
			
			if (info.getSource().startsWith(partnerTranscoderId)) {
				utility = info.maxUtility();
				standardUtility = 1D;
			} else {
				/*
				utility = info.utility(GeneralParameters.getBetha());
				standardUtility = info.standardUtility(GeneralParameters.getBetha());
				
				if (standardUtility > 0) {
					utility *= 0.7;
					standardUtility *= 0.7;
				}
				*/
				
				utility = -3 * info.maxUtility();
				standardUtility = -1D;
			}
			
			feedback.add(
				new UtilityFeedback(
						getAgent().getAID().getName(),
						info.getId(), 
						info.getSource(), 
						info.getRequestingTime(),
						info.getStartTime(),
						info.playbackInterval(),
						utility,
						info.maxUtility(),
						standardUtility, 
						info.getEndTime()
				)					
			);
		}
		
		return feedback;
	}

}
