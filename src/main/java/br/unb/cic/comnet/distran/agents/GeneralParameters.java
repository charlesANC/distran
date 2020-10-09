package br.unb.cic.comnet.distran.agents;

public class GeneralParameters {
	
	private static final Long DURATION = 2000L;
	private static final Double BETHA = 250D;
	private static final Double ALPHA = 1D;
	private static final Integer BUFFER_LENGTH = 3;
	private static final Double TRUST_THRESHOLD = 0.75;
	
	public static Long getDuration() {
		return DURATION;
	}
	
	public static Double getAlpha() {
		return ALPHA;
	}
	
	public static Double getBetha() {
		return BETHA;
	}
	
	public static Integer getBufferLength() {
		return BUFFER_LENGTH;
	}

	public static Double getTrustThreshold() {
		return TRUST_THRESHOLD;
	}
}
