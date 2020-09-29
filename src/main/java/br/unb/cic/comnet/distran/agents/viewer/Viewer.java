package br.unb.cic.comnet.distran.agents.viewer;

import jade.core.AID;
import jade.core.Agent;

public abstract class Viewer extends Agent {
	private static final long serialVersionUID = 1L;
	
	public abstract boolean hasBroker();
	public abstract AID getBroker();
	public abstract void setBroker(AID broker);
	
	public abstract boolean hasEmptySlot();
	public abstract int nextSegment();
	
	public abstract void removeFirstSegment();
	public abstract void addSegment(String segment);
}
