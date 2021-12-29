package br.unb.cic.comnet.distran.agents.broker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.math3.distribution.NormalDistribution;

import br.unb.cic.comnet.distran.agents.GeneralParameters;
import jade.core.AID;
import jade.core.Agent;
import jade.util.Logger;

public class ReNoSTwoTranscodingAssignment extends AbstractTranscodingAssigment {
	
	public ReNoSTwoTranscodingAssignment(Agent a, long period) {
		super(a, period);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public Optional<AID> drawATranscoder() {
		List<TranscoderInfo> tis = getTranscodersAboveThreshold();
		
		if (tis.isEmpty()) return Optional.empty();
		
		Double bestSample = Double.MIN_VALUE;
		AID bestAID = null;
		
		for(TranscoderInfo ti : tis) {
			if (ti.getTrustworthy() != null && ti.getReliability() != null) {
				Double sample = sample(ti.getTrustworthy(), ti.getReliability());
				
				//append(ti.getAID(), ti.getTrustworthy(), ti.getReliability(), sample);
				
				if (sample >= bestSample) {
					bestSample = sample;
					bestAID = ti.getAID();
				}				
			}
		}
		
		return Optional.ofNullable(bestAID);
	}
	
	private double sample(double trust, double reliability) {
		return sample(createNormal(trust, reliability));
	}
	
	private Double sample(NormalDistribution distribution) {
		return Math.min(distribution.sample(), 1D);
	}
	
	private NormalDistribution createNormal(double trust, double reliability) {
		return new NormalDistribution(
			trust * reliability, 
			desvpad(reliability)
		);
	}
	
	private double desvpad(double reliability) {
		return Math.exp(-2 * reliability);
	}
	
	private List<TranscoderInfo> getTranscodersAboveThreshold() {
		return getAgent().getTranscoders().stream()
				.filter(x -> x.getTrustworthy() >= GeneralParameters.getTrustThreshold())
					.sorted((x, y) -> y.getTrustworthy().compareTo(x.getTrustworthy()))
							.collect(Collectors.toList());
	}
	
	private void append(AID aid, Double trustworthiness, Double reliability, Double sample) {
		try (
				 FileWriter fw = new FileWriter("c:\\temp\\Thesis\\renos2samples.txt", true);
				 BufferedWriter bw = new BufferedWriter(fw);
				 PrintWriter pw = new PrintWriter(bw)
			){
				pw.println(String.format("%s;%.4f;%.4f;%.4f", aid.getLocalName(), trustworthiness, reliability, sample));
			} catch (IOException e) {
				e.printStackTrace();
				logger.log(Logger.WARNING, "Can not write out transcoders info!");			
			}		
	}
}
