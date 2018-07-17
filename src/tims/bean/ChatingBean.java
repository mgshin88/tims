package tims.bean;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mybatis.spring.SqlSessionTemplate;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.RList;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping("/chatView/*")
public class ChatingBean {
	
	RConnection conn = null;
	
	@Autowired
	private SqlSessionTemplate sqlMap; // MyBatis..
	
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
	
	
	@RequestMapping("/friendsList.com")
	public String friendsList() {
		return "/chatView/friendsList";
	}
	
	@RequestMapping("/friendsChatRoomList.com")
	public String friendsChatRoomAllList() {
		return "/chatView/friendsChatRoomList";
	}
	
	@RequestMapping("/chatRoomAllList.com")
	public String chatRoomAllList(HttpSession session, HttpServletRequest request) {
		String m_id = (String)session.getAttribute("memId");
		int m_num = (int)session.getAttribute("memNum");
		
		System.out.println("세션값 : " + session.getAttribute("roomMemId"));
		if(session.getAttribute("roomMemId")!=null) {
			int sessionUser = 0;
			String roomMemId = (String)session.getAttribute("roomMemId");
			String[] splitroomMem = roomMemId.split("｀");
			int oc_num = Integer.parseInt(splitroomMem[0]);
			
			ChatingDTO crdto = sqlMap.selectOne("chating.openRoomMemSelect", oc_num);
			
			String oc_number = String.valueOf(oc_num);
			
			LogonDataBean mdto = sqlMap.selectOne("chating.selectMemOne", m_num);
			
			String[] o_m_num = crdto.getM_num().split(",");
			int oc_now = o_m_num.length-1;
			
			request.setAttribute("crdto", crdto);
			request.setAttribute("m_id", m_id); 
			request.setAttribute("oc_num", oc_num);
			request.setAttribute("o_m_num", o_m_num);
			request.setAttribute("oc_number", oc_number);
			request.setAttribute("crdto", crdto);
			request.setAttribute("oc_now", oc_now);
			request.setAttribute("mdto", mdto);
			request.setAttribute("sessionUser", sessionUser);
			
			return "/chatView/chatRoomAll";
		}
		List<ChatingDTO> cdto = new ArrayList<ChatingDTO>();
		cdto = sqlMap.selectList("chating.chatRoomAllList");
		List<Integer> loc_now = new ArrayList<Integer>();
		for(int i=0;i<cdto.size();i++) {
			int count = 0;
			String[] oc_count = cdto.get(i).getM_num().split(",");
			
			count = oc_count.length-1;
			loc_now.add(count);
		}
		
		request.setAttribute("cdto", cdto);
		request.setAttribute("loc_now", loc_now);
		return "/chatView/chatRoomAllList";
	}
	
	@RequestMapping("/chatRoomAllCreate.com")
	public String chatRoomAllCreate(HttpSession session, HttpServletRequest request) {
		List g_option = new ArrayList();
		g_option = sqlMap.selectList("chating.chatRoomGenre");
		
		request.setAttribute("g_option", g_option);
		return "/chatView/chatRoomAllCreate";
	}
	
