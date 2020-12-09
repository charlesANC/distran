package br.unb.cic.comnet.distran.agents.populationControllers;

public class PiramidalDistribution implements DiscreteDistribution {
	
	private int step;
	private int roof;
	private int maxTurn;
	
	public PiramidalDistribution(int step, int roof, int maxTurn) {
		this.step = step;
		this.roof = roof;
		this.maxTurn = maxTurn;
	}

	@Override
	public Integer position(Integer turn) {
		turn = Math.min(turn, maxTurn);
		
		int top = (roof / step);
		int pos = turn % (2 * top);
		if (pos <= top) {
			return pos * step;
		} 
		return step * ((2 * top) - pos);
	}

}
