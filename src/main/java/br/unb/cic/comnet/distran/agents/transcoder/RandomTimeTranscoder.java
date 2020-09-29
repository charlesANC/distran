package br.unb.cic.comnet.distran.agents.transcoder;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import br.unb.cic.comnet.distran.agents.services.TranscodingServiceDescriptor;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.util.Logger;

public class RandomTimeTranscoder extends Transcoder {
	private static final long serialVersionUID = 1L;
	
	Logger logger = Logger.getJADELogger(getClass().getName());
	
	private Map<String, String> segments;
	
	protected void setup() {
		logger.log(Logger.INFO, "Starting transcoder " + getName());
		segments = new ConcurrentHashMap<String, String>();
		
		addBehaviour(new SegmentProviderBehaviour(1000, 2000));
		addBehaviour(new TranscodeSegmentBehaviour(300, 400));
		
		publishMe();
	}

	@Override
	public Optional<String> getSegment(String key) {
		return Optional.ofNullable(segments.get(key));
	}
	
	@Override
	public void addSegment(String key, String segment) {
		segments.put(key, segment);
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
