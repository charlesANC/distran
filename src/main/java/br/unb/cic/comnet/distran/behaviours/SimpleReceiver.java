package br.unb.cic.comnet.distran.behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class SimpleReceiver extends CyclicBehaviour {
	private static final long serialVersionUID = 1L;

	@Override
	public void action() {
		ACLMessage msg = myAgent.receive();
		if (msg != null) {
			System.out.println("Received: " + msg.getContent());
		} else {
			block();
		}
	}
}
