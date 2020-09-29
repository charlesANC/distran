package br.unb.cic.comnet.distran.agents.transcoder;

import java.util.Optional;

import jade.core.Agent;

public abstract class Transcoder extends Agent {
	private static final long serialVersionUID = 1L;
	
	public abstract Optional<String> getSegment(String id);
	
	public abstract void addSegment(String id, String segment);
}
