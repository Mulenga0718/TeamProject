package service;

import java.sql.SQLException;
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
		System.out.println("(1)자주 묻는 질문 (2)1:1 문의 (3)뒤로가기 >");
		int result = ScanUtil.nextInt();
		switch(result) {
		case 1: return View.FAQBOARD;
		case 2: return View.QUESTION;
		case 3: return View.Home;
		default: System.out.println("다시 입력해주세요");
		return View.QUESTIONBOARD;
		}
		
	}
	public int question() {
		System.out.println("==================1:1 문의====================");
		System.out.println("(1)문의 (2)문의 결과 확인 (3)뒤로가기 >");
		int result = ScanUtil.nextInt();
		switch(result) {
		case 1: return View.QUESTIONONE;
		case 2: return View.QUESTIONANSWER;
		case 3: return View.Home; 
		default: System.out.println("다시 입력해주세요.");
		return View.QUESTION; 
			
		}
		
	}
	public int questionone() {
		System.out.println("============= 문의 내용 ===============\n");
		System.out.print("제목 : ");
		String title = ScanUtil.nextLine();
		System.out.println("내용 : ");
		String content = ScanUtil.nextLine();
		boardDao.addQuestion(title, content);
		
		return View.Home;
	}
	public int questionanswer(){
		
		Map<String, Object> board = boardDao.selectQuestion();
		
		
		System.out.println("============= 문의 내용 ===============\n");
		System.out.println("질문 번호: "+ board.get("Q_NO"));
		System.out.println("아이디: " + board.get("ID"));
		System.out.println("내용: " + board.get("Q_CONTENT"));
		System.out.println("작성 시간: "+ board.get("QUESTIONDATE"));
		board = boardDao.selectAnswer();
		try{
			board.get("Q_ANSWER");

		System.out.println("============= 답변 ===================\n");
		System.out.println("답변: " + board.get("Q_ADATE"));
		System.out.println("답변 시간: " + board.get("ANSWERDATE"));}
		catch(Exception e) {
			System.out.println("답변이 없습니다.");
		}
		
		System.out.println("아무키나 누르시오(HOME으로 이동). > ");
		ScanUtil.nextInt();
		return View.Home;
	}
	public int faqboard() {
		List<Map<String, Object>> list = boardDao.selectFAQ();
		System.out.println("FAQ번호\t질문");
		for(int i=0; i<list.size(); i++) {
		System.out.print(list.get(i).get("F_NO")+"\t"+list.get(i).get("F_ANSWER"));	
		}
		System.out.println("(1)질문 선택 (2)뒤로가기 >");
		int result = ScanUtil.nextInt();
		switch(result) {
		case 1: return View.FAQANSWER;
		case 2: return View.Home;	
	
		}
		 return View.FAQBOARD;
	}
	public int faqanswer() {
		System.out.println("몇번을 선택하시겠습니까 > ");
		int result = ScanUtil.nextInt();
		List<Map<String, Object>> list = boardDao.selectFAQ();
		System.out.println("질문 번호 : "+ list.get(result).get("F_NO"));
		System.out.println("질문 : "+ list.get(result).get("F_CONTENT"));
		System.out.println("답변 : "+ list.get(result).get("F_ANSWER"));
		
		
		System.out.println("(1) 뒤로가기 (2)홈으로 이동");
		result = ScanUtil.nextInt();
		switch(result) {
		case 1: return View.FAQBOARD;
		case 2:  return View.Home;
		
	
		 
		}
		return View.FAQANSWER;
	}
}
	