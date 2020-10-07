package br.unb.cic.comnet.distran.agents.transcoder;

public enum TranscoderProfile {
	
	A(210, 290, 300, 500), 
	B(400, 600, 300, 500), 
	C(750, 1250, 300, 500), 
	D(1000, 2000, 1000, 2000);
	
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
