package br.unb.cic.comnet.distran.agents.broker.FIRE;

import java.security.InvalidParameterException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import br.unb.cic.comnet.distran.agents.GeneralParameters;
import br.unb.cic.comnet.distran.agents.broker.Broker;
import br.unb.cic.comnet.distran.agents.broker.DirectTrustTranscodingAssignment;
import br.unb.cic.comnet.distran.agents.broker.PlaylistProviderBehaviour;
import br.unb.cic.comnet.distran.agents.broker.PrintUtilityBehaviour;
import br.unb.cic.comnet.distran.agents.broker.SegmentGenerator;
import br.unb.cic.comnet.distran.agents.broker.TranscoderInfo;
import br.unb.cic.comnet.distran.agents.broker.TranscoderSearcher;
import br.unb.cic.comnet.distran.agents.broker.UtilityFeedback;
import br.unb.cic.comnet.distran.agents.evaluators.FIRETranscoderEvaluator;
import br.unb.cic.comnet.distran.agents.services.BrokerageServiceDescriptor;
import br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;
import br.unb.cic.comnet.distran.agents.viewer.ViewerProfile;
import br.unb.cic.comnet.distran.player.Segment;
import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.util.Logger;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class FIRETRMBroker extends Broker {
	private static final long serialVersionUID = 1L;
	
	Logger logger = Logger.getJADELogger(getClass().getName());
	
	private String accreditedViewer;
	private Map<AID, TranscoderInfo> transcoders;
	private Set<Segment> segments;
	private Map<String, Set<UtilityFeedback>> feedbacks;
	
	public FIRETRMBroker() {
		super();
		
		transcoders = new TreeMap<AID, TranscoderInfo>();
		segments = new LinkedHashSet<Segment>();
		feedbacks = new ConcurrentHashMap<String, Set<UtilityFeedback>>();
	}
	
	@Override
	protected void setup() {
		logger.log(Logger.INFO, "Starting broker " + getName());
		
		addBehaviour(new TranscoderSearcher(this, 10000));
		addBehaviour(new SegmentGenerator(this, GeneralParameters.getDuration(), true, GeneralParameters.getNumOfSegments()));		
		addBehaviour(new PlaylistProviderBehaviour(100, 200));
		addBehaviour(new PrintUtilityBehaviour(this, 12000));
		
		addBehaviour(new DirectTrustTranscodingAssignment(this, GeneralParameters.getDuration()));			
		
		this.accreditedViewer = createAccreditedViewer();
		
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
	public long segmentCount() {
		return segments.size();
	}
	
	@Override
	public List<Segment> getPlaylist() {
		List<Segment> hasSource = 
				segments.stream()
					.filter(Segment::hasSource)
					.sorted()					
					.collect(Collectors.toList());
		
		return hasSource;
	}
	
	@Override
	public List<Segment> getNonAssignedSegments() {
		return segments.stream()
				.filter(x->{return !x.hasSource();})
				.sorted()
				.collect(Collectors.toList());
	}
	
	@Override
	public void addTranscoders(Set<AID> newTranscoders) {
		newTranscoders.stream().forEach(aid -> transcoders.putIfAbsent(aid, new TranscoderInfo(aid)));
	}
	
	@Override
	public Set<TranscoderInfo> getTranscoders() {
		return new TreeSet<TranscoderInfo>(transcoders.values());
	}	
	
	@Override
	public void addTranscoderRating(UtilityFeedback feedback) {
		addSegmentFeedback(feedback);
		
		AID aid = new AID(feedback.getProvider(), true);
		if (transcoders.containsKey(aid)) {
			transcoders.get(aid).addRating(feedback);
		} else {
			throw new InvalidParameterException("I do not have a transcoder named " + feedback.getProvider());			
		}
	}
	
	@Override
	public Map<String, Set<UtilityFeedback>> getFeedbacks() {
		return feedbacks;
	}

	@Override
	public Optional<Segment> getSegment(String segmentId) {
		for(Segment seg : segments) {
			if (seg.getId().equals(segmentId)) {
				return Optional.of(seg);
			}
		}
		return Optional.empty();
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

	private void addSegmentFeedback(UtilityFeedback feedback) {
		if (!feedbacks.containsKey(feedback.getSegmentId())) {
			feedbacks.put(feedback.getSegmentId(), new TreeSet<UtilityFeedback>());
		}
		feedbacks.get(feedback.getSegmentId()).add(feedback);
	}
	
	@Override
	public void evaluateTranscoders() {
		new FIRETranscoderEvaluator().evaluateTranscoders(getTranscoders(), accreditedViewer);
	}
	
	@Override
	public long segmentCountBySource(String source) {
		return segments.stream()
				.filter(x -> (x.getSource() != null && x.getSource().equals(source)))
				.count();
	}	
	
	private String createAccreditedViewer() {
		AgentController viewer;
		try {
			viewer = getContainerController()
				.createNewAgent(
						"vbk", 
						SequentialPlayViewer.class.getName(), 
						new String[] {ViewerProfile.BK.name()});
			
			viewer.start();
			logger.log(Logger.INFO, "Viewer" + viewer.getName() + " has been created.");
			return viewer.getName();
			
		} catch (StaleProxyException e) {
			logger.log(Logger.SEVERE, "Error on creating broker viewer! " + e.getMessage());
			return null;
		}
	}
}
