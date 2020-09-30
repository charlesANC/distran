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
	
			seg.receive((500*1024 + 500 * Long.valueOf(new Random().nextInt(1024))));
		}		
	}

	public static void main(String[] args) {
		Player player = new Player(5);
		
		List<Segment> playlist = new LinkedList<Segment>();
		playlist.add(Segment.create("a1", 2000L, "t1"));
		playlist.add(Segment.create("a2", 2000L, "t1"));
		playlist.add(Segment.create("a3", 2000L, "t1"));
		playlist.add(Segment.create("a4", 2000L, "t1"));
		playlist.add(Segment.create("a5", 2000L, "t1"));
		playlist.add(Segment.create("a6", 2000L, "t1"));
		playlist.add(Segment.create("a7", 2000L, "t1"));
		playlist.add(Segment.create("a8", 2000L, "t1"));
		playlist.add(Segment.create("a9", 2000L, "t1"));
		playlist.add(Segment.create("b1", 2000L, "t1"));
		playlist.add(Segment.create("b2", 2000L, "t1"));
		playlist.add(Segment.create("b3", 2000L, "t1"));
		playlist.add(Segment.create("b4", 2000L, "t1"));
		playlist.add(Segment.create("b5", 2000L, "t1"));
		playlist.add(Segment.create("b6", 2000L, "t1"));
		playlist.add(Segment.create("b7", 2000L, "t1"));
		playlist.add(Segment.create("b8", 2000L, "t1"));
		playlist.add(Segment.create("b9", 2000L, "t1"));	
		playlist.add(Segment.create("c1", 2000L, "t1"));
		playlist.add(Segment.create("c2", 2000L, "t1"));
		playlist.add(Segment.create("c3", 2000L, "t1"));
		playlist.add(Segment.create("c4", 2000L, "t1"));
		playlist.add(Segment.create("c5", 2000L, "t1"));
		playlist.add(Segment.create("c6", 2000L, "t1"));
		playlist.add(Segment.create("c7", 2000L, "t1"));
		playlist.add(Segment.create("c8", 2000L, "t1"));
		playlist.add(Segment.create("c9", 2000L, "t1"));	
		playlist.add(Segment.create("d1", 2000L, "t1"));
		playlist.add(Segment.create("d2", 2000L, "t1"));
		playlist.add(Segment.create("d3", 2000L, "t1"));
		playlist.add(Segment.create("d4", 2000L, "t1"));
		playlist.add(Segment.create("d5", 2000L, "t1"));
		playlist.add(Segment.create("d6", 2000L, "t1"));
		playlist.add(Segment.create("d7", 2000L, "t1"));
		playlist.add(Segment.create("d8", 2000L, "t1"));
		playlist.add(Segment.create("d9", 2000L, "t1"));		
		
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
