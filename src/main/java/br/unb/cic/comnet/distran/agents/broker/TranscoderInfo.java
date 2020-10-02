package br.unb.cic.comnet.distran.agents.broker;

import java.util.ArrayList;
import java.util.List;

import br.com.tm.repfogagent.trm.Rating;
import jade.core.AID;

public class TranscoderInfo implements Comparable<TranscoderInfo> {
	
	private AID aid;
	private List<Rating> ratings;
	private Double totalUtility;
	private double trustworthy;
	
	public TranscoderInfo(AID aid) {
		this.aid = aid;
		ratings = new ArrayList<Rating>();
	}
	
	public AID getAID() {
		return aid;
	}
	
	public List<Rating> getRatings() {
		return new ArrayList<Rating>(ratings);
	}
	
	public void addRating(Rating rating) {
		ratings.add(rating);
	}
	
	public Double getTotalUtility() {
		return totalUtility;
	}
	public void setTotalUtility(Double totalUtility) {
		this.totalUtility = totalUtility;
	}

	public Double getTrustworthy() {
		return trustworthy;
	}
	public void setTrustworthy(Double trustworthy) {
		this.trustworthy = trustworthy;
	}

	@Override
	public int compareTo(TranscoderInfo o) {
		if (o == null) return 1;
		return getAID().compareTo(o.getAID());
	}
}
