package br.unb.cic.comnet.distran.agents.services;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class TranscodingServiceDescriptor extends ServiceDescription {
	private static final long serialVersionUID = 1L;
	
	public static TranscodingServiceDescriptor create(String localName) {
		TranscodingServiceDescriptor service = new TranscodingServiceDescriptor();
		service.setName(localName + "-" + service.getType());
		return service;
	}

	public static Set<AID> search(Agent client) throws FIPAException {
		DFAgentDescription template = new DFAgentDescription();
		template.addServices(new TranscodingServiceDescriptor());
		
		DFAgentDescription[] result = DFService.search(client, template);
		
		return Arrays.asList(result).stream()
				.map(x->x.getName())
				.collect(Collectors.toSet());
	}

	private TranscodingServiceDescriptor() {
		setType("Transcoding");
	}
}
