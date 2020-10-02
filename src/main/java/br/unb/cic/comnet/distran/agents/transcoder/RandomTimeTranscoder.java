package br.unb.cic.comnet.distran.agents.transcoder;

import java.security.InvalidParameterException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import br.unb.cic.comnet.distran.agents.services.TranscodingServiceDescriptor;
import br.unb.cic.comnet.distran.player.Segment;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.util.Logger;

public class RandomTimeTranscoder extends Transcoder {
	private static final long serialVersionUID = 1L;
	
	Logger logger = Logger.getJADELogger(getClass().getName());
	
	private Map<String, Segment> segments;
	
	public TranscoderProfile getProfile() {
		if (getArguments().length == 0) {
			throw new InvalidParameterException("A agent transcoder needs a profile as argument.");
		}
		
		TranscoderProfile profile = TranscoderProfile.valueOf(getArguments()[0].toString());
		
		if (profile == null) {
			throw new InvalidParameterException(getArguments()[0] + " is not a valid transcoder profile.");			
		}
		
		return profile;
	}
	
	protected void setup() {
		TranscoderProfile profile = getProfile();
		
		logger.log(Logger.INFO, "Starting transcoder " + getName() + " with profile " + profile.toString());
		segments = new ConcurrentHashMap<String, Segment>();
		
		addBehaviour(new SegmentProviderBehaviour(profile.getLowerServingTime(), profile.getHigherServingTime()));
		addBehaviour(new TranscodeSegmentBehaviour(profile.getLowerTranscodingTime(), profile.getHigherTranscodingTime()));
		
		publishMe();
	}
	

	@Override
	public Optional<Segment> getSegment(String key) {
		return Optional.ofNullable(segments.get(key));
	}
	
	@Override
	public void addSegment(Segment segment) {
		segments.put(segment.getId(), segment);
	}
	
	private void publishMe() {
		DFAgentDescription desc = new DFAgentDescription();
		desc.setName(getAID());
		desc.addServices(TranscodingServiceDescriptor.create(getLocalName()));
		try {
			DFService.register(this, desc);
		} catch (FIPAException e) {
			e.printStackTrace();
			logger.log(Logger.SEVERE, "I cannot publish myself. I am useless. I must die! " + getName());
			doDelete();
		}
	}
}
