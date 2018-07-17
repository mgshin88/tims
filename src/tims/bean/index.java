package tims.bean;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class index {

	RConnection conn = null;

	// 캡슐화
	private static index instance = new index();

	public static index getInstance() {
		return instance;
	}

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

	@RequestMapping("/content.com")
	public RList loadTweet() throws IOException, RserveException, REXPMismatchException {

		RList content = null; // 전체공개 트윗 리스트
		List<String> idList = sqlMap.selectList("index.idCalls"); // id sql 호출 리스트
		File[] fileList = null; //
		final String fatternName = "content";
		File dirPath = new File("C://Users//Administrator//Downloads//TIMS_resources//"); // 아이디별 폴더 조회
		File[] dirList = dirPath.listFiles();// 위의 경로의 하위폴더 담기
		new StringBuffer();
		ArrayList<File> subdirList = new ArrayList<File>();
		int a = 1;// 파일별 그룹번호

		connect();

		conn.eval("cntList <- NULL;");

		// 아이디별 하위폴더 찾기
		for (int i = 0; i < idList.size(); i++) { // m, z

			// 아이디 하나씩 뽑기
			String id = idList.get(i);
			// 아이디별 폴더 조회
			File path = new File("C://Users//Administrator//Downloads//TIMS_resources//" + idList.get(i) + "//");

			// 해당 아이디에 해당 폴더가 실제 한지 검사
			for (int j = 0; j < dirList.length; j++) {

				// 아이디를 하나씩 출력한다
				File dir = dirList[j];
				String dirName = dir.getName();
				int dirsep = dirName.lastIndexOf("/");
				dirName = dirName.substring(dirsep + 1);

				// 폴더에 해당되는 아이디가 있는 경우만 검색
				if (dirName.equals(id)) {

					// 재귀호출 메서드 하위폴더 검색
					subdirList.clear();
					findSubFiles(path, subdirList);

					// 호출되어 출력된 값이 폴더인지 파일인지 체크
					for (File file : subdirList) {

						// 파일일경우
						if (file.isDirectory()) {
							
							try {
								// 폴더의 경우
								fileList = file.listFiles(new FilenameFilter() {
									// 하위폴더의 content.csv로 시작되는 파일 찾는 기능 상속
									@Override
									public boolean accept(File dir, String name) {
										return name.startsWith(fatternName);
									}
								});

								if (fileList != null) {

									// 리스트에 담긴 파일 하나로 합치기
									for (int k = 0; k < fileList.length; k++) {

										String filePath = fileList[k].getCanonicalPath();
										filePath = filePath.replaceAll("\\\\", "//");
										conn.eval("content <- data.frame()");
										conn.eval("content <- read.csv('" + filePath + "')");
										// a 자동증가 숫자 칼럼 넣어서 강제 그룹화
										conn.eval("ref <-" + a + ";");
										conn.eval("content <- cbind(content,ref);");
										content = conn.eval("cntList <-rbind(cntList,content);").asList();
										a = a + 1;
									}
									conn.eval("write.csv(cntList,'C://Users//Administrator//Downloads//TIMS_resources//admin//tweet//content.csv',row.names=FALSE);");
								}
							} catch (Exception e) {

							}
						}
					}
				}
			}
		}
		close();
		System.out.println(
				"=======================================loading complete=======================================");
		return content;
	}

	// 폴더찾기 재귀호출
	private void findSubFiles(File parentFile, ArrayList<File> subFiles) {

		// 하위파일이 디렉토리일 경우 원주소에 디렉토리를 추가하여 하위폴더로 계속 검색
		if (parentFile.isDirectory()) {
			// 하위폴더명을 주소에 추가
			subFiles.add(parentFile);
			// 하위폴더의 주소를 배열로 담는다
			File[] childFiles = parentFile.listFiles();
			// 배열의 주소를 하나씩 출력
			for (File childFile : childFiles) {
				// 출력된 주소별로 다시 재귀호출
				findSubFiles(childFile, subFiles);
			}
		} else if (parentFile.isFile()) {
			subFiles.add(parentFile);
		}
	}

	@RequestMapping("/main.com")
	public String main(indexDTO feedDto, LogonDataBean dto, HttpSession session, HttpServletRequest request)
			throws Exception {

		loadTweet();
		session = request.getSession();
		String id = (String) session.getAttribute("memId"); // id 세션으로 받기
		feedDto = new indexDTO(); // feed 저장 후 view로 넘길 dto 불러오기
		request.setAttribute("memId", id); // id 세션으로 넘기기
		String[] cntFinal = null; // 아래 리스트에 담을 일차원 배열 하나씩 출력
		String[] rplFinal = null; // 아래 리스트에 담을 일차원 배열 하나씩 출력
		String[] like = null; // 아래 리스트에 담을 일차원 배열 하나씩 출력
		List<String[]> cnt = new ArrayList<String[]>(); // 데이터 정렬 후 뷰로 넘겨줄 리스트
		List<String[]> rpl = new ArrayList<String[]>(); // 데이터 정렬 후 뷰로 넘겨줄 리스트
		List<String[]> lkl = new ArrayList<String[]>(); // 데이터 정렬 후 뷰로 넘겨줄 리스트
		File path = new File("C://Users//Administrator//Downloads//TIMS_resources//" + id + "//myFriends.csv");//친구목록 경로
		Boolean myFrd = path.exists(); // 친구 목록이 존재하는지 유효성 검사

		// name 찾기
		request.setAttribute("dto", dto);// xml에서 작동하는 해당 쿼리에 id 대입하여 불러오기
		dto = (LogonDataBean) sqlMap.selectOne("member.memberId", id);// 불러온 결과값에서 이름 찾기
		String m_name = dto.getM_name();
		String m_profile = dto.getM_profileimage();
		request.setAttribute("m_name", m_name);
		request.setAttribute("m_profile", m_profile);
		

		
		List<String> usercheck = sqlMap.selectList("member.memsearch");
		request.setAttribute("usercheck",usercheck);
		request.setAttribute("memNum", dto.getM_num());
		connect();

		
		// 전체 피드 파일 불러오기
		conn.eval("contentF <- read.csv('C://Users//Administrator//Downloads//TIMS_resources//admin//tweet//content.csv',stringsAsFactors = F)");

		//위의 친구목록.csv 존재 여부 확인 하여 진행
		if (myFrd==true) {
			// 나의 친구목록 파일경로
			
			conn.eval("frList <- read.csv('C://Users//Administrator//Downloads//TIMS_resources//" + id + "//myFriends.csv')");
			String[] frList = conn.eval("frList <- unlist(frList)").asStrings();

			// 내가 쓴글(비공개(3)) 혹은 내가 댓글을 단 글의 ref
			conn.eval("cntMine <- subset(contentF$ref,contentF$id=='" + id + "',);");

			// 내 친구와 관련된 트윗 리스트
			conn.eval("cntFrList <- NULL");

			for (int i = 0; i < frList.length; i++) {
				// 친구 아이디 하나씩 뽑기
				String frName = frList[i];

				if (!frList[0].equals(id)) {

					// 위의 전체 리스트 길이 구하기
					int a = conn.eval("length(contentF[,1])").asInteger();

					// 내 친구의 친구목록
					conn.eval("frOfFrList <- read.csv('C://Users//Administrator//Downloads//TIMS_resources//" + frName
							+ "//myFriends.csv',stringsAsFactors = F)");
					String[] frOfFrList = conn.eval("frOfFrList <- unlist(frOfFrList)").asStrings();

					// 내 친구 목록만큼 다시 반복
					for (int j = 0; j < frList.length; j++) {

						// 위에 i번째에서 나온 친구의 친구목록 만큼 반복
						for (int n = 0; n < frOfFrList.length; n++) {

							// 내 친구의 리스트와 친구의 친구리스트에 같은 값이 없을경우
							if (!frList[j].equals(frOfFrList[n])) {

								// 아이디 대입하여 전체 리스트에서 친구 아이디 들어가 있는 라인만 뽑기
								for (int k = 0; k <= a; k++) {

									// 내친구가 아닌사람의 친구공개만 제외
									conn.eval("cntFrOfFr <-subset(contentF$ref,(contentF$postOption==2&contentF$id!='"
											+ frOfFrList[n] + "'));");
									// 내친구가 아닌사람의 전체공개중 내가 자주보거나 좋아하거나 스크랩한 글들중 연관성 있는 글들의 REF
									// conn.eval("cntFrOfFr
									// <-subset(contentF$ref,(contentF$postOption==2&contentF$id=='" + frOfFrList[n]
									// +"'));");
								}
							} /*
								 * else { // 비공개를 제외한 곳에서 나의 친구 관련 트윗 찾기
								 * conn.eval("cntFr <- subset(contentF$ref,(contentF$id=='" + frName +
								 * "'&contentF$postOption!=3));");
								 * conn.eval("cntFrList <- rbind(cntFrList,cntFr)"); }
								 */
						}
					}
					String[] ccc = conn.eval("ccc <- rbind(cntMine,cntFrOfFr)").asStrings();

					conn.eval("cntFinal <- data.frame()");
					for (int j = 0; j < ccc.length; j++) {

						conn.eval("content <- subset(contentF,contentF$ref==" + ccc[j] + ")");
						conn.eval("cntFinal <- rbind(cntFinal,content)");
					}
				}
			}
			conn.eval("cntFinal <- cntFinal[order(cntFinal$ref,cntFinal$cDate,cntFinal$cTime,decreasing=T),]");

			// 본문
			conn.eval("cntt <- subset(cntFinal,cntFinal$level==1)");

			conn.eval("likeAll<-data.frame()");

			int c = conn.eval("length(cntt[,1])").asInteger();
			
			for (int i = 1; i <= c; i++) {
				System.out.println("리스트 합치기 =============="+i);
				conn.eval("likeRoot <- cntt["+i+",]");
				conn.eval("likeTime <- gsub(':','.',likeRoot$time)");
				conn.eval("realRoot<-paste0('C://Users//Administrator//Downloads//TIMS_resources//',likeRoot$id,'//tweet//',likeRoot$date,'//',likeTime,'//like.csv')");
				conn.eval("likeRead<-read.csv(realRoot,stringsAsFactors = F)");
				conn.eval("root<- likeRoot$ref;");
				conn.eval("likeRead<-cbind(likeRead,root)");
				conn.eval("likeAll <-rbind(likeAll,likeRead)");

			}
			
			int b = conn.eval("length(likeAll[,1])").asInteger();
			
			
			for (int i = 1; i <= b; i++) {
				like = conn.eval("as.character(likeAll["+i+",])").asStrings();
				lkl.add(like);
				System.out.println("리스트 보내기==================");
			}
			request.setAttribute("lkl", lkl);
			
			
			// 댓글
			conn.eval("rpll <- subset(cntFinal,cntFinal$level==2)");

			int a = conn.eval("length(cntFinal[,1])").asInteger();
			for (int i = 1; i <= a; i++) {
				cntFinal = conn.eval("level1 <- as.character(cntt[" + i + ",])").asStrings();
				rplFinal = conn.eval("level2 <- as.character(rpll[" + i + ",])").asStrings();
				cnt.add(cntFinal);
				rpl.add(rplFinal);
			}
			conn.eval("aa<-cntFinal[1,]");
			
			String [] pic = conn.eval("aa <- as.character(aa)").asStrings();
			List<LogonDataBean> abb = new ArrayList<LogonDataBean>();
//			
			for (int i = 0; i < pic.length; i++) {
				String m_id = pic[i];
				LogonDataBean aaaa = sqlMap.selectOne("index.imgCalls", m_id);
				abb.add(aaaa);

			}
			
			request.setAttribute("abb", abb);
			request.setAttribute("cntFr", cnt);
			request.setAttribute("rplFr", rpl);

		}else if(myFrd==false) {
			conn.eval("cntFinal <- subset(contentF,contentF$id=='" + id + "');");
			conn.eval("cntFinal <- cntFinal[order(cntFinal$ref,cntFinal$cDate,cntFinal$cTime,decreasing=T),]");
			// 내 관련 트윗 길이 구하기
			int a = conn.eval("length(cntFinal[,1])").asInteger();
			
			if (a==0) {
				cnt = null;
				rpl = null;
				
				request.setAttribute("cntFr", cnt);
				request.setAttribute("rplFr", rpl);
			}else {
				// 본문
				conn.eval("cntt <- subset(cntFinal,cntFinal$level==1)");
				// 댓글
				conn.eval("rpll <- subset(cntFinal,cntFinal$level==2)");
				conn.eval("likeAll<-data.frame()");

				int c = conn.eval("length(cntt[,1])").asInteger();
				
				for (int i = 1; i <= c; i++) {
					System.out.println("리스트 합치기 =============="+i);
					conn.eval("likeRoot <- cntt["+i+",]");
					conn.eval("likeTime <- gsub(':','.',likeRoot$time)");
					conn.eval("realRoot<-paste0('C://Users//Administrator//Downloads//TIMS_resources//',likeRoot$id,'//tweet//',likeRoot$date,'//',likeTime,'//like.csv')");
					conn.eval("likeRead<-read.csv(realRoot,stringsAsFactors = F)");
					conn.eval("root<- likeRoot$ref;");
					conn.eval("likeRead<-cbind(likeRead,root)");
					conn.eval("likeAll <-rbind(likeAll,likeRead)");

				}
				
				int b = conn.eval("length(likeAll[,1])").asInteger();
				
				for (int i = 1; i <= b; i++) {
					like = conn.eval("as.character(likeAll["+i+",])").asStrings();
					lkl.add(like);
					System.out.println("리스트 보내기==================");
				}
				request.setAttribute("lkl", lkl);
				
				
				for (int i = 1; i <= a; i++) {
					cntFinal = conn.eval("level1 <- as.character(cntt[" + i + ",])").asStrings();
					
					rplFinal = conn.eval("level2 <- as.character(rpll[" + i + ",])").asStrings();
					cnt.add(cntFinal);
					rpl.add(rplFinal);
				}
				request.setAttribute("cntFr", cnt);
				request.setAttribute("rplFr", rpl);
			}
		}
		close();


		return "Index";
	}

	@RequestMapping("mainLogin.com")
	public String mainLogin() {
		return "/member/mainLogin";
	}

	@RequestMapping(value = "/feed.com", method = RequestMethod.POST)
	public String feed(HttpSession session, MultipartHttpServletRequest request, String authorId)
			throws Exception, IOException {

		session = request.getSession();
		String id = (String) session.getAttribute("memId"); // id 세션
		session.setAttribute("id", id);
		String sep = request.getParameter("postSeperator"); // 포스팅,댓글 구분자
		int sepNum = Integer.parseInt(sep); // 본문(1), 댓글(2)
		int level = 0; // if문으로 본문, 댓글 등의 파라미터를 받아 level의 값을 바꿔서 넣어주는 형태로 바꿈
		Date Date = new Date();// 저장되는 날짜 형식
		SimpleDateFormat uploadDate = new SimpleDateFormat("yy-MM-dd"); // 폴더&데이터
		SimpleDateFormat uploadTime = new SimpleDateFormat("HH:mm:ss"); // 데이터
		SimpleDateFormat dirUpload = new SimpleDateFormat("HH.mm.ss"); // 폴더 형식
		String date = uploadDate.format(Date);
		String time = uploadTime.format(Date);
		String dirTime = dirUpload.format(Date);
		String fileName = null;
		String[] likeList = null;
		connect();

		// 본문
		if (sepNum == 1) {
			level = 1;
			String tweet = request.getParameter("tweet_cell"); // 본문
			String postOption = request.getParameter("select"); // 공개범위
			MultipartFile upload = request.getFile("feedUpload"); // 업로드파일

			@SuppressWarnings("deprecation")
			String path = request.getRealPath("//IMG//user");
			System.out.println(path);

			File copy = null;
			File path2 = new File(path + "//" + id);
			File path3 = new File(path2 + "//twt_IMG//");

			// 업로드 중에 사진이 있는지 여부 확인하여 업로드 진행

			if (upload.getOriginalFilename() != "") {

				if (!path2.exists()) {
					path2.mkdir();
					path3.mkdir();
					copy = new File(path3 + "//" + upload.getOriginalFilename());

				} else {
					copy = new File(path3 + "//" + upload.getOriginalFilename());

				}
				upload.transferTo(copy);
				fileName = upload.getOriginalFilename();
			} else {
				fileName = null;
			}

			conn.eval("contentWrite <- data.frame();");
			conn.eval("id <- '" + id + "';");
			conn.eval("level <- '" + level + "';");
			conn.eval("tweet <- '" + tweet + "'");
			conn.eval("postOption <- " + postOption + ";");
			conn.eval("attachment <- '" + fileName + "';");
			conn.eval("like <- 0;");
			conn.eval("date <- '" + date + "';");
			conn.eval("time <- '" + time + "';");
			conn.eval("cDate <- '" + date + "';");
			conn.eval("cTime <- '" + time + "';");
			// 폴더 중복 확인 후 폴더 만들기
			File realPath = new File(
					"C://Users//Administrator//Downloads//TIMS_resources//" + id + "//tweet//" + date + "//" + dirTime);

			if (!realPath.exists()) {
				realPath.mkdirs();
			}

			// 피드 데이터 하나로 합치기
			conn.eval("contentWrite <- cbind(id,level,postOption,tweet,attachment,like,date,time,cDate,cTime)");
			// 해당 아이디 폴더 내에 포스팅 날짜, 시간에 맞는 폴더 생성 후 파일 저장하기
			conn.eval("write.csv(contentWrite,'C://Users//Administrator//Downloads//TIMS_resources//" + id + "//tweet//"
					+ date + "//" + dirTime + "//content.csv',row.names=FALSE)");
			conn.eval("like<-data.frame(num=c(0))");
			// like id list 파일 만들기
			conn.eval("write.csv(like,'C://Users//Administrator//Downloads//TIMS_resources//" + id + "//tweet//" + date
					+ "//" + dirTime + "//like.csv',row.names=FALSE)");
			System.out.println("=======================================포스팅완료=======================================");

			// 댓글
		} else if (sepNum == 2) {
			level = 2;

			String reply = request.getParameter("reply_cell"); // 댓글
			String author = request.getParameter("authorId");// 댓글달때 본문의 아이디
			String aDate = request.getParameter("authorDate");// 댓글달때 본문의 생성날짜
			String aTime = request.getParameter("authorTime");// 댓글달때 본문의 생성시간

			aTime = aTime.replaceAll(":", ".");

			// 본문 글의 아이디 및 날짜 참조하여 폴더 열어야 함
			conn.eval("contentWrite <- read.csv('C://Users//Administrator//Downloads//TIMS_resources//" + author
					+ "//tweet//" + aDate + "//" + aTime + "//content.csv',stringsAsFactors = F);");
			conn.eval("content_temp <- data.frame();");
			conn.eval("id <- '" + id + "';");
			conn.eval("level <- '" + level + "';");
			conn.eval("tweet <- '" + reply + "';");
			conn.eval("postOption <- 0;");
			conn.eval("attachment <- 0;");
			conn.eval("like <- NA;");
			conn.eval("date <- '" + date + "';");
			conn.eval("cDate <- '" + date + "';");
			conn.eval("time <- '" + time + "';");
			conn.eval("cTime <- '" + time + "';");
			conn.eval("content_temp <- cbind(id,level,postOption,tweet,attachment,like,date,time,cDate,cTime);");
			conn.eval("contentWrite <- rbind(contentWrite,content_temp);");
			// 최근 업데이트 된 시간 컬럼 전체 새로 업데이트 하기
			conn.eval("contentWrite$cDate <- '" + date + "';");
			conn.eval("contentWrite$cTime <- '" + time + "';");
			// 본문 폴더로 바꿔야 함
			conn.eval("write.csv(contentWrite,'C://Users//Administrator//Downloads//TIMS_resources//" + author
					+ "//tweet//" + aDate + "//" + aTime + "//content.csv',row.names=FALSE,append=F);");
			System.out.println("=======================================댓글완료=======================================");
		} else if (sepNum == 3) {

			String likeNum = request.getParameter("likeNum"); // 좋아요!
			System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa====>"+likeNum);
			int likeSep = Integer.parseInt(likeNum);
			int a = 0;
			
			String author = request.getParameter("authorId");// 댓글달때 본문의 아이디
			String aDate = request.getParameter("authorDate");// 댓글달때 본문의 생성날짜
			String aTime = request.getParameter("authorTime");// 댓글달때 본문의 생성시간
			System.out.println("댓글 구분=========================================" + likeSep);
			aTime = aTime.replaceAll(":", ".");
			aDate = aDate.replaceAll("2018", "18");

			// like 파일 불러오기
			conn.eval("like <- read.csv('C://Users//Administrator//Downloads//TIMS_resources//" + author + "//tweet//"
					+ aDate + "//" + aTime + "//like.csv',stringsAsFactors = F);");

			if (likeSep == 1) {
						System.out.println("넘버1");

						// like 눌렀을 때 내 아이디 담기
						conn.eval("likeTemp <- '" + id + "';");
						// like 파일에 rbind
						conn.eval("like <- rbind(like,likeTemp);");
						// NA값 지우기
						conn.eval("like <- subset(like,like[,1]!=0)");
						// like 파일 길이 구하기
						a = conn.eval("length(like[,1])").asInteger();
						// like 파일 저장하기
						conn.eval("write.csv(like,'C://Users//Administrator//Downloads//TIMS_resources//" + author
								+ "//tweet//" + aDate + "//" + aTime + "//like.csv', row.names=FALSE, append=F);");
						System.out.println("likeCount============================================" + a);
						// 컨텐츠 파일 불러오기
						conn.eval("contentWrite <- read.csv('C://Users//Administrator//Downloads//TIMS_resources//"
								+ author + "//tweet//" + aDate + "//" + aTime
								+ "//content.csv',stringsAsFactors = F);");
						// 바뀐 likeList 대입
						conn.eval("contentWrite[1,6] <- " + a + ";");
						// 컨텐츠 저장
						conn.eval("write.csv(contentWrite,'C://Users//Administrator//Downloads//TIMS_resources//"
								+ author + "//tweet//" + aDate + "//" + aTime
								+ "//content.csv',row.names=FALSE);");
						likeList = conn.eval("like <- unlist(like)").asStrings();
						request.setAttribute("likeList", likeList);
						request.setAttribute("a", a);
			} else if (likeSep == 2) {
					System.out.println("넘버2");

						// likeList 내의 내 아이디 index 구하기
							// like에서 아이디 빼기
							conn.eval("like<-subset(like,like!='" + id + "')");
							
							int c = conn.eval("length(like)").asInteger();
							
							if (c==0) {
								conn.eval("like<-0");
								conn.eval("like<-data.frame(num=c(0))");
								// like 파일 길이 구하기
								a = conn.eval("length(like)").asInteger();

								// 컨텐츠 파일 불러오기
								conn.eval("contentWrite <- read.csv('C://Users//Administrator//Downloads//TIMS_resources//"
										+ author + "//tweet//" + aDate + "//" + aTime
										+ "//content.csv',stringsAsFactors = F);");
								// 바뀐 likeList 대입
								conn.eval("contentWrite[1,6] <- 0;");
								// 컨텐츠 저장
								conn.eval("write.csv(contentWrite,'C://Users//Administrator//Downloads//TIMS_resources//"
										+ author + "//tweet//" + aDate + "//" + aTime
										+ "//content.csv',row.names=FALSE);");
								// like 파일 저장하기
								conn.eval("write.csv(like,'C://Users//Administrator//Downloads//TIMS_resources//" + author
										+ "//tweet//" + aDate + "//" + aTime + "//like.csv',row.names=FALSE);");
								request.setAttribute("likeList", likeList);
								request.setAttribute("a", a);
							}else {
								
								// like 파일 길이 구하기
								a = conn.eval("length(like[,1])").asInteger();
								
								String [] like = conn.eval("as.character(like)").asStrings();
								
								if (like[0]=="0") {
									conn.eval("like<-0");
									conn.eval("like<-data.frame(num=c(0))");
									// 컨텐츠 파일 불러오기
									conn.eval("contentWrite <- read.csv('C://Users//Administrator//Downloads//TIMS_resources//"
											+ author + "//tweet//" + aDate + "//" + aTime
											+ "//content.csv',stringsAsFactors = F);");
									// 바뀐 likeList 대입
									conn.eval("contentWrite[1,6] <- 0;");
									// 컨텐츠 저장
									conn.eval("write.csv(contentWrite,'C://Users//Administrator//Downloads//TIMS_resources//"
											+ author + "//tweet//" + aDate + "//" + aTime
											+ "//content.csv',row.names=FALSE);");
									// like 파일 저장하기
									conn.eval("write.csv(like,'C://Users//Administrator//Downloads//TIMS_resources//" + author
											+ "//tweet//" + aDate + "//" + aTime + "//like.csv',row.names=FALSE);");
									likeList = conn.eval("like <- unlist(like)").asStrings();
									request.setAttribute("likeList", likeList);
									request.setAttribute("a", a);
								}else {
									// 컨텐츠 파일 불러오기
									conn.eval("contentWrite <- read.csv('C://Users//Administrator//Downloads//TIMS_resources//"
											+ author + "//tweet//" + aDate + "//" + aTime
											+ "//content.csv',stringsAsFactors = F);");
									// 바뀐 likeList 대입
									conn.eval("contentWrite[1,6] <- " + a + ";");
									// 컨텐츠 저장
									conn.eval("write.csv(contentWrite,'C://Users//Administrator//Downloads//TIMS_resources//"
											+ author + "//tweet//" + aDate + "//" + aTime
											+ "//content.csv',row.names=FALSE);");
									// like 파일 저장하기
									conn.eval("write.csv(like,'C://Users//Administrator//Downloads//TIMS_resources//" + author
											+ "//tweet//" + aDate + "//" + aTime + "//like.csv',row.names=FALSE);");
									likeList = conn.eval("like <- unlist(like)").asStrings();
									request.setAttribute("likeList", likeList);
									request.setAttribute("a", a);
								}
								
								

							}	
			}
		}
		close();

		return "redirect:/main.com";
	}
	
	@RequestMapping("user.com")
	public String user(LogonDataBean dto,HttpSession session,HttpServletRequest request, String m_id) throws RserveException, IOException, REXPMismatchException {
		/*String m_id = (String)session.getAttribute("memId");*/
		session = request.getSession();
		String memId = (String)session.getAttribute("memId");
		dto = (LogonDataBean)sqlMap.selectOne("member.memberId", m_id);
		String id = request.getParameter("m_id");
		request.setAttribute("dto",dto);
		request.setAttribute("memId", memId);
		List<String> usercheck = sqlMap.selectList("member.memsearch");
		request.setAttribute("usercheck",usercheck);
		String[] ccc = null;
		File folfile = new File("C://Users/Administrator//Downloads//TIMS_resources//" + memId+"//Following.csv");
		File folfile1 = new File("C://Users/Administrator//Downloads//TIMS_resources//" + memId+"//Follower.csv");
		File folfile2 = new File("C://Users/Administrator//Downloads//TIMS_resources//" + m_id+"//Following.csv");
		File folfile3 = new File("C://Users/Administrator//Downloads//TIMS_resources//" + m_id+"//Follower.csv");
		
		boolean foll = folfile.exists();
		boolean foll2 = folfile1.exists();
		boolean foll3 = folfile2.exists();
		boolean foll4 = folfile3.exists();
		
		
			connect();
			/**/
			/*conn.eval("Following<-subset(Following,(Following==c('"+m_id+"')));")*/
			if(foll==false && foll3 == false) {
				int a = 0;
				request.setAttribute("a", a);
			}
			if(foll==true && foll3 ==true) {
				System.out.println("??:" + m_id);
				conn.eval("Following<-read.csv('C:/Users/Administrator/Downloads/TIMS_resources/"+m_id+"/Following.csv',stringsAsFactors=F);");
				conn.eval("Follower<-read.csv('C:/Users/Administrator/Downloads/TIMS_resources/"+m_id+"/Follower.csv',stringsAsFactors=F);");
				conn.eval("Following<-na.omit(Following);");
				conn.eval("Follower<-na.omit(Follower);");
				conn.eval("Friends<-read.csv('C:/Users/Administrator/Downloads/TIMS_resources/"+m_id+"/myFriends.csv',stringsAsFactors=F);");
				conn.eval("Friends<-Friends[!(Friends$Friends=='"+m_id+"')]");
				conn.eval("checkfollow<-read.csv('C:/Users/Administrator/Downloads/TIMS_resources/"+m_id+"/Following.csv',stringsAsFactors=F);");
				conn.eval("checkfollow<-subset(checkfollow,(checkfollow==c('"+m_id+"')));");
				int Followingnum = conn.eval("length(Following$Following)").asInteger();
				int Followernum = conn.eval("length(Follower$Follower)").asInteger();
				int Friendsnum = conn.eval("length(Friends$Friends)").asInteger();
				int folcheck = conn.eval("length(checkfollow)").asInteger();
				request.setAttribute("Followingnum", Followingnum);
				request.setAttribute("Followernum", Followernum);
				request.setAttribute("Friendsnum", Friendsnum);
				request.setAttribute("folcheck", folcheck);
			}
			/*request.setAttribute("check", ab);
			System.out.println("?????:" +  ab);*/
			//파일 데이터 담을 리스트
			List<String[]> cntList = new ArrayList<String[]>();
			//파일 불러오기
			conn.eval("test<-read.csv('C://Users//Administrator//Downloads//TIMS_resources//admin//tweet//content.csv',stringsAsFactors=F)");
			// 내 아이디 글 보기
			conn.eval("mycnt <- subset(test,test$id=='"+memId+"'&test$level==1);");
			//불러온 파일 길이 구하기
			conn.eval("library(plyr)");
			conn.eval("mycnt<-arrange(mycnt,desc(date),desc(time))");
			int a = conn.eval("length(mycnt[,1])").asInteger();
			// 리스트로 담을 배열 선언
			
			//길이만큼 반복
			for (int i = 1; i <= a; i++) {
				ccc = conn.eval("datamy <- as.character(mycnt["+i+",])").asStrings();
				cntList.add(ccc);
			}
			request.setAttribute("cntList", cntList);
			
			
			Calendar calendar = Calendar.getInstance();
			java.util.Date date = calendar.getTime();
			String today = (new SimpleDateFormat("yyyy.MM.dd.HH.mm").format(date));
			
			String followingid = request.getParameter("myid"); //자신의 아이디 
			String followid = request.getParameter("followid"); // 팔로우할 아이디
			String myid2 = request.getParameter("myid2"); //팔로우해제 자신의 아이디
			String unfollow = request.getParameter("unfollow"); // 팔로우해제 상대 아이디
			
			File file = new File("C://Users/Administrator//Downloads//TIMS_resources//" + followingid+"//Following.csv");
			File file1 = new File("C://Users/Administrator//Downloads//TIMS_resources//" + followingid +"//Follower.csv");
			File file2 = new File("C://Users/Administrator//Downloads//TIMS_resources//" + followid +"//Following.csv");
			File file3 = new File("C://Users/Administrator//Downloads//TIMS_resources//" + followid +"//Follower.csv");
			
			boolean test = file.exists();
			boolean test1 = file1.exists();
			boolean test2 = file2.exists();
			boolean test3 = file3.exists();
			
			
			
			if(followingid!=null && (test==false && test3==false ) || (test==true && test3==false)||(test==false && test3==true)) { // 내 폴더에 csv와 상대방 폴더에 csv가 없으면 파일 생성!
				conn.eval("Follower<-NA");
			    conn.eval("Following<-NA");
			    conn.eval("Follower<-data.frame(Follower=Follower,stringsAsFactors=F)");
			    conn.eval("Following<-data.frame(Following=Following,stringsAsFactors=F)");
			    conn.eval("write.csv(Follower,'C://Users//Administrator//Downloads//TIMS_resources//"+ followingid +"//Follower.csv',row.names=F,append=T)");
			    conn.eval("write.csv(Following,'C://Users//Administrator//Downloads//TIMS_resources//"+ followingid +"//Following.csv',row.names=F,append=T)");
				conn.eval("write.csv(Follower,'C://Users//Administrator//Downloads//TIMS_resources//"+ followid +"//Follower.csv',row.names=F,append=T)");
				conn.eval("write.csv(Following,'C://Users//Administrator//Downloads//TIMS_resources//"+ followid +"//Following.csv',row.names=F,append=T)");}
			    /*conn.eval("write.csv(test,'C://Users//Administrator//Downloads//TIMS_resources//"+ followingid +"//myFriends.csv',row.names=F,append=T)");*/
			    if(followingid!=null && followid!=null) {// 상대의 Follower.csv.의 내 아이디와 시간 추가!
				conn.eval("Followingid<-read.csv('C:/Users/Administrator/Downloads/TIMS_resources/"+followid+"/Follower.csv',stringsAsFactors=F);");
			    conn.eval("insert<-c('"+followingid+"','"+today+"');");
				conn.eval("Followingid<-rbind(Followingid,insert);");
				conn.eval("Followingid<-na.omit(Followingid)");
				conn.eval("write.csv(Followingid,'C:/Users/Administrator/Downloads/TIMS_resources/"+followid+"/Follower.csv',row.names=F,append=T)");
				
				// 나의 Following.csv의 상대 아이디와 시간 추가!
				conn.eval("Followerid<-read.csv('C:/Users/Administrator/Downloads/TIMS_resources/"+followingid+"/Following.csv',stringsAsFactors=F);");
				conn.eval("insert2<-c('"+followid+"','"+today+"');");
				conn.eval("Followerid<-rbind(Followerid,insert2);");
				conn.eval("Followerid<-na.omit(Followerid)");
				conn.eval("write.csv(Followerid,'C:/Users/Administrator/Downloads/TIMS_resources/"+followingid+"/Following.csv',row.names=F,append=T)");
			} 
			    if(myid2 != null && unfollow!=null) { 
				// 팔로우 해제시 상대의 Follower.csv에서 내 아이디를 삭제
				conn.eval("Followingid<-read.csv('C:/Users/Administrator/Downloads/TIMS_resources/"+unfollow+"/Follower.csv',stringsAsFactors=F);");
				conn.eval("Followingid<-subset(Followingid,!(Followingid$Follower==c('"+myid2+"')))");
				conn.eval("length1<-length(Followingid$Follower)");
				conn.eval("if(length1==0){"
						+ "Follower<-NA;"
						+ "Followingid<-data.frame(Follower=Follower,stringsAsFactors=F);"
						+ "write.csv(Followingid,'C:/Users/Administrator/Downloads/TIMS_resources/"+unfollow+"/Follower.csv',row.names=F,append=T);"
						+ "}");
				conn.eval("write.csv(Followingid,'C:/Users/Administrator/Downloads/TIMS_resources/"+unfollow+"/Follower.csv',row.names=F,append=T)");
				// 팔로우 해제시 나의 Following.csv에서 상대 아이디를 삭제
				conn.eval("Followerid<-read.csv('C:/Users/Administrator/Downloads/TIMS_resources/"+myid2+"/Following.csv',stringsAsFactors=F);");
				conn.eval("Followerid<-subset(Followerid,!(Followerid$Follwing==c('"+unfollow+"')));");
				conn.eval("length1<-length(Followerid$Following)");
				conn.eval("if(length1==0){"
						+ "Following<-NA; "
						+ "Followerid<-data.frame(Following=Following,stringsAsFactors=F);"
						+ "write.csv(Followerid,'C:/Users/Administrator/Downloads/TIMS_resources/"+myid2+"/Following.csv',row.names=F,append=T);"
						+ "}");
				conn.eval("write.csv(Followerid,'C:/Users/Administrator/Downloads/TIMS_resources/"+myid2+"/Following.csv',row.names=F,append=T)");
			}
			
			/*int aa = conn.eval("length(Follower)").asInteger();
			int bb = conn.eval("length(Following)").asInteger();
			
			conn.eval("a <- cbind(Follower,Following)");
			conn.eval("a<- as.data.frame()");
			for (int i = 0; i <= aa; i++) {
				for (int j = 0; j <= bb; j++) {
					if (i==j) {
						conn.eval("aaa<-subset(a,a$following=="+i+"&a$follower=="+j+")");
					}
				}
				conn.eval("write.csv('C://Users//Administrator//Downloads//TIMS_resources//"+id+"//myFriends.csv',row.names=F)");
			}*/
			close();
		
		return "/user";
	}
	
	@RequestMapping("Followinglist.com")
	public String test(HttpServletRequest request, String m_id) throws RserveException, REXPMismatchException {
		connect();
		conn.eval("Following<-read.csv('C://Users//Administrator//Downloads//TIMS_resources//"+m_id+"//Following.csv',stringsAsFactors=F)");
		conn.eval("Following<-na.omit(Following)");
		String[] test11 = conn.eval("Following<-Following$Following").asStrings();
		ArrayList<LogonDataBean> alist = new ArrayList<LogonDataBean>();
		for(int i=0;i<test11.length;i++) {
			LogonDataBean mdto = sqlMap.selectOne("member.memberId", test11[i]);
			alist.add(mdto);
			System.out.println("ㅅㅂ:" + test11[i]);
		}	
		request.setAttribute("alist", alist);
		close();
		return "/Follower";
	}
	@RequestMapping("Followerlist.com")
	public String test1(HttpServletRequest request, String m_id) throws RserveException, REXPMismatchException {
		connect();
		conn.eval("Follower<-read.csv('C://Users//Administrator//Downloads//TIMS_resources//"+m_id+"//Follower.csv',stringsAsFactors=F)");
		conn.eval("Follower<-na.omit(Follower)");
		String[] test11 = conn.eval("Follower<-Follower$Follower").asStrings();
		ArrayList<LogonDataBean> alist = new ArrayList<LogonDataBean>();
		for(int i=0;i<test11.length;i++) {
			LogonDataBean mdto = sqlMap.selectOne("member.memberId", test11[i]);
			alist.add(mdto);
			System.out.println("ㅅㅂ:" + test11[i]);
		}	
		request.setAttribute("alist", alist);
		close();
		return "/Follower";
	}
	@RequestMapping("Friendslist.com")
	public String test2(HttpServletRequest request, String m_id) throws RserveException, REXPMismatchException {
		connect();
		conn.eval("myFriends<-read.csv('C://Users//Administrator//Downloads//TIMS_resources//"+m_id+"//myFriends.csv',stringsAsFactors=F)");
		conn.eval("myFriends<-na.omit(myFriends)");
		String[] test11 = conn.eval("myFriends<-myFriends$Friends").asStrings();
		ArrayList<LogonDataBean> alist = new ArrayList<LogonDataBean>();
		for(int i=0;i<test11.length;i++) {
			LogonDataBean mdto = sqlMap.selectOne("member.memberId", test11[i]);
			alist.add(mdto);
			System.out.println("ㅅㅂ:" + test11[i]);
		}	
		request.setAttribute("alist", alist);
		close();
		return "/Follower";
	}
	

}
