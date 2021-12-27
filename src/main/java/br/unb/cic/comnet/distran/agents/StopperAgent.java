package br.unb.cic.comnet.distran.agents;

import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.wrapper.ControllerException;

public class StopperAgent extends Agent {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void setup() {
		addBehaviour(new WakerBehaviour(this, 23 * 60 * 1000) {
			private static final long serialVersionUID = 1L;

			protected void onWake() {
				System.out.println("Terminando tudo...");
				new Thread() {
					public void run() {
						try {
							getContainerController().getPlatformController().kill();
						} catch (ControllerException e) {
							System.out.println("Ooopps!!!...");
						}
					}
				}.start();
			}
		});
	}

}
