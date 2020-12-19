
public class RodarDistran {

	public static void main(String[] args) {
		String[] rodarDistran = new String[] {
				"-gui", 
				" -agents " + 
					"b1:br.unb.cic.comnet.distran.agents.broker.SequentialBroker(T);" + 
					"t1:br.unb.cic.comnet.distran.agents.transcoder.RandomTimeTranscoder(C);" + 
					"t2:br.unb.cic.comnet.distran.agents.transcoder.RandomTimeTranscoder(A);" + 
					"t3:br.unb.cic.comnet.distran.agents.transcoder.RandomTimeTranscoder(B);" + 
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
