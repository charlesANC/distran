package br.unb.cic.comnet.distran.agents;

import java.io.File;

public class GeneralParameters {
	
	private static final Long DURATION = 2000L;
	private static final Double BETHA = 250D;
	private static final Double ALPHA = 1D;
	private static final Integer BUFFER_LENGTH = 5;
	private static final Double TRUST_THRESHOLD = 0.666;
	private static final Integer WINDOW_LENGTH = 15;
	private static final Integer NUM_OF_SEGMENTS = 100;
	
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

	public static int getWindowLength() {
		return WINDOW_LENGTH;
	}
	
	public static int getNumOfSegments() {
		return NUM_OF_SEGMENTS;
	}
	
	public static String mountOutputFileName(String fileName) {
		String dir = System.getProperty("outDir", "c:\\temp\\");
		File file = new File(dir, fileName);
		return file.getAbsolutePath();
	}
}
