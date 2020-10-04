package br.unb.cic.comnet.distran.agents.viewer;

import java.util.List;
import java.util.stream.Collectors;

import br.unb.cic.comnet.distran.agents.GeneralParameters;
import br.unb.cic.comnet.distran.agents.MessageProtocols;
import br.unb.cic.comnet.distran.agents.broker.UtilityFeedback;
import br.unb.cic.comnet.distran.util.SerializationHelper;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.util.Logger;

public class PlaylistRequestingBehaviour extends ViewerTickerBehaviour {
	private static final long serialVersionUID = 1L;
	
	Logger log = Logger.getJADELogger(getClass().getName());

	public PlaylistRequestingBehaviour(Agent a, long period) {
		super(a, period);
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
		return getAgent().getPlayer().getPlayedSegments().stream()
				.map(x -> new UtilityFeedback(
								getAgent().getAID().getName(),
								x.getId(), 
								x.getSource(), 
								x.utility(GeneralParameters.getBetha()),
								x.maxUtility(),
								x.standardUtility(GeneralParameters.getBetha()), 
								x.getEndTime()
							)
				)
				.collect(Collectors.toList());
	}

}
