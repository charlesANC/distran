package br.unb.cic.comnet.distran.agents.transcoder;

import java.util.Optional;

import br.unb.cic.comnet.distran.player.Segment;
import jade.core.Agent;

public abstract class Transcoder extends Agent {
	private static final long serialVersionUID = 1L;
	
	public abstract Optional<Segment> getSegment(String id);
	
	public abstract void addSegment(Segment segment);
}
