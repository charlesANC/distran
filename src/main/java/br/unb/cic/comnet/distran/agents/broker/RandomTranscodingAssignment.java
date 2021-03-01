package br.unb.cic.comnet.distran.agents.broker;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import br.unb.cic.comnet.distran.util.RandomService;
import jade.core.AID;
import jade.core.Agent;

public class RandomTranscodingAssignment extends AbstractTranscodingAssigment {
	private static final long serialVersionUID = 1L;
	
	public RandomTranscodingAssignment(Agent a, long period) {
		super(a, period);
	}

	@Override
	public Optional<AID> drawATranscoder() {
		List<AID> transcoders =  
				getAgent().getTranscoders().stream()
					.map(x -> x.getAID())
					.collect(Collectors.toList());
		
		if (transcoders.isEmpty()) return Optional.empty();
			
		int rand = getRandom().nextInt(transcoders.size() * 100);
		int index = Math.floorDiv(rand, 100);
		return Optional.ofNullable(transcoders.get(index));
	}

	private Random getRandom() {
		return RandomService.getInstance().getClassGenerator(this.getClass());
	}
}
