package br.unb.cic.comnet.distran.agents.viewer;

import br.unb.cic.comnet.distran.agents.MessageProtocols;
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
			getAgent().send(msg);				
		}
	}

}
