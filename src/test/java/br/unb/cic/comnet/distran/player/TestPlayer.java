package br.unb.cic.comnet.distran.player;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class TestPlayer {
	
	private static class PlayTick extends TimerTask {
		
		private Player player;
		
		public PlayTick(Player player) {
			this.player = player;
		}

		@Override
		public void run() {
			System.out.println("Playing...");
			System.out.println();
			
			player.playStep();
			
			double sum_max = 0;
			double sum_actual = 0;
			for(SegmentInfo seg : player.getPlayedSegments()) {
				double max = seg.maxUtility();
				double actual = seg.utility(1000);
				
				sum_max += max;
				sum_actual += actual;
				
				System.out.println(seg + " Utility: " + actual + " Max Utility: " + max);
			}
			System.out.println("---");
			System.out.println("Max Utility: " + sum_max);
			System.out.println("Actual Utility: " + sum_actual);			
			
			if (player.getPlayedSegments().size() == 36) cancel();
		}
	}
	
	private static class ServeSegment extends TimerTask {
		private SegmentInfo seg;
		
		public ServeSegment(SegmentInfo segment) {
			this.seg = segment;
		}

		@Override
		public void run() {
			System.out.println("Serving " + seg.getId() + "... ");
	
			Random rand = new Random();
			seg.receive((500*1024 + 500 * Long.valueOf(rand.nextInt(1024))));
		}		
	}

	public static void main(String[] args) {
		Player player = new Player(3);
		
		List<SegmentInfo> playlist = new LinkedList<SegmentInfo>();
		playlist.add(new SegmentInfo("a1", 2000, "t1"));
		playlist.add(new SegmentInfo("a2", 2000, "t1"));
		playlist.add(new SegmentInfo("a3", 2000, "t1"));
		playlist.add(new SegmentInfo("a4", 2000, "t1"));
		playlist.add(new SegmentInfo("a5", 2000, "t1"));
		playlist.add(new SegmentInfo("a6", 2000, "t1"));
		playlist.add(new SegmentInfo("a7", 2000, "t1"));
		playlist.add(new SegmentInfo("a8", 2000, "t1"));
		playlist.add(new SegmentInfo("a9", 2000, "t1"));
		playlist.add(new SegmentInfo("b1", 2000, "t1"));
		playlist.add(new SegmentInfo("b2", 2000, "t1"));
		playlist.add(new SegmentInfo("b3", 2000, "t1"));
		playlist.add(new SegmentInfo("b4", 2000, "t1"));
		playlist.add(new SegmentInfo("b5", 2000, "t1"));
		playlist.add(new SegmentInfo("b6", 2000, "t1"));
		playlist.add(new SegmentInfo("b7", 2000, "t1"));
		playlist.add(new SegmentInfo("b8", 2000, "t1"));
		playlist.add(new SegmentInfo("b9", 2000, "t1"));	
		playlist.add(new SegmentInfo("c1", 2000, "t1"));
		playlist.add(new SegmentInfo("c2", 2000, "t1"));
		playlist.add(new SegmentInfo("c3", 2000, "t1"));
		playlist.add(new SegmentInfo("c4", 2000, "t1"));
		playlist.add(new SegmentInfo("c5", 2000, "t1"));
		playlist.add(new SegmentInfo("c6", 2000, "t1"));
		playlist.add(new SegmentInfo("c7", 2000, "t1"));
		playlist.add(new SegmentInfo("c8", 2000, "t1"));
		playlist.add(new SegmentInfo("c9", 2000, "t1"));	
		playlist.add(new SegmentInfo("d1", 2000, "t1"));
		playlist.add(new SegmentInfo("d2", 2000, "t1"));
		playlist.add(new SegmentInfo("d3", 2000, "t1"));
		playlist.add(new SegmentInfo("d4", 2000, "t1"));
		playlist.add(new SegmentInfo("d5", 2000, "t1"));
		playlist.add(new SegmentInfo("d6", 2000, "t1"));
		playlist.add(new SegmentInfo("d7", 2000, "t1"));
		playlist.add(new SegmentInfo("d8", 2000, "t1"));
		playlist.add(new SegmentInfo("d9", 2000, "t1"));		
		
		player.updateSegmentsSource(playlist);
		
		player.addRequestingListener((seg) -> {
			System.out.println("Requesting " + seg.getId() + "... ");
			
			Random rand = new Random();
			new Timer().schedule(new ServeSegment(seg), 2500 + rand.nextInt(2500));
		}); 
		
		Timer timer = new Timer();
		
		timer.schedule(new PlayTick(player), 2000, 2000);

	}

}
