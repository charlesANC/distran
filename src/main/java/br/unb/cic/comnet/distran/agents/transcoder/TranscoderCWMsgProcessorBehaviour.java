package br.unb.cic.comnet.distran.agents.transcoder;

import br.unb.cic.comnet.distran.behaviours.CyclicWaitingMessageProcessor;
import jade.lang.acl.MessageTemplate;

public abstract class TranscoderCWMsgProcessorBehaviour extends CyclicWaitingMessageProcessor {
	private static final long serialVersionUID = 1L;
	
	public TranscoderCWMsgProcessorBehaviour(int[] waitingTimes, MessageTemplate template) {
		super(waitingTimes, template);
	}

	public Transcoder getAgent() {
		if (Transcoder.class.isAssignableFrom(myAgent.getClass())) {
			return (Transcoder) myAgent;
		}
		// FIXME: preciso achar uma exceção melhor do que essa.
		throw new RuntimeException("Agent is not a transcoder.");
	}	

}
