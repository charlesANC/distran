package br.com.tm.repfogagent.trm.components;

import java.util.Date;
import java.util.List;

import br.com.tm.repfogagent.trm.Rating;
import br.com.tm.repfogagent.util.DateUtil;

public class InteractionTrustComponent extends FIREBaseComponent {

	private double lambda; //recency scaling factor
	
	public InteractionTrustComponent(double lambda) {
		this.lambda = lambda != 0.0 ? lambda : (5 / Math.log(0.5));
	}
	
	public InteractionTrustComponent(double lambda, double coefficient) {
		this.lambda = lambda != 0.0 ? lambda : (5 / Math.log(0.5));
		setCoefficient(coefficient);
	}
	
	@Override
	public double weight(Rating rating, long currentIteration) {
		return recencyWeight(rating.getIteration(), currentIteration);
	}
	
	private double recencyWeight(long ti, long currentIteration) {
		return Math.exp(Math.abs(currentIteration - ti) / lambda);
	}
}
