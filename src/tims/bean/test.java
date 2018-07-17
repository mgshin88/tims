package tims.bean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.rosuda.REngine.Rserve.RserveException;
import org.springframework.web.bind.annotation.RequestMapping;


//public List findTweet() throws IOException, RserveException {

//final String fatternName = "contentList"; // 파일이름
//File[] fileList = null; // 파일객체 리스트
//FileInputStream fin = null; // 파일병합
//String contentLines = null; // 통합 파일
//StringBuffer cBuf = new StringBuffer(); // 줄바꿈을 위한 스트림버퍼 호출
//String content = null; // String으로 변환된 최종파일 ( 자바로 파일 불러올 때)


//자바로 파일 불러오기

// 아이디별 하위폴더 찾기
//for (int i = 0; i < idList.size(); i++) {
//	System.out.println("id====" + idList.get(i));
//
//	File path = new File("C://Users//Administrator//Downloads//TIMS_resources//" + idList.get(i) + "//");
//
//	fileList = path.listFiles(new FilenameFilter() {
//		// 위의 파일이름에 해당하는 파일 찾아 리스트에 담기
//		@Override
//		public boolean accept(File dir, String name) {
//			return name.startsWith(fatternName);
//		}
//	});
//	if (fileList != null) {
//		System.out.println("file====" + fileList);
//
//		// 리스트에 담긴 파일 하나로 합치기
//		for (int j = 0; j < fileList.length; j++) {
////			fin = new FileInputStream(fileList[j]);
//			conn.eval("");
//		}
//		
//		
//		System.out.println("통합파일==="+fin);
//	}
//}

//try {
//	// 파일이 담긴 리스트를 읽음
//	BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
//
//	// 파일의 텍스르를 줄바꿈하여 스트링으로 보내준다
//	while ((contentLines = reader.readLine()) != null) {
//		cBuf.append("\n");
//		cBuf.append(contentLines);
//		content = cBuf.toString();
//	}
//} catch (Exception e) {
//	System.out.println(e);
//} finally {
//	if (fin != null) {
//		fin.close();
//	}
//}


//rjava 파일찾기

//// 아이디별로 폴더내의 content파일 읽어서 합치기
//for (int i = 0; i < idList.size(); i++) {
//	String id = idList.get(i);// 아이디 하나씩 담기
//	
//	File path = new File("C://Users//Administrator//Downloads//TIMS_resources//" + idList.get(i)) + "//content//";
//	
//	// 폴더 검색
////	for (int j = 0; j < dirList.length; j++) {
//		
//		// 폴더인지 파일인지 확인	
//
//
//		System.out.println("id==dir===>>" + (id + "====" + dir));
//		// 아이디에 해당되는 폴더가 있을 경우 쿼리 실행
//		// ***ref 칼럼이 있는경우도 추가되는지 확인해볼 것
//		if (id.equals(dir)) {
//
//			conn.eval("content <- read.csv('C://Users//Administrator//Downloads//TIMS_resources//" + dir
//					+ "//content.csv')");
//			// a 자동증가 숫자 칼럼 넣어서 강제 그룹화
//			conn.eval("ref <-" + a + ";");
//			conn.eval("content <- cbind(content,ref);");
//			content = conn.eval("cntList <-rbind(cntList,content);").asList();
//			a = a + 1;
//			System.out.println("folder has found!!===" + dir);
//		} else {
//			continue;
//		}
////	}
//}






//System.out.println("friendList!!!!====" + frList);

//for (int i = 2; i < ts.length; i += 7) {
//	System.out.println(i);
//	// 전체 트윗 중 전체공개 만 출력
//	if (ts[i].equals('1')) {
//		String content1 = tweet.valueOf(i);
//	}
//}


// 전체공개만 담기
// 벡터카운트
//int contentAmount = contentList.size();
//for (int i = 0; i < contentAmount; i++) {
//	// 컨텐츠 행 출력
//	content = new String[contentAmount][];
//	content[i] = contentList.at(i).asStrings();
//	// 행에서 아이디 출력
//	for (int j = 0; j < content[i].length; j++) {
//		String contentName = content[j][0];
//		// 1번 옵션 컨텐츠와 내친구 리스트 비교
//		if (frList.contains(contentName)) {
//			// true
//			for (int k = 0; k < content[j].length; k++) {
//
//				RList frFeed = new RList();
//
//				feedDto.setM_id(content[j][0]);
//				feedDto.setTweet(content[j][1]);
//				feedDto.setOptNum(Integer.parseInt(content[j][3]));
//				feedDto.setUploadPath(content[j][4]);
//				feedDto.setDate((Timestamp) uploadDate.parse(content[j][5]));
//				feedDto.setTime((Timestamp) uploadTime.parse(content[j][6]));
//
//				frFeed.add(feedDto);
//				System.out.println(frFeed);
//			}
//		} else {
//			// false
//			for (int k = 0; k < content[j].length; k++) {
//				RList nfrFeed = new RList();
//
//				feedDto.setM_id(content[j][0]);
//				feedDto.setTweet(content[j][1]);
//				feedDto.setOptNum(Integer.parseInt(content[j][3]));
//				feedDto.setUploadPath(content[j][4]);
//				feedDto.setDate((Timestamp) uploadDate.parse(content[j][5]));
//				feedDto.setTime((Timestamp) uploadTime.parse(content[j][6]));
//
//				nfrFeed.add(feedDto);
//				System.out.println(nfrFeed);
//			}
//		}
//	}
//}