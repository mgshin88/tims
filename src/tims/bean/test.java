package tims.bean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.rosuda.REngine.Rserve.RserveException;
import org.springframework.web.bind.annotation.RequestMapping;


//public List findTweet() throws IOException, RserveException {

//final String fatternName = "contentList"; // �����̸�
//File[] fileList = null; // ���ϰ�ü ����Ʈ
//FileInputStream fin = null; // ���Ϻ���
//String contentLines = null; // ���� ����
//StringBuffer cBuf = new StringBuffer(); // �ٹٲ��� ���� ��Ʈ������ ȣ��
//String content = null; // String���� ��ȯ�� �������� ( �ڹٷ� ���� �ҷ��� ��)


//�ڹٷ� ���� �ҷ�����

// ���̵� �������� ã��
//for (int i = 0; i < idList.size(); i++) {
//	System.out.println("id====" + idList.get(i));
//
//	File path = new File("C://Users//Administrator//Downloads//TIMS_resources//" + idList.get(i) + "//");
//
//	fileList = path.listFiles(new FilenameFilter() {
//		// ���� �����̸��� �ش��ϴ� ���� ã�� ����Ʈ�� ���
//		@Override
//		public boolean accept(File dir, String name) {
//			return name.startsWith(fatternName);
//		}
//	});
//	if (fileList != null) {
//		System.out.println("file====" + fileList);
//
//		// ����Ʈ�� ��� ���� �ϳ��� ��ġ��
//		for (int j = 0; j < fileList.length; j++) {
////			fin = new FileInputStream(fileList[j]);
//			conn.eval("");
//		}
//		
//		
//		System.out.println("��������==="+fin);
//	}
//}

//try {
//	// ������ ��� ����Ʈ�� ����
//	BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
//
//	// ������ �ؽ����� �ٹٲ��Ͽ� ��Ʈ������ �����ش�
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


//rjava ����ã��

//// ���̵𺰷� �������� content���� �о ��ġ��
//for (int i = 0; i < idList.size(); i++) {
//	String id = idList.get(i);// ���̵� �ϳ��� ���
//	
//	File path = new File("C://Users//Administrator//Downloads//TIMS_resources//" + idList.get(i)) + "//content//";
//	
//	// ���� �˻�
////	for (int j = 0; j < dirList.length; j++) {
//		
//		// �������� �������� Ȯ��	
//
//
//		System.out.println("id==dir===>>" + (id + "====" + dir));
//		// ���̵� �ش�Ǵ� ������ ���� ��� ���� ����
//		// ***ref Į���� �ִ°�쵵 �߰��Ǵ��� Ȯ���غ� ��
//		if (id.equals(dir)) {
//
//			conn.eval("content <- read.csv('C://Users//Administrator//Downloads//TIMS_resources//" + dir
//					+ "//content.csv')");
//			// a �ڵ����� ���� Į�� �־ ���� �׷�ȭ
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
//	// ��ü Ʈ�� �� ��ü���� �� ���
//	if (ts[i].equals('1')) {
//		String content1 = tweet.valueOf(i);
//	}
//}


// ��ü������ ���
// ����ī��Ʈ
//int contentAmount = contentList.size();
//for (int i = 0; i < contentAmount; i++) {
//	// ������ �� ���
//	content = new String[contentAmount][];
//	content[i] = contentList.at(i).asStrings();
//	// �࿡�� ���̵� ���
//	for (int j = 0; j < content[i].length; j++) {
//		String contentName = content[j][0];
//		// 1�� �ɼ� �������� ��ģ�� ����Ʈ ��
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