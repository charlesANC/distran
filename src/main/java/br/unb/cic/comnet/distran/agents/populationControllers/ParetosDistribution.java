package br.unb.cic.comnet.distran.agents.populationControllers;

public class ParetosDistribution implements DiscreteDistribution {
	
	private double xm;
	private double alpha;
	private double add;
	private int initialNumber = 0;
	
	public ParetosDistribution(int initialNumber, double xm, double alpha, double add) {
		this.initialNumber = initialNumber;
		this.xm = xm;
		this.alpha = alpha;
		this.add = add;
	}

	@Override
	public Integer position(Integer turn) {
		double x = turn * add;
		if (x < xm) return initialNumber;
		Long value = Math.round(initialNumber * (Math.pow(xm * alpha, alpha) / Math.pow(x, alpha + 1))); 
		return value.intValue(); 
	}

}
