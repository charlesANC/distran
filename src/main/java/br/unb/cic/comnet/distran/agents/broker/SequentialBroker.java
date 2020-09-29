package br.unb.cic.comnet.distran.agents.broker;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import br.unb.cic.comnet.distran.agents.services.BrokerageServiceDescriptor;
import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.util.Logger;

public class SequentialBroker extends Broker {
	private static final long serialVersionUID = 1L;
	
	Logger logger = Logger.getJADELogger(getClass().getName());
	
	private Set<AID> transcoders;
	private Set<String> segments;
	private Map<String, String> assignments;
	
	public SequentialBroker() {
		super();
		
		transcoders = new TreeSet<AID>();
		segments = new TreeSet<String>();
		assignments = new LinkedHashMap<String, String>();
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
	public void addSegment(String segment) {
		segments.add(segment);
	}
	
	@Override
	public Set<String> getSegments() {
		return new TreeSet<String>(segments);
	}
	
	@Override
	public void addTranscoders(Set<AID> newTranscoders) {
		transcoders.addAll(newTranscoders);
	}
	
	@Override
	public Set<AID> getTranscoders() {
		return new TreeSet<AID>(transcoders);
	}	
	
	@Override
	public void addAssignment(String segment, String transcoder) {
		assignments.put(segment, transcoder);
	}

	@Override
	public Map<String, String> getAssignments() {
		return new LinkedHashMap<String, String>(assignments);
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
