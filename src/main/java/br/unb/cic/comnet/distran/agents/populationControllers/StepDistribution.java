package br.unb.cic.comnet.distran.agents.populationControllers;

public class StepDistribution implements DiscreteDistribution {
	
	private int start;
	private int stop;
	private int value;
	
	public StepDistribution(int value, int start, int stop) {
		this.value = value;
		this.start = start;
		this.stop = stop;
	}

	@Override
	public Integer position(Integer turn) {
		if (turn >= start && turn <= stop) {
			return value;
		}
		return 0;
	}

}
