package br.unb.cic.comnet.distran.agents.broker;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UtilityFeedback implements Serializable, Comparable<UtilityFeedback> {
	private static final long serialVersionUID = 1L;
	
	private String evaluator;
	private String segmentId;
	private String provider;
	private Long playInterval;
	private Double utility;
	private Double maxUtility;
	private Double standardUtility;
	private LocalDateTime timestamp;
	
	public UtilityFeedback(
			String evaluator,
			String id,
			String provider,
			Long playInterval,
			Double utility,
			Double maxUtility,
			Double standardUtility,
			LocalDateTime timestamp
	) {
		this.evaluator = evaluator;
		this.segmentId = id;
		this.provider = provider;
		this.playInterval = playInterval;
		this.utility = utility;
		this.maxUtility = maxUtility;
		this.standardUtility = standardUtility;
		this.timestamp = timestamp;
	}
	
	public UtilityFeedback() {}
	
	public String getEvaluator() {
		return evaluator;
	}
	public void setEvaluator(String evaluator) {
		this.evaluator = evaluator;
	}

	public String getSegmentId() {
		return segmentId;
	}
	public void setSegmentId(String id) {
		this.segmentId = id;
	}
	
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	
	public Long getPlayInterval() {
		return playInterval;
	}
	public void setPlayInterval(Long playInterval) {
		this.playInterval = playInterval;
	}

	public Double getUtility() {
		return utility;
	}
	public void setUtility(Double utility) {
		this.utility = utility;
	}
	
	public Double getMaxUtility() {
		return maxUtility;
	}
	public void setMaxUtility(Double maxUtility) {
		this.maxUtility = maxUtility;
	}

	public Double getStandardUtility() {
		return standardUtility;
	}
	public void setStandardUtility(Double standardUtility) {
		this.standardUtility = standardUtility;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	public String toString() {
		return String.format("%s: %s [ %s ] : %.2f", 
				getTimestamp().toString(), 
				getProvider(), 
				getSegmentId(), 
				getUtility());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((evaluator == null) ? 0 : evaluator.hashCode());
		result = prime * result + ((provider == null) ? 0 : provider.hashCode());
		result = prime * result + ((segmentId == null) ? 0 : segmentId.hashCode());
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
		UtilityFeedback other = (UtilityFeedback) obj;
		if (evaluator == null) {
			if (other.evaluator != null)
				return false;
		} else if (!evaluator.equals(other.evaluator))
			return false;
		if (provider == null) {
			if (other.provider != null)
				return false;
		} else if (!provider.equals(other.provider))
			return false;
		if (segmentId == null) {
			if (other.segmentId != null)
				return false;
		} else if (!segmentId.equals(other.segmentId))
			return false;
		return true;
	}

	@Override
	public int compareTo(UtilityFeedback o) {
		if (o == null) return 1;
		
		return getSegmentId().compareTo(o.getSegmentId()) * 1000 + 
				getEvaluator().compareTo(o.getEvaluator()) * 100 + 
					getProvider().compareTo(getProvider());
	}
}
