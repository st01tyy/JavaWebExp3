package dataobject;

import java.sql.Timestamp;

public class Paper {
	private long paperid;
	private long ownerid;
	private String papername;
	private Timestamp publish_time;
	private Timestamp cutoff_time;

	public Timestamp getPublish_time() { return publish_time; }
	public void setPublish_time(Timestamp publish_time) { this.publish_time = publish_time; }

	public Timestamp getCutoff_time() { return cutoff_time; }
	public void setCutoff_time(Timestamp cutoff_time) { this.cutoff_time = cutoff_time; }

	public long getPaperid() { return paperid; }
	public void setPaperid(long paperid) { this.paperid = paperid; }

	public long getOwnerid() { return ownerid; }
	public void setOwnerid(long ownerid) { this.ownerid = ownerid; }

	public String getPapername() { return papername; }
	public void setPapername(String papername) { this.papername = papername; }
}
