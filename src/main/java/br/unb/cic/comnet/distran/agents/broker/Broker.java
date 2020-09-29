package br.unb.cic.comnet.distran.agents.broker;

import java.util.Map;
import java.util.Set;

import jade.core.AID;
import jade.core.Agent;

public abstract class Broker extends Agent {
	private static final long serialVersionUID = 1L;

	public abstract void addSegment(String segment);
	public abstract Set<String> getSegments();
	
	public abstract void addTranscoders(Set<AID> newTranscoders);
	public abstract Set<AID> getTranscoders();
	
	public abstract void addAssignment(String segment, String transcoder);
	public abstract Map<String, String> getAssignments();

}
