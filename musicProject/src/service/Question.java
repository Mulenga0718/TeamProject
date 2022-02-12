package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dao.BoardDao;
import util.ScanUtil;
import util.View;

public class Question {

	private Question() {
	}

	private static Question instance;

	public static Question getInstance() {
		if (instance == null) {
			instance = new Question();
		}
		return instance;
	}

	BoardDao boardDao = BoardDao.getInstance();

	public int questionboard() {
		System.out.println("==================문의 사항====================");

		if (Login.loginMember == null) {
			System.out.println("(1)자주 묻는 질문 (2)뒤로가기 >");
			int result = ScanUtil.nextInt();
			switch (result) {
			case 1:
				return View.FAQBOARD;
			case 2:
				return View.Home;
			}
		} else {
			System.out.println("(1)자주 묻는 질문 (2)1:1 문의 (3)뒤로가기 >");
			int result = ScanUtil.nextInt();
			switch (result) {
			case 1:
				return View.FAQBOARD;
			case 2:
				if (("admin").equals((String) Login.loginMember.get("ID"))) {
					return View.QUESTIONONEADMIN;
				} else {
					return View.QUESTION;
				}
			case 3:
					return View.MAIN;
				}
			}
		return View.QUESTIONBOARD;
	}

	public int question() {

		System.out.println("==================1:1 문의====================");
		System.out.println("(1)문의 (2)문의 결과 확인 (3)뒤로가기 >");
		int result = ScanUtil.nextInt();
		switch (result) {
		case 1:
			return View.QUESTIONONE;
		case 2:
			return View.QUESTIONANSWER;
		case 3:
			if (Login.loginMember == null) {
				return View.Home;
			} else {
				return View.MAIN;
			}
		default:
			System.out.println("다시 입력해주세요.");
			return View.QUESTION;

		}

	}

	public int questionone() {
		System.out.println("============= 문의 내용 ===============\n");
		System.out.print("제목 : ");
		String title = ScanUtil.nextLine();
		System.out.print("내용 : ");
		String content = ScanUtil.nextLine();
		int result = boardDao.addQuestion(title, content);
		if(result>0) {
			System.out.println("등록에 성공하였습니다.");
		}else {
			System.out.println("등록에 실패하였습니다.");
		}
		System.out.print("(1)뒤로가기 (2)홈으로 이동> ");
		result = ScanUtil.nextInt();
		switch(result) {
		case 1: return View.QUESTION;
		case 2:
		if (Login.loginMember == null) {
			return View.Home;
		} else {
			return View.MAIN;
		}
		} return View.QUESTIONONE;
	}

	public int questionanswer() {
		
		Map<String, Object> board = boardDao.selectQuestion((String)Login.loginMember.get("ID"));
		try {
		board.get("Q_NO");
		System.out.println("============= 문의 내용 ===============\n");
		System.out.println("질문 번호: " + board.get("Q_NO"));
		System.out.println("아이디: " + board.get("ID"));
		System.out.println("내용: " + board.get("Q_CONTENT"));
		System.out.println("작성 시간: " + board.get("QUESTIONDATE"));
		
		board = boardDao.selectAnswer((String)Login.loginMember.get("ID"));
		try {
			board.get("Q_ANSWER");

			System.out.println("============= 답변 ===================\n");
			System.out.println("답변: " + board.get("Q_ADATE"));
			System.out.println("답변 시간: " + board.get("ANSWERDATE"));
		} catch (Exception e) {
			System.out.println("답변이 없습니다.");
		}

		}catch(Exception e){
			System.out.println("문의한 글이 없습니다.");
			
		}
		
	
		System.out.println("(1)뒤로가기 (2)홈으로 이동 ");
		int result = ScanUtil.nextInt();
		switch(result) {
		case 1: return View.QUESTION;
		case 2:
		if (Login.loginMember == null) {
			return View.Home;
		} else {
			return View.MAIN;
		}
		}return View.QUESTIONANSWER;
	}