	@RequestMapping("/chatRoomAllCreatePro.com")
	public String chatRoomAllCreatePro(MultipartHttpServletRequest mhs_request) throws RserveException {
		ChatingDTO cdto = new ChatingDTO();
		cdto.setM_num(mhs_request.getParameter("m_num"));
		cdto.setOc_name(mhs_request.getParameter("oc_name"));
		cdto.setOc_content(mhs_request.getParameter("oc_content"));
		cdto.setG_num(Integer.parseInt(mhs_request.getParameter("g_num")));
		cdto.setOc_max(Integer.parseInt(mhs_request.getParameter("oc_max")));
		
		MultipartFile mf = mhs_request.getFile("oc_thumbnail");
		String path = mhs_request.getRealPath("//IMG//chatRoomAll");
		String oc_thumbnail = mf.getOriginalFilename();
		String file_name = System.currentTimeMillis() + "_" + oc_thumbnail;
		File uploadFile = new File(path + "//" + file_name);
		System.out.println(path);
		try {
			mf.transferTo(uploadFile); // 파일을 직접적으로 생성
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		cdto.setOc_thumbnail(file_name);
		sqlMap.insert("chating.chatRoomInsert", cdto);
		String roomNum = sqlMap.selectOne("chating.selectRoomNum");
		ChatingDTO crdto = sqlMap.selectOne("chating.openRoomMemSelect", Integer.parseInt(roomNum) );
		
		Date date = new Date();
		SimpleDateFormat sdformat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		String today = sdformat.format(date);
		String s_oc_num = crdto.getOc_num()+"";
		
		File path2 = new File(path + "//room_" + s_oc_num );
		File path3 = new File(path2 + "//img//");
		File path4 = new File(path2 + "//video//");
		path2.mkdir();
		path3.mkdir();
		path4.mkdir();
		
		String realPath = path.replace("\\","/");
		System.out.println(realPath);
		
		String unreal_path = realPath + "/room_"+s_oc_num+"/room_"+s_oc_num;
		
		connect();
		conn.eval("m_id <- '" + cdto.getM_num() + "'");
		conn.eval("msg <- '방장'");
		conn.eval("img <- NA");
		conn.eval("video <- NA");
		conn.eval("roomNum <- '" + s_oc_num + "'");
		conn.eval("sendTime <- '" + today + "'");
		conn.eval("resultSend <- cbind(m_id, msg, img, video, roomNum, sendTime)");
		
		conn.eval("write.csv(resultSend, '"+ unreal_path + ".csv',row.names=F )");
		
		conn.eval("subject <- 'NA'");
		conn.eval("resultSender <- cbind(m_id, subject, sendTime)");
		conn.eval("write.csv(resultSender, '"+ unreal_path + "_notice.csv', row.names=F)");
		
		mhs_request.setAttribute("crdto", crdto);
		close();
		
		return "/chatView/chatRoomAllCreatePro";
	}
	
	@RequestMapping("/chatRoomAll.com")
	public String chatRoomAll(HttpSession session, HttpServletRequest request, ChatingDTO cdto) {
		String m_id = (String)session.getAttribute("memId");
		int m_num = (int)session.getAttribute("memNum");
		int sessionUser = 1;
		
		int oc_num = cdto.getOc_num();
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("m_num", m_num);
		map.put("oc_num", oc_num);
		
		int c_count = sqlMap.selectOne("chating.chatRoomAllCount", oc_num);
		if(c_count >= 1) {
			sqlMap.update("chating.joinchatRoom", map);
		} else {
			return "/chatView/chatRoomAllError";
		}
		/* oc_num 이용해서 다시 셀렉해서 m_num 추출 */
		ChatingDTO crdto = sqlMap.selectOne("chating.openRoomMemSelect", oc_num); // 1,2
		
		String oc_number = String.valueOf(oc_num);
		
		LogonDataBean mdto = sqlMap.selectOne("chating.selectMemOne", m_num);
		
		String[] o_m_num = crdto.getM_num().split(",");
		int oc_now = o_m_num.length-1;
		
		request.setAttribute("m_id", m_id); 
		request.setAttribute("oc_num", oc_num);
		request.setAttribute("o_m_num", o_m_num);
		request.setAttribute("oc_number", oc_number);
		request.setAttribute("crdto", crdto); //
		request.setAttribute("oc_now", oc_now);
		request.setAttribute("mdto", mdto);
		request.setAttribute("sessionUser", sessionUser);
		session.setAttribute("roomMemId", oc_num+"｀"+m_id+"｀"+mdto.getM_profileimage()); // 방 번호랑 사용자 아이디, 프로필 사진을 담은 요청
		
		return "/chatView/chatRoomAll";
	}
	
	@RequestMapping("/chatRoomExit.com")
	public String chatRoomExit(HttpSession session, HttpServletRequest request, int oc_num, String url) {
		session.removeAttribute("roomMemId");
		int m_num = (int) session.getAttribute("memNum");
		
		/* 방에서 나갔을 때 필요한 정보를 Map에 담음 */
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("m_num", m_num); // 현재 세션에 담긴 유저 번호
		map.put("oc_num", oc_num); // 방 번호
		
		sqlMap.update("chating.exitchatRoom", map);
		
		/* 방에 인원이 몇명인지 확인 하기 위해서! */
		ChatingDTO crdto = sqlMap.selectOne("chating.openRoomMemSelect", oc_num);
		String[] o_m_num = crdto.getM_num().split(",");

		if(o_m_num.length-1 <= 0) { // 지금 현재 방 인원이 0명 일 시 (앞에 방장 표시를 제외 해야 하기 때문에 -1을 함)
			sqlMap.delete("chating.chatRoomDelete", oc_num);
		} 
		/* 현재 방이 존재하고 만약 나간 사람이 방장이라면 */
		else {
			if( Integer.parseInt(o_m_num[0]) == m_num) {
				Map<String, Integer> map2 = new HashMap<String, Integer>(); // 방 정보를 담기 위한 map2 선언
				map2.put("beforeOwner", m_num); // 방장 자신이 나간 거기 때문에 session에 담긴 값을 map2에 저장 (방을 나간 사람이 이 메소드를 실행하기 때문에)
				map2.put("afterOwner", Integer.parseInt(o_m_num[1])); // 그 다음번째에 있는 사람의 값을 map2에 저장
				map2.put("oc_num", oc_num); // 방 번호 map2에 저장
				sqlMap.update("chating.RoomOwnerReplace", map2); // 방장 변경 sql 쿼리문
			}
		}
		request.setAttribute("url", url);
		
		return "/chatView/chatRoomExit";
	}
	
	@RequestMapping("/chatAllSend.com")
	public void chatAllSend(HttpSession session, HttpServletRequest request, String sendMsg) throws RserveException {
		String[] requestMsg = sendMsg.split("｀");
		Date date = new Date();
		SimpleDateFormat sdformat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		String today = sdformat.format(date);
		String path = request.getRealPath("//IMG//chatRoomAll");
		
		String realPath = path.replace("\\", "/");
		
		String FileName = realPath + "/room_"+requestMsg[0]+"/room_"+requestMsg[0]+".csv";
		
		System.out.println(FileName);
		
		connect();
		conn.eval("mainList <- read.csv('"+FileName+"')");
		conn.eval("m_id <- '" +  requestMsg[1] + "'");
		conn.eval("msg <- '"  + requestMsg[3] + "'");
		conn.eval("roomNum <- '" + requestMsg[0] + "'");
		conn.eval("img <- NA");
		conn.eval("video <- NA");
		conn.eval("sendTime <- '" + today + "'");
		conn.eval("abc <- cbind(m_id, msg, img, video, roomNum, sendTime)");
		conn.eval("mainList <- rbind(mainList,abc)");
		conn.eval("write.csv(mainList, '" + FileName + "',row.names=F)");
	
		close();
	}
	
	@RequestMapping(value="/chatAllFileSend.com", method=RequestMethod.POST, produces="application/text; charset=utf8")
	public @ResponseBody String chatAllFileSend(HttpSession session, MultipartHttpServletRequest mhs_request, int count) throws UnsupportedEncodingException, RserveException {
		System.out.println("카운트 : " + count);
		Date date = new Date();
		SimpleDateFormat sdformat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		String today = sdformat.format(date);
		String today2 = sdformat.format(date);
		
		today = today.replaceAll(":", "-");
		
		String m_id = (String)session.getAttribute("memId");
		String oc_num = mhs_request.getParameter("oc_num");
		
		if(count == 1) {
			MultipartFile mf = mhs_request.getFile("imageUpload");
			String path = mhs_request.getRealPath("//IMG//chatRoomAll//room_"+oc_num+"//img");
		
			System.out.println("기본 파일 이름 : " + mf.getOriginalFilename());
		
			String imageUpload = m_id + "_" + today + "_" + mf.getOriginalFilename();
			File uploadFile = new File(path + "//" + imageUpload);
		
			System.out.println("이미지 이름 : " + imageUpload);
		
			try {
				mf.transferTo(uploadFile); // 파일을 직접적으로 생성
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			String path2 = mhs_request.getRealPath("//IMG//chatRoomAll");
			String realPath = path2.replace("\\", "/");
			String FileName = realPath + "/room_"+oc_num+"/room_"+oc_num+".csv";
			System.out.println(FileName);
			
			connect();
			conn.eval("mainList <- read.csv('"+FileName+"')");
			conn.eval("m_id <- '" +  m_id + "'");
			conn.eval("msg <- NA");
			conn.eval("roomNum <- '" + oc_num + "'");
			conn.eval("img <- '" + imageUpload + "'");
			conn.eval("video <- NA");
			conn.eval("sendTime <- '" + today2 + "'");
			conn.eval("abc <- cbind(m_id, msg, img, video, roomNum, sendTime)");
			conn.eval("mainList <- rbind(mainList,abc)");
			conn.eval("write.csv(mainList, '" + FileName + "',row.names=F)");
		
			
			close();
	
		
			return imageUpload;
		}
		
		else if(count == 2) {
			MultipartFile mf = mhs_request.getFile("videoUpload");
			String path = mhs_request.getRealPath("//IMG//chatRoomAll//room_"+oc_num+"//video");
		
			System.out.println("기본 파일 이름 : " + mf.getOriginalFilename());
		
			String videoUpload = m_id + "_" + today + "_" + mf.getOriginalFilename();
			File uploadFile = new File(path + "//" + videoUpload);
		
			System.out.println("동영상 이름 : " + videoUpload);
		
			try {
				mf.transferTo(uploadFile); // 파일을 직접적으로 생성
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			String path2 = mhs_request.getRealPath("//IMG//chatRoomAll");
			String realPath = path2.replace("\\", "/");
			
			String FileName = realPath + "/room_"+oc_num+"/room_"+oc_num+".csv";
			System.out.println(FileName);
			connect();
			conn.eval("mainList <- read.csv('"+FileName+"')");
			conn.eval("m_id <- '" +  m_id + "'");
			conn.eval("msg <- NA");
			conn.eval("roomNum <- '" + oc_num + "'");
			conn.eval("img <- NA ");
			conn.eval("video <- '" + videoUpload + "'");
			conn.eval("sendTime <- '" + today2 + "'");
			conn.eval("abc <- cbind(m_id, msg, img, video, roomNum, sendTime)");
			conn.eval("mainList <- rbind(mainList,abc)");
			conn.eval("write.csv(mainList, '" + FileName + "',row.names=F)");
		
			
			close();
	
		
			return videoUpload;
		}
		
		return null;
	}
	
	@RequestMapping("/chatRoomAllNoticeSend.com")
	public String chatRoomAllNoticeSend(HttpSession session, HttpServletRequest request, String noticeContent, int oc_num) throws RserveException, REXPMismatchException {
		Date date = new Date();
		SimpleDateFormat sdformat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		String today = sdformat.format(date);
		
		String m_id = (String) session.getAttribute("memId");

		String path = request.getRealPath("//IMG//chatRoomAll");
		String realPath = path.replace("\\", "/");
		
		String FileName = realPath + "/room_"+oc_num+"/room_"+oc_num+"_notice.csv";
		System.out.println(FileName);
		connect();
		
		conn.eval("mainList <- read.csv('"+FileName+"')");
		conn.eval("m_id <- '" +  m_id + "'");
		conn.eval("subject <- '" + noticeContent + "'");
		conn.eval("sendTime <- '" + today + "'");
		conn.eval("abc <- cbind(m_id, subject, sendTime)");
		conn.eval("mainList <- rbind(mainList,abc)");
		conn.eval("write.csv(mainList, '" + FileName + "',row.names=F)");
		
		/* Notice 메소드에 있는 내용 다시 실행해서 request.setAttribute 해줌 */
		conn.eval("noticeList <- read.csv('"+FileName+"', stringsAsFactors = F)");
		conn.eval("noticeList <- na.omit(noticeList)");
		conn.eval("noticeList_order <- noticeList[order(noticeList$sendTime, decreasing = T),] "); // sendTime 기준으로 내림차순
		
		
		String[] idList = conn.eval("idList <- noticeList_order$m_id").asStrings();
		String[] subjectList = conn.eval("subjectList <- noticeList_order$subject").asStrings();
		String[] sendTimeList = conn.eval("sendTimeList <- noticeList_order$sendTime").asStrings();
		
		close();
		
		request.setAttribute("idListResult", idList);
		request.setAttribute("subjectListResult", subjectList);
		request.setAttribute("sendTimeListResult", sendTimeList);
		request.setAttribute("oc_num", oc_num);
		
		return "/chatView/chatRoomAllNotice";
	}
	
	@RequestMapping("/chatRoomAllTalkPartner.com")
	public String chatRoomAllTalkPartner(HttpSession session, HttpServletRequest request, int oc_num) {
		ChatingDTO crdto = sqlMap.selectOne("chating.openRoomMemSelect", oc_num);
		String[] o_m_num = crdto.getM_num().split(","); // 22,22,21
		
		List<LogonDataBean> chatRoom_MIdList = new ArrayList<LogonDataBean>();
		for(int i=1;i<o_m_num.length;i++) {
			LogonDataBean mdto = sqlMap.selectOne("chating.chatRoomAllTalkPeopleList", o_m_num[i]);
			chatRoom_MIdList.add(mdto);
		}

		request.setAttribute("chatRoom_MIdList", chatRoom_MIdList);
		return "/chatView/chatRoomAllTalkPartner";
	}
	
	@RequestMapping("/chatRoomAllNotice.com")
	public String chatRoomAllNotice(HttpServletRequest request, int oc_num) throws RserveException, REXPMismatchException {
		String path = request.getRealPath("//IMG//chatRoomAll");
		String realPath = path.replace("\\", "/");
		
		String FileName = realPath + "/room_"+oc_num+"/room_"+oc_num+"_notice.csv";
		
		connect();
		
		conn.eval("mainList <- read.csv('"+FileName+"', stringsAsFactors = F)");
		conn.eval("noticeList <- cbind(mainList$m_id, mainList$subject, mainList$sendTime)");
		conn.eval("noticeList <- na.omit(noticeList)");
		conn.eval("noticeListDataFrame <- data.frame(noticeList)");
		String[] idList = conn.eval("idList <- noticeListDataFrame$X1").asStrings();
		String[] subjectList = conn.eval("subjectList <- noticeListDataFrame$X2").asStrings();
		String[] sendTimeList = conn.eval("sendTimeList <- sort(noticeListDataFrame$X3, decreasing = TRUE)").asStrings();
		
		close();
		
		request.setAttribute("idListResult", idList);
		request.setAttribute("subjectListResult", subjectList);
		request.setAttribute("sendTimeListResult", sendTimeList);
		request.setAttribute("oc_num", oc_num);
		
		return "/chatView/chatRoomAllNotice";
	}
	
	@RequestMapping("/chatRoomAllPicture.com")
	public String chatRoomAllPicture(HttpServletRequest request, int oc_num) throws RserveException, REXPMismatchException {
		String path = request.getRealPath("//IMG//chatRoomAll");
		String realPath = path.replace("\\", "/");
		
		String FileName = realPath + "/room_"+oc_num+"/room_"+oc_num+".csv";
		
		connect();
		 
		conn.eval("mainList <- read.csv('"+FileName+"', stringsAsFactors = F)");
		conn.eval("imgList <- cbind(mainList$m_id, mainList$img)");
		conn.eval("imgList <-na.omit(imgList)");
		conn.eval("imgListDataFrame <- data.frame(imgList)");
		String[] idList = conn.eval("idList <- imgListDataFrame$X1 ").asStrings();
		// conn.eval("idList<-unlist(idList)");
		String[] imageList = conn.eval("imageList <- imgListDataFrame$X2 ").asStrings();
		
		close();
		
		request.setAttribute("idListResult", idList);
		request.setAttribute("imageListResult", imageList);
		request.setAttribute("oc_num", oc_num);
		return "/chatView/chatRoomAllPicture";
	}
	
	@RequestMapping("/chatRoomAllVideo.com")
	public String chatRoomAllVideo(HttpServletRequest request, int oc_num) throws RserveException, REXPMismatchException {
		String path = request.getRealPath("//IMG//chatRoomAll");
		String realPath = path.replace("\\", "/");
		
		String FileName = realPath + "/room_"+oc_num+"/room_"+oc_num+".csv";
		
		connect();
		 
		conn.eval("mainList <- read.csv('"+FileName+"', stringsAsFactors = F)");
		conn.eval("videoList <- cbind(mainList$m_id, mainList$video)");
		conn.eval("videoList <-na.omit(videoList)");
		conn.eval("videoListDataFrame <- data.frame(videoList)");
		String[] idList = conn.eval("idList <- videoListDataFrame$X1 ").asStrings();
		// conn.eval("idList<-unlist(idList)");
		String[] videoList = conn.eval("videoListResult <- videoListDataFrame$X2 ").asStrings();
		
		close();
		
		request.setAttribute("idListResult", idList);
		request.setAttribute("videoListResult", videoList);
		request.setAttribute("oc_num", oc_num);
		
		return "/chatView/chatRoomAllVideo";
	}
	
}
