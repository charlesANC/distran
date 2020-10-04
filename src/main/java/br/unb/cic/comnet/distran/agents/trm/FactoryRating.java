package br.unb.cic.comnet.distran.agents.trm;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import br.com.tm.repfogagent.trm.Rating;
import br.unb.cic.comnet.distran.agents.broker.UtilityFeedback;

public class FactoryRating {
	
	public static Rating create(UtilityFeedback feedback) {
		return new Rating(
					feedback.getProvider(),
					feedback.getEvaluator(),
					feedback.getStandardUtility(),
					feedback.getStandardUtility(),
					calculateIteration(feedback.getSegmentId()),
					"Transcoding",
					convert(feedback.getTimestamp())
				);
	}
	
	public static Integer calculateIteration(String segmentId) {
		// FIXME: O modelo do tiago está acoplado a simulação episódica que ele construiu
		// antes de usar esse modelo para simulação utilizando reputação temos de 
		// eliminar essa dependência, além de usar LocalDateTime ao invés de Date
		return Integer.valueOf(segmentId.substring(1));
	}
	
	private static Date convert(LocalDateTime time) {
		return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
	}

}
