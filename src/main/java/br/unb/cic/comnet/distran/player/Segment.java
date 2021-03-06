package br.unb.cic.comnet.distran.player;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

public class Segment implements Serializable, Comparable<Segment> {
	private static final long serialVersionUID = 1L;
	
	public static Segment create(String id, Long duration) {
		return create(id, duration, null);
	}
	
	public static Segment create(String id, Long duration, String source) {
		Segment seg = new Segment();
		seg.setId(id);
		seg.setDuration(duration);
		seg.setSource(source);
		seg.setCreationTime(LocalDateTime.now());
		return seg;
	}
	
	private String id;
	private Long duration;
	private Long length;
	private LocalDateTime creationTime;	
	private String source;	
	
	public Segment() {}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	
	public Long getLength() {
		// FIXME: Retirar essa linha abaixo
		//return length;
		return 1024*1024L;
	}
	public void setLength(Long length) {
		this.length = length;
	}	
	
	public LocalDateTime getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
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

	@Override
	public int compareTo(Segment o) {
		if (o == null) return 1;
		return Integer.valueOf(getId()).compareTo(Integer.valueOf(o.getId()));
	}
	
	public String toString() {
		return id + "-" + Optional.ofNullable(source).orElse("");
	}
}
