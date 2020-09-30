package br.unb.cic.comnet.distran.player;

import java.io.Serializable;

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
		return seg;
	}
	
	private String id;
	private Long duration;
	private Long length;
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
		return length;
	}
	public void setLength(Long length) {
		this.length = length;
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
		return getId().compareTo(o.getId());
	}
}
