package tims.bean;
import java.sql.Timestamp;

public class LogonDataBean{
	
	private int m_num;
	private String m_id;
	private String m_passwd;
	private String m_phone;
	private String m_name;
	private String m_email;
	private String m_genre;
	private String m_profileimage;
	private String m_intro;
	private Timestamp m_inputdate;
	
	
	public int getM_num() {
		return m_num;
	}
	public void setM_num(int m_num) {
		this.m_num = m_num;
	}
	public String getM_id() {
		return m_id;
	}
	public void setM_id(String m_id) {
		this.m_id = m_id;
	}
	public String getM_passwd() {
		return m_passwd;
	}
	public void setM_passwd(String m_passwd) {
		this.m_passwd = m_passwd;
	}
	public String getM_phone() {
		return m_phone;
	}
	public void setM_phone(String m_phone) {
		this.m_phone = m_phone;
	}
	public String getM_name() {
		return m_name;
	}
	public void setM_name(String m_name) {
		this.m_name = m_name;
	}
	public String getM_email() {
		return m_email;
	}
	public void setM_email(String m_email) {
		this.m_email = m_email;
	}
	public String getM_genre() {
		return m_genre;
	}
	public void setM_genre(String m_genre) {
		this.m_genre = m_genre;
	}
	
	public Timestamp getM_inputdate() {
		return m_inputdate;
	}
	public void setM_inputdate(Timestamp m_inputdate) {
		this.m_inputdate = m_inputdate;
	}
	public String getM_profileimage() {
		return m_profileimage;
	}
	public void setM_profileimage(String m_profileimage) {
		this.m_profileimage = m_profileimage;
	}
	public String getM_intro() {
		return m_intro;
	}
	public void setM_intro(String m_intro) {
		this.m_intro = m_intro;
	}

	
}