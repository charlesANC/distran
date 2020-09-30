package br.unb.cic.comnet.distran.player;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

public class SegmentInfo implements Serializable, Comparable<SegmentInfo> {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String source;
	private boolean empty;
	private long length;
	private long duration;
	
	private LocalDateTime requestingTime;
	private LocalDateTime receivingTime;
	
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	
	public String getId() {
		return id;
	}
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public boolean hasSource() {
		return source != null;
	}

	public boolean isEmpty() {
		return empty;
	}

	public long getLength() {
		return length;
	}

	public long getDuration() {
		return duration;
	}

	public LocalDateTime getRequestingTime() {
		return requestingTime;
	}
	public boolean isRequested() {
		return requestingTime != null && receivingTime == null;
	}

	public LocalDateTime getReceivingTime() {
		return receivingTime;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}
	public boolean isPlaying() {
		return startTime != null;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}	
	public boolean isPlayed() {
		return endTime != null;
	}
	
	public SegmentInfo(String id, long duration) {
		this(id, duration, null);
	}
	
	public SegmentInfo(String id, long duration, String source) {
		this.id = id;
		this.duration = duration;
		this.source = source;
		this.empty = true;
	}
	
	public void signRequested() {
		requestingTime = LocalDateTime.now();
	}
	
	public void receive(Long length) {
		receivingTime = LocalDateTime.now();
		this.length = length;		
		empty = false;
	}
	
	public void signStartPlayback() {
		startTime = LocalDateTime.now();
	}
	
	public void signEndPlayback() {
		endTime = LocalDateTime.now();
	}
	
	public double utility(double betha) {
		return (length + betha * (duration - playbackInterval())) / duration;
	}
	
	public double maxUtility() {
		return length / duration;
	}

	private long playbackInterval() {
		return Duration.between(startTime, endTime).toMillis();
	}

	private long servingInterval() {
		return Duration.between(requestingTime, receivingTime).toMillis();		
	}

	@Override
	public int compareTo(SegmentInfo o) {
		if (o == null) return 1;
		return getId().compareTo(o.getId());
	}
	
	@Override
	public String toString() {
		String ret = "[" + getId() + ", " + getLength();
		
		if (isEmpty()) {
			ret += ", 0";
		} else {
			ret += ", " + servingInterval();
		}
		
		if (isPlayed()) {
			ret += ", " + playbackInterval();
		} else {
			ret += ", 0";			
		}
		
		return ret + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SegmentInfo other = (SegmentInfo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
