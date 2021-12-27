package br.unb.cic.comnet.distran.behaviours;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Timer;
import java.util.TimerTask;

import br.unb.cic.comnet.distran.agents.GeneralParameters;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.Logger;

public abstract class NonBlockingMessageProcessorBehaviour extends CyclicBehaviour {
	private static final long serialVersionUID = 1L;
	
	private Logger logger = Logger.getJADELogger(getClass().getName());
	
	private MessageTemplate template;
		
	public NonBlockingMessageProcessorBehaviour(MessageTemplate template) {
		super();
		this.template = template;
	}

	@Override
	public void action() {
		ACLMessage msg = myAgent.receive(template);
		if (msg != null) {
			long start = System.currentTimeMillis();
			String className = getClass().getSimpleName();
			
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					new Thread(new Runnable() {
						@Override
						public void run() {
							long interval = System.currentTimeMillis() - start;
							logger.log(Logger.INFO, "* NB " + className + " : " + getAgent().getLocalName() + " waited for " + interval + "ms...");
							
							doAction(msg);
						}
					}).start();
				}
			}, getInterval());
			
		} else {
			block();
		}
	}
	
	public abstract void doAction(ACLMessage msg);
	
	public abstract int getInterval();
	
	public void logMessage(String msg) {
		try (
			 FileWriter fw = new FileWriter(GeneralParameters.mountOutputFileName("t_" + getAgent().getLocalName() + ".txt"), true);
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
	
	public void customWait(int interval) {
		long start = System.currentTimeMillis();
		do {
			interval -= System.currentTimeMillis() - start;
			if (interval > 0) {
				getAgent().doWait(interval);				
			}
		} while ((System.currentTimeMillis() - start) < interval);
	}
}
