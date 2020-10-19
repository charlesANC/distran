package br.unb.cic.comnet.distran.agents.populationControllers;

public class PiramidalDistribution implements DiscreteDistribution {
	
	private int step;
	private int roof;
	
	public PiramidalDistribution(int step, int roof) {
		this.step = step;
		this.roof = roof;
	}

	@Override
	public Integer position(Integer turn) {
		int top = (roof / step);
		int pos = turn % (2 * top);
		if (pos <= top) {
			return pos * step;
		} 
		return step * ((2 * top) - pos);
	}

}
