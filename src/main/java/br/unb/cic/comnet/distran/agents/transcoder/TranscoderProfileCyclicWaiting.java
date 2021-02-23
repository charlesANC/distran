package br.unb.cic.comnet.distran.agents.transcoder;

public enum TranscoderProfileCyclicWaiting {
	
	A(new int[] {
			1094,
			1367,
			1823,
			2734,
			3646,
			2734,
			1823,
			1367
	}), 
	B(new int[] {
			1094,
			1367,
			1823,
			2734,
			3646,
			2734,
			1823,
			1367
	}), 
	C(new int[] {
			608,
			1367,
			2734,
			5469,
			2734,
			1367
	});
	
	private int[] waitingTimes;
	
	private TranscoderProfileCyclicWaiting(int[] waitingTimes) {
		this.waitingTimes = waitingTimes;		
	}
	
	public int[] getWaitingTimes() {
		return waitingTimes;
	}
}
