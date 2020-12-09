package br.unb.cic.comnet.distran.agents.broker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import br.unb.cic.comnet.distran.agents.GeneralParameters;
import br.unb.cic.comnet.distran.player.Segment;
import jade.core.Agent;
import jade.util.Logger;

public class SegmentGenerator extends BrokerTickerBehaviour {
	private static final long serialVersionUID = 1L;
	
	Logger logger = Logger.getJADELogger(getClass().getName());
	
	private boolean hasLimits;
	private long maxSegments;
	
	public SegmentGenerator(Agent a, long period) {
		this(a, period, false, 0);
	}

	public SegmentGenerator(Agent a, long period, boolean hasLimits, long maxSegments) {
		super(a, period);
		this.hasLimits = hasLimits;
		this.maxSegments = maxSegments;
	}

	@Override
	protected void onTick() {
		if (getAgent().hasNoSegments()) {
			logMessage("Starting with 10 segments");
			
			for(int i=0; i < 10; i++) {
				getAgent().addSegment(Segment.create(String.valueOf(i), GeneralParameters.getDuration()));					
			}
		} else {
			if (hasLimits && getAgent().segmentCount() >= maxSegments) {
				return;
			}
			
			String id = "" + getAgent().segmentCount();
			
			logMessage("Adding new seg " + id);			
			getAgent().addSegment(Segment.create(id, GeneralParameters.getDuration()));				
		}
	}
	
	public void logMessage(String msg) {
		try (
			 FileWriter fw = new FileWriter("c:\\temp\\t_" + getAgent().getLocalName() + ".txt", true);
			 BufferedWriter bw = new BufferedWriter(fw);
			 PrintWriter pw = new PrintWriter(bw)				
		){
			logger.log(Logger.INFO, msg);			
			pw.println(msg);
		} catch (IOException e) {
			logger.log(Logger.WARNING, "Could not write in the file!");
			e.printStackTrace();
		}
	}		
}
