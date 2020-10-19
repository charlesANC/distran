package br.unb.cic.comnet.distran.agents.populationControllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.unb.cic.comnet.distran.agents.GeneralParameters;
import br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.util.Logger;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class ViewersPopulationController extends Agent {
	private static final long serialVersionUID = 1L;
	
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
		
		setDistribution(new PiramidalDistribution(1, 10));
	}
	
	@Override
	public void setup() {
		addBehaviour(new TickerBehaviour(this, GeneralParameters.getDuration()) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onTick() {
				try {
					int numberOfAgents = getDistribution().position(turn);
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
							null);
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
}
