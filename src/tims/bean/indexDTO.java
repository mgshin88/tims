package tims.bean;

import java.sql.Timestamp;

public class indexDTO {

	private String m_id;
	private String tweet;
	private int optNum;
	private String uploadPath;
	private Timestamp date;
	private Timestamp time;
	
	
	public String getM_id() {
		return m_id;
	}
	public void setM_id(String m_id) {
		this.m_id = m_id;
	}
	public String getTweet() {
		return tweet;
	}
	public void setTweet(String tweet) {
		this.tweet = tweet;
	}
	public int getOptNum() {
		return optNum;
	}
	public void setOptNum(int optNum) {
		this.optNum = optNum;
	}
	public String getUploadPath() {
		return uploadPath;
	}
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}

	
}
