package br.unb.cic.comnet.distran.behaviours;

import jade.lang.acl.MessageTemplate;

public abstract class CyclicWaitingMessageProcessor extends MessageProcessorBehaviour {
	private static final long serialVersionUID = 1L;
	
	private int[] waitingTimes;
	private int turn;
	
	public CyclicWaitingMessageProcessor(int[] waitingTimes, MessageTemplate template) {
		super(template);
		this.waitingTimes = waitingTimes;
		this.turn = -1;
	}
	
	@Override
	public int getInterval() {
		return waitingTimes[getNextTurn()];
	}
	
	private int getNextTurn() {
		return (++turn) % waitingTimes.length;
	}
}
