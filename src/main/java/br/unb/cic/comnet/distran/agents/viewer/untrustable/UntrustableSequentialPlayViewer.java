package br.unb.cic.comnet.distran.agents.viewer.untrustable;

import java.security.InvalidParameterException;

import br.unb.cic.comnet.distran.agents.GeneralParameters;
import br.unb.cic.comnet.distran.agents.MessageProtocols;
import br.unb.cic.comnet.distran.agents.viewer.BrokerSearchBehaviour;
import br.unb.cic.comnet.distran.agents.viewer.PlayerTickBehaviour;
import br.unb.cic.comnet.distran.agents.viewer.PlaylistProcessorBehaviour;
import br.unb.cic.comnet.distran.agents.viewer.ReceivingSegmentBehaviour;
import br.unb.cic.comnet.distran.agents.viewer.Viewer;
import br.unb.cic.comnet.distran.agents.viewer.ViewerProfile;
import br.unb.cic.comnet.distran.player.Player;
import br.unb.cic.comnet.distran.player.RequestingSegmentListener;
import br.unb.cic.comnet.distran.player.SegmentInfo;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.util.Logger;

public class UntrustableSequentialPlayViewer extends Viewer {

	private static final long serialVersionUID = 1L;
	
	private Logger logger = Logger.getJADELogger(getClass().getName());
	
	private Player player;
	private AID broker;
	
	private String partnerTranscoderID;
	
	public ViewerProfile getProfile() {
		if (getArguments() == null || getArguments().length == 0) {
			return ViewerProfile.A;
		}
		ViewerProfile profile = ViewerProfile.valueOf(getArguments()[0].toString());
		if (profile == null) {
			throw new InvalidParameterException(getArguments()[0] + " is not a valid transcoder profile.");			
		}
		return profile;
	}
	
	public String getPartnerTranscoderId() {
		if (getArguments() == null || getArguments().length < 2) {
			return null;
		}
		return getArguments()[1].toString();
	}
	
	@Override
	public Player getPlayer() {
		return player;
	}	
	
	public UntrustableSequentialPlayViewer() {
		super();
		this.player = new Player(GeneralParameters.getBufferLength());
		this.player.addRequestingListener(new RequestSegment(this));
	}

	@Override
	protected void setup() {
		logger.log(Logger.INFO, "Viewer " + getName() + " is on.");
		
		addBehaviour(new BrokerSearchBehaviour(this, 1000));

		addBehaviour(new PlayerTickBehaviour(this, GeneralParameters.getDuration()));
		
		ViewerProfile profile = getProfile();
		addBehaviour(new ReceivingSegmentBehaviour(profile.getLowerReceivingTime(), profile.getHigherReceivingTime()));
		
		partnerTranscoderID = getPartnerTranscoderId();
		addBehaviour(new UntrustablePlaylistRequestingBehaviour(this, GeneralParameters.getDuration(), partnerTranscoderID));
		addBehaviour(new PlaylistProcessorBehaviour(profile.getLowerReceivingTime(), profile.getHigherReceivingTime()));		
	}
	
	public boolean hasBroker() {
		return broker != null;
	}

	@Override
	public AID getBroker() {
		return broker;
	}
	
	@Override
	public void setBroker(AID broker) {
		this.broker = broker;
	}	
	
	private class RequestSegment implements RequestingSegmentListener {
		
		private Logger log = Logger.getJADELogger(getClass().getName());
		
		private Viewer viewer;
		
		public RequestSegment(Viewer viewer) {
			this.viewer = viewer;
		}

		@Override
		public void requesting(SegmentInfo segInfo) {
			log.log(Logger.INFO, "Requesting segment " + 
					segInfo.getId() + " to " + segInfo.getSource() + "...");
			
			ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
			msg.addReceiver(new AID(segInfo.getSource(), true));
			msg.setProtocol(MessageProtocols.send_segment.toString());
			msg.setContent(segInfo.getId());
			viewer.send(msg);
		}
	}
}
