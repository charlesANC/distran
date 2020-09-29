package br.unb.cic.comnet.distran.behaviours;

import java.util.Random;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public abstract class MessageProcessorBehaviour extends CyclicBehaviour {
	private static final long serialVersionUID = 1L;
	
	private int low;
	private int high;
	private MessageTemplate template;
		
	public MessageProcessorBehaviour(int low, int high, MessageTemplate template) {
		super();
		this.low = low;
		this.high = high;
		this.template = template;
	}

	@Override
	public void action() {
		ACLMessage msg = myAgent.receive(template);
		if (msg != null) {
			myAgent.doWait(makeInterval());
			doAction(msg);
		} else {
			block();
		}
	}
	
	public abstract void doAction(ACLMessage msg);

	private int makeInterval() {
		return low + new Random().nextInt(high - low);
	}
}
