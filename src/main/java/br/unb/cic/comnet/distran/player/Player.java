package br.unb.cic.comnet.distran.player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class Player {
	
	private Map<String, SegmentInfo> segmentsInfo;
	private Queue<SegmentInfo> buffer;
	private int bufferSize;
	
	private List<RequestingSegmentListener> requestingListeners;
	
	public Set<SegmentInfo> getPlayedSegments() {
		return segmentsInfo.values().stream()
				.filter(SegmentInfo::isPlayed)
				.sorted()
				.collect(Collectors.toSet());
	}
	
	public List<SegmentInfo> getRequestedSegments() {
		return segmentsInfo.values().stream()
					.filter(SegmentInfo::isRequested)
					.sorted()
					.collect(Collectors.toList());
	}
	
	public Optional<SegmentInfo> getNextSegmentToBeRequested() {
		List<SegmentInfo> empties = getRequestedSegments();
		return empties.isEmpty() ? Optional.empty() : Optional.of(empties.get(0));
	}
	
	public Set<SegmentInfo> getNextSegmentsToBePlayed() {
		Set<SegmentInfo> segs = new TreeSet<SegmentInfo>(segmentsInfo.values());
		segs.removeAll(getPlayedSegments());
		return segs;
	}
	
	public void addRequestingListener(RequestingSegmentListener listener) {
		Objects.requireNonNull(listener, "Listener n�o pode ser nulo!");
		
		requestingListeners.add(listener);
	}
	
	public void clearRequestingListeners() {
		requestingListeners.clear();
	}
	
	public Player(int bufferSize) {
		this.segmentsInfo = new ConcurrentHashMap<String, SegmentInfo>();
		this.buffer = new ConcurrentLinkedQueue<SegmentInfo>();
		this.bufferSize = bufferSize;
		this.requestingListeners = new ArrayList<RequestingSegmentListener>();
	}
	
	public void receiveSegment(String id, Long content) {
		if (!segmentsInfo.containsKey(id)) return;
			
		SegmentInfo seg = segmentsInfo.get(id);
		seg.receive(content);
	}
	
	public void playStep() {
		updateBuffer();
		
		if (buffer.isEmpty()) return;
		
		SegmentInfo head = buffer.peek();
		
		if (head.isPlaying() && !head.isEmpty()) {
			head.signEndPlayback();
			buffer.remove(head);
			head = buffer.peek();
		} 
		
		if (head != null && !head.isPlaying()) {
			head.signStartPlayback();			
		}
	}

	private void updateBuffer() {
		Iterator<SegmentInfo> segs = getNextSegmentsToBePlayed().iterator();
		while(segs.hasNext() && buffer.size() < bufferSize) {
			SegmentInfo seg = segs.next();
			if (!buffer.contains(seg)) {
				requestSegment(seg);
				buffer.add(seg);				
			}
		}
	}
	
	private void requestSegment(SegmentInfo seg) {
		seg.signRequested();
		
		for(RequestingSegmentListener listener : requestingListeners) {
			listener.requesting(seg);
		}
	}
	
	public void updateSegmentsSource(List<Segment> playlist) {
		for(Segment seg : playlist) {
			if (segmentsInfo.containsKey(seg.getId())) {
				SegmentInfo oldSeg = segmentsInfo.get(seg.getId());
				if (oldSeg.isEmpty()) {
					oldSeg.setSource(seg.getSource());
				}
			} else {
				SegmentInfo newSeg = new SegmentInfo(seg.getId(), seg.getDuration(), seg.getSource());
				segmentsInfo.put(newSeg.getId(), newSeg);
			}
		}
	}
}
