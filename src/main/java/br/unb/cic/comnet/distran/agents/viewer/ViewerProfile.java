package br.unb.cic.comnet.distran.agents.viewer;

public enum ViewerProfile {
	
	A(200, 300), B(400, 600), C(750, 1250);
	
	private int lowerReceivingTime;
	private int higherReceivingTime;
	
	public int getLowerReceivingTime() {
		return lowerReceivingTime;
	}
	
	public int getHigherReceivingTime() {
		return higherReceivingTime;
	}	
	
	private ViewerProfile(int lowerReceivingTime, int higherReceivingTime) {
		this.lowerReceivingTime = lowerReceivingTime;
		this.higherReceivingTime = higherReceivingTime;
	}
}
