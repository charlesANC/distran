
public class RodarDistran {
/*
	public static void main(String[] args) {
		String[] rodarDistran = new String[] {
				"-gui", 
				" -agents " + 
					"b1:br.unb.cic.comnet.distran.agents.broker.SequentialBroker(R);" + 
					"ta1:br.unb.cic.comnet.distran.agents.transcoder.RandomTimeTranscoder(A);" +
					"tb1:br.unb.cic.comnet.distran.agents.transcoder.RandomTimeTranscoder(B);" +					
					"tb2:br.unb.cic.comnet.distran.agents.transcoder.RandomTimeTranscoder(B);" +
					"tc1:br.unb.cic.comnet.distran.agents.transcoder.RandomTimeTranscoder(C);" +					
					"tc2:br.unb.cic.comnet.distran.agents.transcoder.RandomTimeTranscoder(C);" +
					"v1:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v2:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v3:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v4:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v5:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v6:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v7:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v8:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v9:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v10:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v11:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" +
					"v12:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v13:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v14:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v15:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v16:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v17:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v18:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v19:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v20:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v21:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v22:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" +
					"v23:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" +					
					"v24:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer"};
		
		jade.Boot.main(rodarDistran);
	}	
*/	

	public static void main(String[] args) {
		String[] rodarDistran = new String[] {
				"-gui", 
				" -agents " + 
					"b1:br.unb.cic.comnet.distran.agents.broker.SequentialBroker(T);" + 
					"t1:br.unb.cic.comnet.distran.agents.transcoder.RandomTimeTranscoder(A);" + 
					"t2:br.unb.cic.comnet.distran.agents.transcoder.RandomTimeTranscoder(B);" + 
					"t3:br.unb.cic.comnet.distran.agents.transcoder.RandomTimeTranscoder(C);" + 
					"v1:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v2:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v3:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v4:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v5:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v6:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v7:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v8:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v9:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v10:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v11:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v12:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer"};
		
		jade.Boot.main(rodarDistran);
	}

}
