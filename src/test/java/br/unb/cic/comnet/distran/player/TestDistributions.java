package br.unb.cic.comnet.distran.player;

import br.unb.cic.comnet.distran.agents.populationControllers.DiscreteDistribution;
import br.unb.cic.comnet.distran.agents.populationControllers.StepDistribution;

public class TestDistributions {

	public static void main(String[] args) {
		//DiscreteDistribution distribution = new ParetosDistribution(120, 1, 0.7, 0.1);
		//DiscreteDistribution distribution = new PiramidalDistribution(5, 100, 40);
		DiscreteDistribution distribution = new StepDistribution(120, 30, 90);
		for(int i = 0; i < 120; i++) {
			System.out.println(distribution.position(i));
		}

	}

}
