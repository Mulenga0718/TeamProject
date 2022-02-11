package controller;

import service.Notice;
import service.Question;
import util.ScanUtil;
import util.View;

public class Controller {

	public static void main(String[] args) {
		new Controller().start();
	}
	private Notice notice = Notice.getInstance();
	private Question question = Question.getInstance();
	
	private void start() {
		int view = View.Home;
			while (true) {
			switch (view) {
			case View.Home : view = home(); break;
			case View.NOTICEBOARD : view = notice.noticeBoard(); break;
			case View.NOTICECONTENT : view = notice.noticecontent(); break;
			case View.QUESTIONBOARD :  view = question.questionboard(); break;
			case View.QUESTION : view = question.question(); break;
			case View.QUESTIONONE : view =  question.questionone(); break;
			case View.QUESTIONANSWER : view = question.questionanswer(); break;
			case View.FAQBOARD : view = question.faqboard(); break;
			case View.FAQANSWER: view = question.faqanswer(); break;
			case 9:	 break;
			case 0: 
				System.out.println("시스템을 종료합니다.");
				System.exit(0);
		
			}
		}
	}
	
	private int home() {
		
			
			System.out.println("🎵==================== 대덕 뮤직 =======================🎵");
			System.out.println();
			System.out.print("1.노래검색 2.인기차트 3.최신 음악 4.추천노래목록 "
					+ "5.공지사항 6.문의사항 7.내 음악 8.마이페이지 9.로그아웃 0.프로그램종료 >");
			int result = ScanUtil.nextInt();
			switch(result) {
			case 5: return View.NOTICEBOARD; 
			case 6: return View.QUESTIONBOARD; 
			case 0: System.out.println("프로그램이 종료되었습니다.");
			System.exit(0);
			}
		
		return View.Home;
	}
	}