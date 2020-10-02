package br.unb.cic.comnet.distran.agents.transcoder;

public enum TranscoderProfile {
	
	A(1000, 2000, 100, 300), B(2000, 3000, 300, 600), C(5000, 8000, 600, 1000);
	
	private Integer lowerServingTime;
	private Integer higherServingTime;
	private Integer lowerTranscodingTime;
	private Integer higherTranscodingTime;
	
	private TranscoderProfile(
			Integer lowerServingTime,
			Integer higherServingTime,
			Integer lowerTranscodingTime,
			Integer higherTranscodingTime
	) {
		this.lowerServingTime = lowerServingTime;
		this.higherServingTime = higherServingTime;
		this.lowerTranscodingTime = lowerTranscodingTime;
		this.higherTranscodingTime = higherTranscodingTime;		
	}
	
	public Integer getLowerServingTime() {
		return lowerServingTime;
	}
	
	public Integer getHigherServingTime() {
		return higherServingTime;
	}
	
	public Integer getLowerTranscodingTime() {
		return lowerTranscodingTime;
	}
	
	public Integer getHigherTranscodingTime() {
		return higherTranscodingTime;
	}
}
