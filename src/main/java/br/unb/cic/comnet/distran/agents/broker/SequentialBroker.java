package br.unb.cic.comnet.distran.agents.broker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import br.unb.cic.comnet.distran.agents.services.BrokerageServiceDescriptor;
import br.unb.cic.comnet.distran.player.Segment;
import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.util.Logger;

public class SequentialBroker extends Broker {
	private static final long serialVersionUID = 1L;
	
	Logger logger = Logger.getJADELogger(getClass().getName());
	
	private Set<AID> transcoders;
	private Set<Segment> segments;
	
	public SequentialBroker() {
		super();
		
		transcoders = new TreeSet<AID>();
		segments = new LinkedHashSet<Segment>();
	}
	
	@Override
	protected void setup() {
		logger.log(Logger.INFO, "Starting broker " + getName());
		
		addBehaviour(new TranscoderSearcher(this, 10000));
		addBehaviour(new SegmentGenerator(this, 2000));
		addBehaviour(new RandomTranscodingAssignment(this, 1000));
		addBehaviour(new PlaylistProviderBehaviour(100, 200));
		
		publishMe();		
	}
	
	@Override
	public boolean hasNoSegments() {
		return segments.isEmpty();
	}

	@Override
	public void addSegment(Segment segment) {
		segments.add(segment);
	}
	
	@Override
	public List<Segment> getPlaylist() {
		Collection<Segment> segs = 
			segments.stream()
				.filter(Segment::hasSource)
				.sorted()
				.collect(Collectors.toList());
		
		return new ArrayList<Segment>(segs);
	}
	
	@Override
	public List<Segment> getNonAssignedSegments() {
		return segments.stream()
				.filter(x->{return !x.hasSource();})
				.collect(Collectors.toList());
	}
	
	@Override
	public void addTranscoders(Set<AID> newTranscoders) {
		transcoders.addAll(newTranscoders);
	}
	
	@Override
	public Set<AID> getTranscoders() {
		return new TreeSet<AID>(transcoders);
	}	
	
	private void publishMe() {
		DFAgentDescription desc = new DFAgentDescription();
		desc.setName(getAID());
		desc.addServices(BrokerageServiceDescriptor.create(getLocalName()));
		try {
			DFService.register(this, desc);
		} catch (FIPAException e) {
			e.printStackTrace();
			logger.log(Logger.SEVERE, "I cannot publish myself. I am useless. I must die! " + getName());
			doDelete();
		}
	}
}
