package br.unb.cic.comnet.distran.agents.broker;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.stream.Collectors;

import br.unb.cic.comnet.distran.agents.GeneralParameters;
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
	}
	
	private void printFeedbacks(String fileName) {
		
		try (Writer writer = new FileWriter(fileName)) {
			for(String segment : getAgent().getFeedbacks().keySet()) {
				Double viewersUtility = 
						getAgent().getFeedbacks().get(segment).stream()
							.collect(Collectors.summingDouble(UtilityFeedback::getUtility));
				
				// Por enquanto a utilidade dos transcoders é sempre a soma da utilidade dos viewers
				// Na experiência em ambiente real, vai ter viewer que não vai obter utilidade de 
				// nenhum transcoder, então a utilidade pode ser diferente.
				Double transcodersUtility = viewersUtility;
				
				Double totalUtility = viewersUtility + GeneralParameters.getAlpha() * transcodersUtility;
				
				writer.write(String.format("%s;%.2f;%.2f;%.2f\r\n", segment, viewersUtility, transcodersUtility, totalUtility));
			}			
		} catch (IOException e) {
			e.printStackTrace();
			logger.log(Logger.WARNING, "Can not write out feedbacks!");
		}
	}	

}
