package br.unb.cic.comnet.distran.agents.viewer;

import java.util.PriorityQueue;
import java.util.Queue;

import jade.core.AID;
import jade.util.Logger;

public class SequentialViewer extends Viewer {

	private static final long serialVersionUID = 1L;
	
	private Logger logger = Logger.getJADELogger(getClass().getName());
	
	private AID broker;
	
	private Queue<String> buffer;
	
	private int bufferLength = 5;
	private int lastSegment = -1;
	
	public SequentialViewer() {
		super();
		
		buffer = new PriorityQueue<String>();
	}

	@Override
	protected void setup() {
		logger.log(Logger.INFO, "Viewer " + getName() + " is on.");
		
		addBehaviour(new BrokerSearchBehaviour(this, 1000));
		addBehaviour(new SegmentRemoverBehaviour(this, 2000));
		addBehaviour(new PlaylistProcessorBehaviour(100, 200));
	}
	
	public boolean hasBroker() {
		return broker != null;
	}

	@Override
	public AID getBroker() {
		return broker;
	}
	
	@Override
	public void setBroker(AID broker) {
		this.broker = broker;
	}	

	@Override
	public boolean hasEmptySlot() {
		return buffer.size() < bufferLength;
	}

	@Override
	public int nextSegment() {
		return lastSegment++;
	}

	@Override
	public void removeFirstSegment() {
		if (buffer.isEmpty()) return;
		
		buffer.poll();
	}

	@Override
	public void addSegment(String segment) {
		buffer.add(segment);
	}
}
