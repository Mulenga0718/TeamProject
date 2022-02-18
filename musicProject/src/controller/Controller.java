package controller;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import service.AdminPage;
import service.BuyCoupon;
import service.BuyMusic;
import service.BuyService;
import service.BuySubscribe;
import service.Login;
import service.Main;
import service.MusicService;
import service.MyListService;
import service.Mypage;
import service.NoSubService;
import service.Notice;
import service.Question;
import util.MusicPlayMethod;
import util.MyListPlayMethod;
import util.ScanUtil;
import util.View;

public class Controller {

	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		new Controller().start();
	}
	private Notice notice = Notice.getInstance();
	private Question question = Question.getInstance();
	private Main main = Main.getInstance();
	private AdminPage adminPage = AdminPage.getInstance();
	private MyListService myListService = MyListService.getInstance();
	private NoSubService noSubService  = NoSubService.getInstance();
	private MusicService musicService = MusicService.getInstance();
	private Login login = Login.getInstance();
	private Mypage mypage = Mypage.getInstance();
	private BuyService buyService = BuyService.getInstance();
	private BuyCoupon buyCoupon = BuyCoupon.getInstance();
	private BuySubscribe buySubscribe = BuySubscribe.getInstance();
	private MyListPlayMethod myListPlayMethod = MyListPlayMethod.getInstance();
	private BuyMusic buyMusic = BuyMusic.getInstance();
	private void start() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		int view = View.HOME;
			while (true) {
			switch (view) {
			case View.HOME : view = home(); break;
			case View.LOGIN_PAGE : view = login.loginpage(); break;
			case View.LOGIN : view = login.login(); break;
			case View.JOIN : view = login.join(); break;
			case View.MAIN : view = main.main(); break;
			case View.MYPAGE : view = mypage.memberInfo(); break;
			case View.MYPAGE_UPDATESEL : view = mypage.updateSel(); break;
			case View.MYPAGE_PWDUPDATE : view = mypage.pwdUpdate(); break; 
			case View.MYPAGE_ADDRUPDATE : view = mypage.addrUpdate(); break;
			case View.MYPAGE_CARDNOUPDATE : view = mypage.cardNoUpdate(); break;
			case View.BUYPAGE : view = buyService.BuyProd(); break;
			case View.BUYCOUPON : view = buyCoupon.buyCoupon(); break;
			case View.BUYMUSIC : view = buyMusic.buyMusic(); break;
			case View.BUYSUBSCRIBE : view = buySubscribe.buySubscribe(); break;
			case View.MUSIC_SEARCH : view = musicService.musicsearch(); break;
			case View.MUSIC_PLAY : view = musicService.musicPlay(); break;
			case View.MUSIC_CHART : view = musicService.musicChart(); break;
			case View.MUSIC_RECENTCHART : view = musicService.recentChart();break;
			case View.MUSIC_RECOMCHART : view = musicService.recomChart(); break;
			case View.MUSIC_MYLIST : view = myListService.myList(); break;
			case View.MUSIC_MYPLAYMAIN : view = myListPlayMethod.myplaymain(); break;
			case View.MUSIC_MYPLAYMETHOD :	view = myListPlayMethod.myPlayMethod(); break;
			case View.MUSIC_NOMEMPLAY : view = musicService.noMemPlay(); break;
			case View.MUSIC_NOSUBPLAY : view = noSubService.noSubPlay(); break; //회원이지만 구독권이 없는 사람 
			case View.NOTICEBOARD : view = notice.noticeBoard(); break;
			case View.NOTICECONTENT : view = notice.noticecontent(); break;
			case View.NOTICEADD : view = notice.noticeadd(); break;
			case View.QBOARD :  view = question.questionboard(); break;
			case View.Q : view = question.question(); break;
			case View.QONE : view =  question.questionone(); break;
			case View.QANSWER : view = question.questionanswer(); break;
			case View.QADMINANSWER: view = question.qadminanswer(); break;
			case View.QONEADMIN: view = question.qustiononeadmin(); break;
			case View.FAQBOARD : view = question.faqboard(); break;
			case View.FAQANSWER: view = question.faqanswer(); break;
			case View.FAQADD : view = question.faqadd(); break;
			case View.ADMINMAIN : view = adminPage.adminMain(); break;
			case View.ADMINMEMBER : view = adminPage.adminMember(); break;
			case View.AMEMBERALL : view = adminPage.aMemberAll(); break;
			case View.AMEMBERSEARCH : view = adminPage.aMemberSearch(); break;
			case View.IDSEARCH : view = adminPage.idSearch(); break;
			case View.NAMESEARCH : view = adminPage.nameSearch(); break;
			case View.ADMININCOME : view = adminPage.adminIncome(); break;
			case View.DAYINCOME : view = adminPage.dayIncome(); break;
			case View.ADMINMUSIC : view = adminPage.adminMusic(); break;
			case View.ADMINMSELECT : view = adminPage.adminMselect(); break;
			case View.ADMINMMOD : view = adminPage.adminMmod(); break;
			case View.ADMINMADD : view = adminPage.adminMadd(); break;
			case View.ADMINCOU : view = adminPage.admincou(); break;
			case View.ADMINCOUSELECT : view = adminPage.admincouselect(); break;
			case View.ADMINCOUADD : view = adminPage.admincouadd(); break;
			case View.ADMINSUB : view = adminPage.adminsub(); break;
			case View.ADMINSUBSELECT : view = adminPage.adminsubselect(); break;
			case View.ADMINSUBADD : view = adminPage.adminsubadd(); break;
			
			case 0: 
				System.out.println("시스템을 종료합니다.");
				System.exit(0);
			}
		}
	}
	
	private int home() {
		System.out.println("🎵==================== 대덕 뮤직 =======================🎵");
		System.out.println();
		System.out.print("(1)로그인페이지 (2)노래검색 (3)인기차트 (4)최신 음악"
				+ " (5)추천노래목록 (6)공지사항 (7)문의사항 (0)프로그램종료>");
		int result = ScanUtil.nextInt();
		switch(result) {
		case 1: return View.LOGIN_PAGE;
		case 2: return View.MUSIC_SEARCH;
		case 3 :return View.MUSIC_CHART;
		case 4: return View.MUSIC_RECENTCHART;
		case 5: return View.MUSIC_RECOMCHART;
		case 6: return View.NOTICEBOARD; 
		case 7: return View.QBOARD; 
		case 0: System.out.println("[프로그램이 종료되었습니다]");
		System.exit(0);
		}
		return View.HOME;
	}
}