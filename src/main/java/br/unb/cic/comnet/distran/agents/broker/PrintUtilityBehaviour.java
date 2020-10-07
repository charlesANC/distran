package br.unb.cic.comnet.distran.agents.broker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import br.unb.cic.comnet.distran.agents.GeneralParameters;
import br.unb.cic.comnet.distran.agents.trm.FactoryRating;
import br.unb.cic.comnet.distran.player.Segment;
import jade.core.Agent;
import jade.util.Logger;

public class PrintUtilityBehaviour extends BrokerTickerBehaviour {
	private static final long serialVersionUID = 1L;
	
	Logger logger = Logger.getJADELogger(getClass().getName());

	public PrintUtilityBehaviour(Agent a, long period) {
		super(a, period);
	}

	@Override
	protected void onTick() {
		logger.log(Logger.INFO, "Writing out utility...");
		
		printFeedbacks("c:\\temp\\util_" + System.currentTimeMillis() + "_.txt");
		appendTranscodersInfo("c:\\temp\\transcoders_.txt");
	}
	
	private void printFeedbacks(String fileName) {
		
		try (Writer writer = new FileWriter(fileName)) {
			for(String segment : getAgent().getFeedbacks().keySet().stream().sorted().collect(Collectors.toSet())) {
				Double viewersUtility = 
						getAgent().getFeedbacks().get(segment).stream()
							.collect(Collectors.summingDouble(UtilityFeedback::getUtility));
				
				Double maxUtility = 
						getAgent().getFeedbacks().get(segment).stream()
							.collect(Collectors.summingDouble(UtilityFeedback::getMaxUtility));			
				
				Double meanInterval = 
						getAgent().getFeedbacks().get(segment).stream()
							.collect(Collectors.averagingLong(UtilityFeedback::getPlayInterval));
				
				// Por enquanto a utilidade dos transcoders é sempre a soma da utilidade dos viewers
				// Na experiência em ambiente real, vai ter viewer que não vai obter utilidade de 
				// nenhum transcoder, então a utilidade pode ser diferente.
				Double transcodersUtility = GeneralParameters.getAlpha() * viewersUtility;
				
				Double totalUtility = viewersUtility + GeneralParameters.getAlpha() * transcodersUtility;
				
				writer.write(String.format("%d;[%s];%.2f;%.2f;%.2f;%.2f;%.2f\r\n", 
						FactoryRating.calculateIteration(segment), 
						getAgent().getSegment(segment).map(Segment::getSource).orElse(""),			
						meanInterval,
						maxUtility, 
						viewersUtility, 
						transcodersUtility, 
						totalUtility));
			}			
		} catch (IOException e) {
			e.printStackTrace();
			logger.log(Logger.WARNING, "Can not write out feedbacks!");
		}
	}
	
	private void appendTranscodersInfo(String fileName) {
		String info = "" + getAgent().getPlaylist().size() + ";";
		for(TranscoderInfo ti : getAgent().getTranscoders()) {
			info += String.format("[%s];%d;%.4f;%.4f;%.2f;", 
					ti.getAID().getName(),
					ti.getRatings().size(),
					ti.getTrustworthy(), 
					ti.getReliability(), 
					ti.getTotalUtility());
		}
		
		info += LocalDateTime.now().toString();
		
		try (
			 FileWriter fw = new FileWriter(fileName, true);
			 BufferedWriter bw = new BufferedWriter(fw);
			 PrintWriter pw = new PrintWriter(bw)
		){
			pw.println(info);
		} catch (IOException e) {
			e.printStackTrace();
			logger.log(Logger.WARNING, "Can not write out transcoders info!");			
		}
	}
}