	public int qustiononeadmin() {
		List<Map<String, Object>> list = boardDao.qustiononeadmin();
		System.out.println("==================문의 내역====================");
		System.out.println("NO\t제목\t\t작성자\t작성일");
		for (int i = 0; i < list.size(); i++) {
			System.out.print(i + 1);
			System.out.print("\t" + list.get(i).get("Q_NAME"));
			System.out.print("\t\t" + list.get(i).get("ID"));
			System.out.println("\t" + list.get(i).get("QDATE"));
		}
		System.out.println("(1)조회 (2)뒤로 가기");
		int result = ScanUtil.nextInt();
		switch (result) {
		case 1:
			return View.QADMINANSWER;
		case 2:
			return View.QUESTIONBOARD;
		}

		return View.QUESTIONONEADMIN;
	}

	public int qadminanswer() {
		List<Map<String, Object>> list = boardDao.qustiononeadmin();
		List<Object> param = new ArrayList<Object>();
		System.out.println("몇번을 조회하시겠습니까? > ");
		int no = ScanUtil.nextInt()-1;

		System.out.println("질문번호: " + list.get(no).get("Q_NO"));
		System.out.println("아이디: " + list.get(no).get("ID"));
		System.out.println("제목: " + list.get(no).get("Q_NAME"));
		System.out.println("내용: " + list.get(no).get("Q_CONTENT"));
		System.out.println("등록일: " + list.get(no).get("QDATE"));
		System.out.println("(1)답변 등록 (2)뒤로가기");
		int result = ScanUtil.nextInt();
		switch (result) {
		case 1:
			System.out.println("답변: ");
			String answer = ScanUtil.nextLine();
			param.add(no+1);
			param.add(answer);
			int check = boardDao.qadminanswer(param);
			if (check > 0) {
				System.out.println("등록이 완료되었습니다.");
			} else {
				System.out.println("등록에 실패하였습니다.");
			}
		case 2:
			return View.QUESTIONONEADMIN;
		}
		return View.QADMINANSWER;
	}

	public int faqboard() {
		List<Map<String, Object>> list = boardDao.selectFAQ();
		System.out.println("FAQ번호\t질문");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).get("FQ_NO") + "\t" + list.get(i).get("FQ_ANSWER"));
		}

		try {
			if (("admin").equals((String) Login.loginMember.get("ID"))) {
				System.out.println("(1)조회 (2)자주묻는 질문등록 (3)뒤로가기");
				int result = ScanUtil.nextInt();
				switch (result) {
				case 1:
					return View.FAQANSWER;
				case 2:
					return View.FAQADD;
				case 3:
					return View.MAIN;
				}
			} else {
				System.out.println("(1)조회 (2)뒤로가기 >");
				int result = ScanUtil.nextInt();
				switch (result) {
				case 1:
					return View.FAQANSWER;
				case 2:
					return View.MAIN;
				}
			}
		} catch (Exception e) {
			System.out.println("(1)조회 (2)뒤로가기 >");
			int result = ScanUtil.nextInt();
			switch (result) {
			case 1:
				return View.FAQANSWER;
			case 2:
				if (Login.loginMember == null) {
					return View.Home;
				} else {
					return View.MAIN;
				}
			}
		}
		return View.FAQBOARD;
	}

	public int faqanswer() {
		System.out.println("몇번을 선택하시겠습니까 > ");
		int result = ScanUtil.nextInt();
		List<Map<String, Object>> list = boardDao.selectFAQ();
		System.out.println("질문 번호 : " + list.get(result).get("F_NO"));
		System.out.println("질문 : " + list.get(result).get("F_CONTENT"));
		System.out.println("답변 : " + list.get(result).get("F_ANSWER"));

		System.out.println("(1) 뒤로가기 (2)홈으로 이동");
		result = ScanUtil.nextInt();
		switch (result) {
		case 1:
			return View.FAQBOARD;
		case 2:
			if (Login.loginMember == null) {
				return View.Home;
			} else {
				return View.MAIN;
			}

		}
		return View.FAQANSWER;
	}

	public int faqadd() {
		System.out.print("제목: ");
		String title = ScanUtil.nextLine();
		System.out.print("내용: ");
		String content = ScanUtil.nextLine();
		int result = boardDao.faqadd(title, content);
		if (result > 0) {
			System.out.println("등록되었습니다.");
		} else {
			System.out.println("등록에 실패하였습니다.");
		}
		return View.FAQBOARD;
	}
}
