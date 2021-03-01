package br.unb.cic.comnet.distran.agents.broker;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import br.unb.cic.comnet.distran.player.Segment;
import jade.core.AID;
import jade.core.Agent;

public abstract class Broker extends Agent {
	private static final long serialVersionUID = 1L;

	public abstract boolean hasNoSegments();
	public abstract void addSegment(Segment segment);
	public abstract Optional<Segment> getSegment(String segmentId);
	public abstract long segmentCount();
	public abstract List<Segment> getPlaylist();
	public abstract List<Segment> getNonAssignedSegments();
	
	public abstract void addTranscoders(Set<AID> newTranscoders);
	public abstract Set<TranscoderInfo> getTranscoders();
	
	public abstract void addTranscoderRating(UtilityFeedback feedback);
	public abstract Map<String, Set<UtilityFeedback>> getFeedbacks();
	
	public abstract void evaluateTranscoders();
}
