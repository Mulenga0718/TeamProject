package service;

import util.ScanUtil;
import util.View;

public class Main {
	private Main() {
	}
private static Main instance; 
public static Main getInstance() { 
	if (instance == null) {
		instance = new Main();
	}
	return instance;
}
	public int main() {
	System.out.println("🎵==================== 대덕 뮤직 =======================🎵");
	System.out.println();
	System.out.print("(1)노래검색 (2)인기차트 (3)최신 음악 (4)추천노래목록 "
			+ "(5)공지사항 \n(6)문의사항 (7)내 음악 (8)마이페이지 (9)구매 (10)로그아웃 (0)프로그램종료>");
	int result = ScanUtil.nextInt();
	switch(result) {
	case 1: return View.MUSIC_SEARCH;
	case 2: return View.MUSIC_CHART;
	case 3: return View.MUSIC_RECENTCHART;
	case 4: return View.MUSIC_RECOMCHART;
	case 5: return View.NOTICEBOARD; 
	case 6: return View.QBOARD; 
	case 7: return View.MUSIC_MYPLAYMAIN;
	case 8 :if(Login.loginMember.get("ID").toString().equals("admin")){
   		return View.ADMINMAIN;
   	}else {
   		return View.MYPAGE;
   	}
	case 9 : return View.BUYPAGE;
	case 10: Login.loginMember = null; return View.HOME; 
	case 0: System.out.println("[프로그램이 종료되었습니다]");
	System.exit(0);
	}return View.MAIN;
}
}

