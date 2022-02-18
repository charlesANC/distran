package br.unb.cic.comnet.distran.agents.broker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import br.unb.cic.comnet.distran.agents.GeneralParameters;
import br.unb.cic.comnet.distran.agents.trm.FactoryRating;
import br.unb.cic.comnet.distran.player.Segment;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.basic.Action;
import jade.core.Agent;
import jade.domain.JADEAgentManagement.JADEManagementOntology;
import jade.domain.JADEAgentManagement.ShutdownPlatform;
import jade.lang.acl.ACLMessage;
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
		
		printUtility(GeneralParameters.mountOutputFileName("util_" + System.currentTimeMillis() + "_.txt"));
		printFeedbacks(GeneralParameters.mountOutputFileName("feedback_" + System.currentTimeMillis() + "_.txt"));
		appendTranscodersInfo(GeneralParameters.mountOutputFileName("transcoders_.txt"));
		
		if (hasCompleted()) {
			shutdown();
		}
	}
	
	private void printFeedbacks(String fileName) {
		DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
		
		try (Writer writer = new FileWriter(fileName)) {
			List<String> segmentsIds = 
					getAgent().getFeedbacks().keySet().stream()
						.sorted((x,y) -> Integer.valueOf(x).compareTo(Integer.valueOf(y)))
						.collect(Collectors.toList());
			
			for(String segmentId : segmentsIds) {
				Segment segment = getAgent().getSegment(segmentId).get();
				
				List<UtilityFeedback> feedbacks = 
						getAgent().getFeedbacks().get(segmentId).stream()
							.sorted((x, y) -> x.getRequestTime().compareTo(y.getRequestTime()))
							.collect(Collectors.toList());
				
				for(UtilityFeedback feedback : feedbacks) {
					writer.write(String.format("%s;%s;%s;%s;%s;%s;%d;%.04f;%.04f\r\n", 
							segmentId, 
							feedback.getEvaluator(),
							feedback.getProvider(),
							formater.format(segment.getCreationTime()),
							formater.format(feedback.getRequestTime()),
							formater.format(feedback.getPlayTime()),
							feedback.getPlayInterval(),
							feedback.getStandardUtility(), 
							feedback.getUtility()
					));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.log(Logger.WARNING, "Can not write out feedbacks!");
		}
	}
	
	private void printUtility(String fileName) {
		
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
		String info = "" + currentSegmentsInterval() + ";";
		for(TranscoderInfo ti : getAgent().getTranscoders()) {
			info += String.format("[%s];%d;%d;%.4f;%.4f;%.2f;", 
					ti.getAID().getLocalName(),
					getAgent().segmentCountBySource(ti.getAID().getName()),
					ti.getNumOfRatings(),
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
	
	private String currentSegmentsInterval() {
		if (getAgent().getPlaylist().isEmpty()) return "";
		return getAgent().getPlaylist().get(0).getId() + "-" + 
				getAgent().getPlaylist().get(getAgent().getPlaylist().size()-1).getId();
	}
	
	private boolean hasCompleted() {
		List<String> segmentIds = getSegmentIds();
		// I suppose the initial number of feedbacks is equal to the final number of feedbacks
		return segmentIds.size() == GeneralParameters.getNumOfSegments() && 
					getAgent().getFeedbacks().get(segmentIds.get(0)).size() == getAgent().getFeedbacks().get(segmentIds.get(segmentIds.size()-1)).size();	
	}
	
	private List<String> getSegmentIds() {
		return getAgent().getFeedbacks().keySet().stream()
					.sorted((x,y) -> Integer.valueOf(x).compareTo(Integer.valueOf(y)))
					.collect(Collectors.toList());
	}
	
	private void shutdown() {
		Codec codec = new SLCodec();    
		Ontology jmo = JADEManagementOntology.getInstance();
		getAgent().getContentManager().registerLanguage(codec);
		getAgent().getContentManager().registerOntology(jmo);
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.addReceiver(getAgent().getAMS());
		msg.setLanguage(codec.getName());
		msg.setOntology(jmo.getName());
		try {
			getAgent().getContentManager().fillContent(msg, new Action(getAgent().getAID(), new ShutdownPlatform()));
			getAgent().send(msg);
		}
		catch (Exception e) {}		
	}	
}
