package util;

import java.util.List;
import java.util.Map;

import dao.MusicDao;
import service.AdminPage;
import service.Login;
import service.MusicService;

public class MusicPlayMethod {
	private MusicPlayMethod() {
	}

	private static MusicPlayMethod instance;

	public static MusicPlayMethod getInstance() {
		if (instance == null) {
			instance = new MusicPlayMethod();
		}
		return instance;

	}

	private MusicDao musicDao = MusicDao.getInstance();

	public int playMethod(List<Map<String, Object>> List) {
		System.out.println("=============================================");
		System.out.println("번호\t곡명\t\t\t가수");
		System.out.println("---------------------------------------------");

		int i;
		if (List.size() <= 5) {
			for (i = 0; i < List.size(); i++) {
				
			    String str = List.get(i).get("M_NAME").toString();
				 if(str.length()>11) {
					str = str.substring(0,12)+"..";
				    } 
				String str2 = List.get(i).get("M_SINGER").toString();
				if(str2.length()>11) {
					str2 = str2.substring(0,12)+"..";
				}
				System.out.printf("%d\t%-17s\t%-10s",i+1,str,str2);
				System.out.println();
			}
			System.out.println("=============================================");
			System.out.print("(1) 곡 선택 (0)뒤로가기 >");
			int num = ScanUtil.nextInt();
			switch (num) {
			case 1:
				System.out.print("몇 번 노래를 선택하시겠습니까?>");
				int choice = ScanUtil.nextInt() - 1;
				MusicService.currentmus = List.get(choice);
				if (Login.loginMember != null) {
					MusicService.subCheck = musicDao.subcheck();
					MusicService.musicCheck = musicDao.musiccheck();
					if (MusicService.subCheck.get("SUBCHECK") == null) {MusicService.subCheck.put("SUBCHECK", 0);}
					if (MusicService.musicCheck.get("MUSICCHECK") == null) {MusicService.musicCheck.put("MUSICCHECK","");}
				}
			
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
			case 0:
				if (Login.loginMember != null) {
					return View.MAIN;
				} else {
					return View.HOME;
				}
			}
		}
		if (List.size() > 5) {
			outer: for (i = 0; i <= List.size() / 5; i++) {
				for (int j = i * 5; j <= 5 * i + 4; j++) {
					if (j == List.size()) {
						break;
					}
				String str = List.get(j).get("M_NAME").toString();
				 if(str.length()>11) {
					str = str.substring(0,12)+"..";
				    } 
				String str2 = List.get(j).get("M_SINGER").toString();
				if(str2.length()>11) {
					str2 = str2.substring(0,12)+"..";
				}
				System.out.printf("%d\t%-17s\t%-10s",j+1,str,str2);
				System.out.println();	
				}
				System.out.println("---------------------------------------------");
				if (i == 0) {
					System.out.print("(1) 곡 선택 (2) 다음 페이지 (0)뒤로가기 >");
				} else if (i != 0) {
					System.out.print("(1) 곡 선택 (2) 다음 페이지 (3) 이전페이지 (0)뒤로가기 >");
				}
				int num = ScanUtil.nextInt();
				switch (num) {
				case 1:
					System.out.print("몇 번 노래를 선택하시겠습니까?>");
					int choice = ScanUtil.nextInt() - 1;
					MusicService.currentmus = List.get(choice);

					if (Login.loginMember != null) {
						MusicService.subCheck = musicDao.subcheck();
						MusicService.musicCheck = musicDao.musiccheck();
						if (MusicService.subCheck.get("SUBCHECK") == null) {MusicService.subCheck.put("SUBCHECK", 0);}
						if (MusicService.musicCheck.get("MUSICCHECK") == null) {MusicService.musicCheck.put("MUSICCHECK","");};
					}
					
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
				case 2:
					continue outer;
				case 3:
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
		
	}return View.HOME;
	}
}

