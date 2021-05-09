package br.unb.cic.comnet.distran.agents.broker;

import java.util.Collection;
import java.util.Set;

import br.com.tm.repfogagent.trm.Rating;

public class AverageUtilityEvaluator {
	
	public void evaluateTranscoders(Collection<TranscoderInfo> transcoders) {
		for(TranscoderInfo transcoder : transcoders) {
			long qtdRatings = 0;
			double sumUtility = 0;
			
			for(Set<Rating> ratings : transcoder.getRatings().values()) {
				for(Rating rating : ratings) {
					qtdRatings++;
					sumUtility += rating.getNormalizedValue();					
				}
			}
			
			transcoder.setTrustworthy(sumUtility / qtdRatings);
		}
	}
}
