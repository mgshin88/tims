package tims.bean;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.mybatis.spring.SqlSessionTemplate;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping("/member/*")
public class MemberBean {
	RConnection conn = null;

	@Autowired
	private SqlSessionTemplate sqlMap;

	private RConnection connect() {
		try {
			conn = new RConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	private void close() {
		if (conn != null) {
			conn.close();
		}
	}
	

	@RequestMapping("test.com")
	public String test() {
		return "/member/test";
	}

	// �쉶�썝媛��엯 �럹�씠吏�
	@RequestMapping("inputForm.com")
	public String inputForm(LogonDataBean dto,HttpServletRequest request) {
		String id =request.getParameter("m_id");
		int check = (int) sqlMap.selectOne("member.confirmId", id);
		request.setAttribute("check", check);
		return "/member/inputForm";
	}

	// 회원가입하는 페이지
	@RequestMapping("inputPro.com")
	public String inputPro(LogonDataBean dto, HttpServletRequest request, String m_email1, String m_email2,
			String m_phone1, String m_phone2, String m_phone3) throws RserveException {
		
		String id = request.getParameter("m_id");
		String[] checkbox = request.getParameterValues("m_genre");
		for (int i = 0; i < checkbox.length; i++) {
			checkbox[i] = checkbox[i] + ",";
		}
		String email = m_email1 + "@" + m_email2;
		String phone = m_phone1 + m_phone2 + m_phone3;
		dto.setM_email(email);
		dto.setM_phone(phone);
		sqlMap.insert("member.insertMember", dto);
//      가입시 계정별 폴더 만들기
	      File path = new File("C://Users//Administrator//Downloads//TIMS_resources//" + id);
	      File path2 = new File(path + "//tweet//");
	      path.mkdir();
	      path2.mkdir();
	      
	      String path3 = request.getRealPath("//IMG//user");
	      File path4 = new File(path3 + "//" + id); // IMG/user/mkshin13
	      File path5 = new File(path4 + "//profile_img//");
	      path4.mkdir();
	      path5.mkdir();
	      
	      
	      connect(); 
	      conn.eval("Friends<-'"+ id + "'");
	      conn.eval("Friends<-data.frame(Friends=Friends,stringsAsFactors=F)");
	      conn.eval("write.csv(Friends,'C://Users//Administrator//Downloads//TIMS_resources//"+ id +"//myFriends.csv',row.names=F,append=T)");
	      close();
	      
	      
	      
		return "/member/inputPro";
	}
	
	// 濡쒓렇�씤�븯�뒗 �럹�씠吏�
	@RequestMapping("loginPro.com")
	public String loginPro(LogonDataBean dto, HttpSession session, HttpServletRequest request) throws RserveException {
		int c = (int) sqlMap.selectOne("member.userCheck", dto);
		if (c == 1) {
			session = request.getSession();
			session.setAttribute("memId", dto.getM_id());
			int m_num = sqlMap.selectOne("member.selectM_num", dto.getM_id());
			
			session.setAttribute("memNum", m_num);
		}
		
		return "/member/loginPro";

	}

	// �븘�씠�뵒 以묐났寃��궗
	@RequestMapping("confirmId.com")
	public String confirmId(String m_id, HttpServletRequest request) {
		int check = (int) sqlMap.selectOne("member.confirmId", m_id);
		request.setAttribute("check", check);
		return "/member/confirmId";

	}

	// �쉶�썝�젙蹂� �닔�젙�럹�씠吏�
	@RequestMapping("modify.com")
	public String modify(LogonDataBean dto, HttpSession session, HttpServletRequest request) {
		session = request.getSession();
		String m_id = (String) session.getAttribute("memId");
		dto = (LogonDataBean) sqlMap.selectOne("member.memberId", m_id);
		request.setAttribute("dto", dto);
		List<String> usercheck = sqlMap.selectList("member.memsearch");
		request.setAttribute("usercheck", usercheck);
		return "/member/modifyForm";
	}

	@RequestMapping("modifyPro.com") // �쉶�썝�젙蹂� �닔�젙
	public String modifyPro(LogonDataBean dto,HttpServletRequest request,HttpSession session) {
		session = request.getSession();
		String m_id = (String) session.getAttribute("memId");
		dto.setM_id(m_id);
		System.out.println("???:" + m_id);
		
		sqlMap.update("member.memberUpdate", dto);
		request.setAttribute("m_id", m_id);
		return "/member/modifyPro";
	}
	@RequestMapping("pwmodifyPro.com")//鍮꾨�踰덊샇 �닔�젙
	public String pwmodify(HttpServletRequest request,HttpSession session,LogonDataBean dto) {
		session = request.getSession();
		String m_id=(String)session.getAttribute("memId");
		dto.setM_id(m_id);
		dto.setM_passwd(request.getParameter("m_passwd"));
		
		sqlMap.update("member.pwUpdate",dto);
		return "/member/pwmodi";
	}

	@RequestMapping("imgmodify.com") // �쉶�썝�봽�궗 蹂�寃�
	public String imgmodify(MultipartHttpServletRequest request) {
		LogonDataBean dto = new LogonDataBean();
		HttpSession session = request.getSession();
		session = request.getSession();
		String m_id = (String) session.getAttribute("memId");
		Calendar calendar = Calendar.getInstance();
		java.util.Date date = calendar.getTime();
		String today = (new SimpleDateFormat("yyyy.MM.dd.HH.mm").format(date));
		MultipartFile mf = request.getFile("m_profileimage");
		String path = request.getRealPath("/IMG/user/" + m_id + "/profile_IMG");
		String m_profileimage = mf.getOriginalFilename();
		File uploadFile = new File(path + "//" + today + "_" + m_id + "_" + m_profileimage);
		System.out.println(m_profileimage + "," + path);
		try {
			mf.transferTo(uploadFile);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		dto.setM_profileimage(today + "_" + m_id + "_" + m_profileimage);
		dto.setM_id(m_id);
		sqlMap.update("member.imageUpdate", dto);
		return "/member/imagemodify";
	}

	@RequestMapping("normalmodi.com") // �쁽�옱 �봽濡쒗븘 �궗吏� �궘�젣(湲곕낯 �씠誘몄� �궫�엯)
	public String normalmodi(LogonDataBean dto,HttpSession session, HttpServletRequest request,String normalimg) {
		session = request.getSession();
		String m_id = (String) session.getAttribute("memId");
		dto.setM_id(m_id);
		dto.setM_profileimage(normalimg);
		sqlMap.update("member.imageUpdate",dto);
		return "/member/normalmodi";
	}
	
	@RequestMapping("delmem.com") //�쉶�썝 �깉�눜
	public String delMem(LogonDataBean dto,HttpSession session, HttpServletRequest request) {
		session = request.getSession();
		String m_id = (String) session.getAttribute("memId");
		dto.setM_id(m_id);
		dto.setM_passwd(request.getParameter("m_passwd"));
		sqlMap.delete("member.delMem",dto);
		session.invalidate();
		
		return "/member/delMem";
	}
}
