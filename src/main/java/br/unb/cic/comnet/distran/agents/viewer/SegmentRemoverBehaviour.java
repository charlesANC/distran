package br.unb.cic.comnet.distran.agents.viewer;

import br.unb.cic.comnet.distran.agents.MessageProtocols;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class SegmentRemoverBehaviour extends ViewerTickerBehaviour {
	private static final long serialVersionUID = 1L;
	
	public SegmentRemoverBehaviour(Agent a, long period) {
		super(a, period);
	}

	@Override
	protected void onTick() {
		getAgent().removeFirstSegment();
		
		if (getAgent().hasBroker()) {
			ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
			msg.addReceiver(getAgent().getBroker());
			msg.setProtocol(MessageProtocols.request_playlist.toString());
			getAgent().send(msg);
		}
	}

}
