package br.unb.cic.comnet.distran.player;

import br.unb.cic.comnet.distran.agents.populationControllers.DiscreteDistribution;
import br.unb.cic.comnet.distran.agents.populationControllers.ParetosDistribution;

public class TestDistributions {

	public static void main(String[] args) {
		DiscreteDistribution distribution = new ParetosDistribution(500, 1, 0.7, 0.1);
		for(int i = 0; i < 100; i++) {
			System.out.println(distribution.position(i));
		}

	}

}
