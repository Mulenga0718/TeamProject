package controller;

import service.Login;
import service.Main;
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
	private Login login = Login.getInstance();
	private Main main = Main.getInstance();
	private void start() {
		int view = View.Home;
			while (true) {
			switch (view) {
			case View.Home : view = home(); break;
			case View.LOGIN : view = login.login(); break;
			case View.MAIN : view = main.main(); break;
			case View.NOTICEBOARD : view = notice.noticeBoard(); break;
			case View.NOTICECONTENT : view = notice.noticecontent(); break;
			case View.NOTICEADD : view = notice.noticeadd(); break;
			case View.QUESTIONBOARD :  view = question.questionboard(); break;
			case View.QUESTION : view = question.question(); break;
			case View.QUESTIONONE : view =  question.questionone(); break;
			case View.QUESTIONANSWER : view = question.questionanswer(); break;
			case View.QADMINANSWER: view = question.qadminanswer(); break;
			case View.QUESTIONONEADMIN: view = question.qustiononeadmin(); break;
			case View.FAQBOARD : view = question.faqboard(); break;
			case View.FAQANSWER: view = question.faqanswer(); break;
			case View.FAQADD : view = question.faqadd(); break;
			
			case 0: 
				System.out.println("시스템을 종료합니다.");
				System.exit(0);
			}
		}
	}
	
	private int home() {
		System.out.println("🎵==================== 대덕 뮤직 =======================🎵");
		System.out.println();
		System.out.println("1.로그인페이지 2.노래검색 3.인기차트 4.최신 음악"
				+ " 5.추천노래목록 6.공지사항 7.문의사항  0.프로그램종료 >");
		int result = ScanUtil.nextInt();
		switch(result) {
		case 1: return View.LOGIN;
		case 6: return View.NOTICEBOARD; 
		case 7: return View.QUESTIONBOARD; 
		case 0: System.out.println("프로그램이 종료되었습니다.");
		System.exit(0);
		
		}
		return View.Home;
	}
}