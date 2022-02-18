package util;

import java.util.List;
import java.util.Map;

import dao.MusicDao;
import dao.MyListDao;
import service.Login;
import service.MusicService;
import service.MyListService;

public class MyListPlayMethod {

	private MyListPlayMethod() {
	}
	
	private MyListDao myListDao = MyListDao.getInstance(); // 마이리스트다오 객체 생성
	private static MyListPlayMethod instance;

	public static MyListPlayMethod getInstance() {
		if (instance == null) {
			instance = new MyListPlayMethod();
		}
		return instance;
	}
	private MusicDao musicDao = MusicDao.getInstance();
	
	public int myplaymain() {
		System.out.println();
		System.out.print("(1)음원구매 리스트  (2)playlist   (0)뒤로가기>" );
		int input = ScanUtil.nextInt();
		switch(input) {
		case 1:  MusicService.mlist=myListDao.selectBuyMusic();
		return View.MUSIC_MYPLAYMETHOD;
		case 2:  MusicService.mlist = myListDao.selectMyList();
		return View.MUSIC_MYPLAYMETHOD;
		}
		return View.MUSIC_MYPLAYMAIN;
	}
	// 내 음악 출력
	public int myPlayMethod() {
		System.out.println("=============================================");
		System.out.println("번호\t곡명\t\t\t가수");
		System.out.println("---------------------------------------------");
		int i;
		
		if (MusicService.mlist.size() <= 5) {
			for (i = 0; i < MusicService.mlist.size(); i++) {
				 String str = MusicService.mlist.get(i).get("M_NAME").toString();
				 if(str.length()>11) {
					str = str.substring(0,12)+"..";
				    } 
				String str2 = MusicService.mlist.get(i).get("M_SINGER").toString();
				if(str2.length()>11) {
					str2 = str2.substring(0,12)+"..";
				}
				System.out.printf("%d\t%-17s\t%-10s",i+1,str,str2);
				System.out.println();	
		
			}
			System.out.print("(1) 곡 선택 (2) 음악 리스트 섞기 (0)뒤로가기 >");
			int num = ScanUtil.nextInt();
			switch (num) {
			case 1:
				System.out.print("몇 번 노래를 선택하시겠습니까?>");
				int choice = ScanUtil.nextInt() - 1;
				MusicService.currentmus = MusicService.mlist.get(choice);
			
					MusicService.subCheck = musicDao.subcheck();
					MusicService.musicCheck = musicDao.musiccheck();
			
					if (MusicService.subCheck.get("SUBCHECK") == null) {MusicService.subCheck.put("SUBCHECK", 0);}
					if (MusicService.musicCheck.get("MUSICCHECK") == null) {MusicService.musicCheck.put("MUSICCHECK","");};
					if (Login.loginMember == null) {
						return View.MUSIC_NOMEMPLAY;
					} else if (Integer.parseInt(String.valueOf(MusicService.subCheck.get("SUBCHECK"))) > 0) {
						return View.MUSIC_PLAY;
					} else if (String.valueOf(MusicService.musicCheck.get("MUSICCHECK")).equals(MusicService.currentmus.get("M_ADDR")) ) { // 회원이지만 노래 구매																					// 노래구매했다
						return View.MUSIC_PLAY;
					} else {
						return View.MUSIC_NOSUBPLAY;
					}
				
			case 2: // 음악 리스트 섞기 (1.가수이름순 2.등록순 3.제목)
				System.out.print("(1)제목별 (2)최신등록 순 (3)가수이름 순> ");
				int input = ScanUtil.nextInt();
				switch (input) {
				case 1:
					MusicService.mlist = myListDao.listMixName();
					return myPlayMethod()  ;
				case 2:
					MusicService.mlist = myListDao.listMixDate(); // 최신등록 순
					return myPlayMethod();
				case 3:
					MusicService.mlist = 	myListDao.listMixSinger();
					return myPlayMethod();
				}

			case 0: // 뒤로가기 (회원 : 메인화면 / 비회원 : 홈화면 )
				if (Login.loginMember != null) {
					return View.MAIN;
				} else {
					return View.HOME;
				}
			}
		}
		if (MusicService.mlist.size() > 5) {
			outer: for (i = 0; i <= MusicService.mlist.size() / 5; i++) {
				for (int j = i * 5; j <= 5 * i + 4; j++) {
					if (j == MusicService.mlist.size()) {
						break;
					}
					String str = MusicService.mlist.get(j).get("M_NAME").toString();
					 if(str.length()>11) {
						str = str.substring(0,12)+"..";
					    } 
					String str2 = MusicService.mlist.get(j).get("M_SINGER").toString();
					if(str2.length()>11) {
						str2 = str2.substring(0,12)+"..";
					}
					System.out.printf("%d\t%-17s\t%-10s",j+1,str,str2);
					System.out.println();		
				
				}
				if (i == 0) {
					System.out.print("(1) 곡 선택 (2) 음악 리스트 섞기 (3)다음 페이지 (0)뒤로가기 >");
				} else if (i != 0) {
					System.out.print("(1) 곡 선택 (2)음악 리스트 섞기 (3) 다음 페이지 (4) 이전페이지 (0)뒤로가기 >");
				}
				int num = ScanUtil.nextInt();
				switch (num) {
				case 1:
					System.out.print("몇 번 노래를 선택하시겠습니까?>");
					int choice = ScanUtil.nextInt() - 1;
					MusicService.currentmus = MusicService.mlist.get(choice);
					
					MusicService.subCheck = musicDao.subcheck();
					MusicService.musicCheck = musicDao.musiccheck();
			
				if (MusicService.subCheck.get("SUBCHECK") == null) {MusicService.subCheck.put("SUBCHECK", 0);}
				if (MusicService.musicCheck.get("MUSICCHECK") == null) {MusicService.musicCheck.put("MUSICCHECK","");};
				if (Login.loginMember == null) {
					return View.MUSIC_NOMEMPLAY;
				} else if (Integer.parseInt(String.valueOf(MusicService.subCheck.get("SUBCHECK"))) > 0) {
					return View.MUSIC_PLAY;
				} else if (String.valueOf(MusicService.musicCheck.get("MUSICCHECK")).equals(MusicService.currentmus.get("M_ADDR")) ) { // 회원이지만
																										// 노래구매했다
					return View.MUSIC_PLAY;
				} else {
					return View.MUSIC_NOSUBPLAY;
				}
				case 2 :
				System.out.print("(1)제목별 (2)최신등록 순 (3)가수이름 순> ");
				int input = ScanUtil.nextInt();
				switch (input) {
				case 1:
					MusicService.mlist = myListDao.listMixName();
					return myPlayMethod();
				case 2:
					MusicService.mlist = myListDao.listMixDate(); // 최신등록 순
					return myPlayMethod();
				case 3:
					MusicService.mlist = 	myListDao.listMixSinger();
					return myPlayMethod();
				}	
				case 3:
					continue outer;
				case 4:
					i = i - 2;
					continue outer;
				case 0:
					if (Login.loginMember == null) {
						return View.HOME;
					} else if (Login.loginMember != null) {
						return View.MAIN;
					}
				}
			}
		}
		return View.HOME;
	}

}
