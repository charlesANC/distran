package br.unb.cic.comnet.distran.behaviours;

import java.util.Random;

import br.unb.cic.comnet.distran.util.RandomService;
import jade.lang.acl.MessageTemplate;

public abstract class NonBlockingRandomWaitingMessageProcessor extends NonBlockingMessageProcessorBehaviour {
	private static final long serialVersionUID = 1L;
	
	private int low;
	private int high;
		
	public NonBlockingRandomWaitingMessageProcessor(int low, int high, MessageTemplate template) {
		super(template);
		this.low = low;
		this.high = high;
	}

	@Override
	public int getInterval() {
		int diff = high - low;
		if (diff == 0) return low;
		return low + getRandom().nextInt(diff);
	}

	private Random getRandom() {
		return RandomService.getInstance().getClassGenerator(this.getClass());
	}
}
