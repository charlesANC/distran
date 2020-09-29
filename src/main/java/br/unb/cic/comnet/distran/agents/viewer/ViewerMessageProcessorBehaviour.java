package br.unb.cic.comnet.distran.agents.viewer;

import br.unb.cic.comnet.distran.behaviours.MessageProcessorBehaviour;
import jade.lang.acl.MessageTemplate;

public abstract class ViewerMessageProcessorBehaviour extends MessageProcessorBehaviour {
	private static final long serialVersionUID = 1L;

	public ViewerMessageProcessorBehaviour(int low, int high, MessageTemplate template) {
		super(low, high, template);
	}
	
	@Override
	public Viewer getAgent() {
		if (Viewer.class.isAssignableFrom(myAgent.getClass())) {
			return (Viewer) myAgent;
		}
		// FIXME: Arrumar um melhor do que esse
		throw new RuntimeException("This is not a viewer.");
	}	
}
