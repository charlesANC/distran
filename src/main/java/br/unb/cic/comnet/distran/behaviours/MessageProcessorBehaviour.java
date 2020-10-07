package br.unb.cic.comnet.distran.behaviours;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.Logger;

public abstract class MessageProcessorBehaviour extends CyclicBehaviour {
	private static final long serialVersionUID = 1L;
	
	private Logger logger = Logger.getJADELogger(getClass().getName());
	
	private int low;
	private int high;
	private MessageTemplate template;
		
	public MessageProcessorBehaviour(int low, int high, MessageTemplate template) {
		super();
		this.low = low;
		this.high = high;
		this.template = template;
	}

	@Override
	public void action() {
		ACLMessage msg = myAgent.receive(template);
		if (msg != null) {
			long start = System.currentTimeMillis();
			customWait(makeInterval());
			long interval = System.currentTimeMillis() - start;
			logger.log(Logger.INFO, getAgent().getName() + " waited for " + interval + "ms...");
			
			doAction(msg);
		} else {
			block();
		}
	}
	
	public abstract void doAction(ACLMessage msg);
	
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
	
	public void customWait(int interval) {
		long start = System.currentTimeMillis();
		do {
			interval -= System.currentTimeMillis() - start;
			if (interval > 0) {
				getAgent().doWait(interval);				
			}
		} while ((System.currentTimeMillis() - start) < interval);
	}

	private int makeInterval() {
		int diff = high - low;
		if (diff == 0) return low;
		return low + new Random().nextInt(diff);
	}
}
