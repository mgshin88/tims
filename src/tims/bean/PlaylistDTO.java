package tims.bean;

import java.sql.Timestamp;

public class PlaylistDTO {

	private String pl_videoId;

	private int oc_num;

	private String pl_title;

	private int m_num;

	private Timestamp pl_uploadTime;
	
	private int pl_duration;
	
	public int getPl_duration() {
		return pl_duration;
	}

	public void setPl_duration(int pl_duration) {
		this.pl_duration = pl_duration;
	}

	public String getPl_videoId() {

		return pl_videoId;
	}

	public void setPl_videoId(String pl_videoId) {

		this.pl_videoId = pl_videoId;
	}

	public int getOc_num() {

		return oc_num;
	}

	public void setOc_num(int oc_num) {

		this.oc_num = oc_num;
	}

	public String getPl_title() {

		return pl_title;
	}

	public void setPl_title(String pl_title) {

		this.pl_title = pl_title;
	}

	public int getM_num() {

		return m_num;
	}

	public void setM_num(int m_num) {

		this.m_num = m_num;
	}

	public Timestamp getPl_uploadTime() {

		return pl_uploadTime;
	}

	public void setPl_uploadTime(Timestamp pl_uploadTime) {

		this.pl_uploadTime = pl_uploadTime;
	}
}