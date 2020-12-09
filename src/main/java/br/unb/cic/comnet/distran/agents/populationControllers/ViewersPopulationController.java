package br.unb.cic.comnet.distran.agents.populationControllers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.unb.cic.comnet.distran.agents.GeneralParameters;
import br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;
import br.unb.cic.comnet.distran.agents.viewer.ViewerProfile;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.util.Logger;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class ViewersPopulationController extends Agent {
	private static final long serialVersionUID = 1L;
	
	private static final int MINIMUM_NUMBER_OF_AGENTS = 10;
	
	private Logger logger = Logger.getJADELogger(getClass().getName());
	
	private Integer turn;
	private DiscreteDistribution distribution;
	private List<AgentController> viewers;
	private Integer viewersCounter;
	
	protected DiscreteDistribution getDistribution() {
		return distribution;
	}
	
	protected void setDistribution(DiscreteDistribution distribution) {
		this.distribution = distribution;
	}
	
	public ViewersPopulationController() {
		super();		
		this.turn = 0;
		this.viewers = new ArrayList<AgentController>();
		this.viewersCounter = 0;
		
		//setDistribution(new PiramidalDistribution(2, 120, 240));
		//setDistribution(new StepDistribution(120, 30, 90));
		setDistribution(new ParetosDistribution(120, 1, 0.7, 0.1));
	}
	
	@Override
	public void setup() {
		addBehaviour(new TickerBehaviour(this, GeneralParameters.getDuration() * 2) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onTick() {
				try {
					int numberOfAgents = getDistribution().position(turn) + MINIMUM_NUMBER_OF_AGENTS;
					
					//ramdomlyKillSome(numberOfAgents);
					
					logger.log(Logger.INFO, "New number: " + numberOfAgents);
					
					if (numberOfAgents > viewers.size()) {
						for(int i = viewers.size(); i < numberOfAgents; i++) {
							createViewer();
						}
					} else {
						for(int i = viewers.size(); i > numberOfAgents; i--) {
							killViewer();
						}
					}
				} catch (StaleProxyException e) {
					logger.log(Logger.WARNING, "Could not communicate with container.", e);
				} finally {
					incrementTurn();					
				}
			}

			private void ramdomlyKillSome(int numberOfAgents) throws StaleProxyException {
				if (numberOfAgents > 0 && viewers.size() > 0) {
					int killSome = new Random().nextInt(Math.min(numberOfAgents, viewers.size()));
					while (killSome-- > 0) {
						killViewer();
					}
				}
			}
		});
		
		addBehaviour(new TickerBehaviour(this, 1000) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onTick() {
				appendPopulationInfo("c:\\temp\\population_.txt");
			}
		});
	}
	
	private void incrementTurn() {
		turn++;
	}
	
	private void createViewer() throws StaleProxyException {
		AgentController viewer = 
				getContainerController()
					.createNewAgent(
							"v" + viewersCounter++, 
							SequentialPlayViewer.class.getName(), 
							new String[] {drawAProfile()});
		viewer.start();
		logger.log(Logger.INFO, "Viewer" + viewer.getName() + " has been created.");
		viewers.add(viewer);
	}
	
	private void killViewer() throws StaleProxyException {
		int index = drawAViewer();
		AgentController viewer = viewers.get(index);
		viewer.kill();
		viewers.remove(index);
		logger.log(Logger.INFO, "Viewer" + viewer.getName() + " has been deleted.");		
	}
	
	private int drawAViewer() {
		return new Random().nextInt(viewers.size());
	}
	
	private String drawAProfile() {
		int roll = new Random().nextInt(1000);
		if (roll < 333) {
			return ViewerProfile.A.name();
		}
		if (roll < 666) {
			return ViewerProfile.B.name();
		}
		return ViewerProfile.C.name();
	}
	
	private void appendPopulationInfo(String fileName) {
		
		String info = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").format(LocalDateTime.now()) + ";" + viewers.size();
		
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
