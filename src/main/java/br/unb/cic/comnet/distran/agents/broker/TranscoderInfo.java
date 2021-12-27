package br.unb.cic.comnet.distran.agents.broker;

import java.io.Serializable;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import br.com.tm.repfogagent.trm.Rating;
import br.unb.cic.comnet.distran.agents.GeneralParameters;
import br.unb.cic.comnet.distran.agents.trm.FactoryRating;
import jade.core.AID;

public class TranscoderInfo implements Serializable, Comparable<TranscoderInfo> {
	private static final long serialVersionUID = 1L;
	
	private AID aid;
	private Map<String, Set<Rating>> ratings;
	private Double totalUtility = 0.0;
	private Double trustworthy = (1.0 + GeneralParameters.getTrustThreshold()) / 2;
	private Double reliability = 0.0;
	
	public TranscoderInfo(AID aid) {
		this.aid = aid;
		ratings = new ConcurrentHashMap<String, Set<Rating>>();
	}
	
	public AID getAID() {
		return aid;
	}
	
	public Map<String, Set<Rating>> getRatings() {
		return new LinkedHashMap<String, Set<Rating>>(ratings);
	}
	
	public Integer getNumOfRatings() {
		int totalSize = 0;
		for(String key : ratings.keySet()) {
			totalSize += ratings.get(key).size();
		}
		return totalSize;
	}
	
	public void addRating(UtilityFeedback feedback) {
		if (!ratings.containsKey(feedback.getEvaluator())) {
			ratings.put(feedback.getEvaluator(), new TreeSet<Rating>(new Comparator<Rating>() {
				@Override
				public int compare(Rating o1, Rating o2) {
					return Integer.valueOf(o1.getIteration()).compareTo(o2.getIteration());
				}
			}));
		}
		
		ratings.get(feedback.getEvaluator()).add(FactoryRating.create(feedback));
		setTotalUtility(getTotalUtility() + feedback.getUtility());
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
	
	public Double getReliability() {
		return reliability;
	}
	public void setReliability(Double reliability) {
		this.reliability = reliability;
	}

	@Override
	public int compareTo(TranscoderInfo o) {
		if (o == null) return 1;
		return getAID().compareTo(o.getAID());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aid == null) ? 0 : aid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TranscoderInfo other = (TranscoderInfo) obj;
		if (aid == null) {
			if (other.aid != null)
				return false;
		} else if (!aid.equals(other.aid))
			return false;
		return true;
	}
}
