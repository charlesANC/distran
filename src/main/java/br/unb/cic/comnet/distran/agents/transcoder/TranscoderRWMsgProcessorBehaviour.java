package br.unb.cic.comnet.distran.agents.transcoder;

import br.unb.cic.comnet.distran.behaviours.NonBlockingRandomWaitingMessageProcessor;
import jade.lang.acl.MessageTemplate;

public abstract class TranscoderRWMsgProcessorBehaviour extends NonBlockingRandomWaitingMessageProcessor {
	private static final long serialVersionUID = 1L;
	
	public TranscoderRWMsgProcessorBehaviour(int low, int high, MessageTemplate template) {
		super(low, high, template);
	}

	public Transcoder getAgent() {
		if (Transcoder.class.isAssignableFrom(myAgent.getClass())) {
			return (Transcoder) myAgent;
		}
		// FIXME: preciso achar uma exceção melhor do que essa.
		throw new RuntimeException("Agent is not a transcoder.");
	}	

}
