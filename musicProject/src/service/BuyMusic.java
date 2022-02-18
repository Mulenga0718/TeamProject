package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.BuyMusicDao;
import dao.MusicDao;
import util.ScanUtil;
import util.View;

public class BuyMusic {
	private BuyMusic() {
	}
//	private MusicService musicService = MusicService.getInstance();

	private static BuyMusic instance;
	private MusicDao musicDao = MusicDao.getInstance();
	private BuyMusicDao buyMusicDao = BuyMusicDao.getInstance();
	public static Object currentmusadd; // 음악 주소
	public static List<Map<String, Object>> music;

	public static BuyMusic getInstance() {
		if (instance == null) {
			instance = new BuyMusic();
		}
		return instance;
	}
	
	public int buyMusic(){
		String memberPwd = String.valueOf(Login.loginMember.get("CARD_PWD"));
		System.out.println();
		System.out.println("현재 듣고 있는 음악 : " 
                       + MusicService.currentmus.get("M_SINGER") +" "
                       + MusicService.currentmus.get("M_NAME"));
		int cou = Integer.parseInt(String.valueOf(Login.loginMember.get("COU_REST")));
		System.out.println("현재 잔여 쿠폰의 개수가 <"+ cou +"개> 입니다.");
		System.out.print("쿠폰을 사용해서 음악을 구매하시겠습니까?(Y/N)>");	
		String sel = ScanUtil.nextLine();
		if(sel.equals("Y")||sel.equals("y")) {	
			System.out.print("결제비밀번호(카드비밀번호) 입력>");
			String cardPwd = ScanUtil.nextLine();
			if(cardPwd.equals(memberPwd)) {
			if(cou > 0) {				
					Map<String, Object>param = new HashMap<String, Object>();
					String memId = (String)Login.loginMember.get("ID");
					int musId = Integer.parseInt(String.valueOf(MusicService.currentmus.get("M_NO")));
					param.put("ID", memId);
					param.put("M_NO", musId);
					
					int result3 = buyMusicDao.buyMusic(param);
					if (0 < result3) {
						System.out.println("[음악구매가 성공적으로 완료되었습니다]");
					} else {
						System.out.println("[음악구매에 실패하였습니다]");
					}
					return View.MAIN;				
			}else {
				System.out.println("[보유한 음악구매쿠폰이 없습니다]");
				System.out.print("(1)쿠폰 혹은 구독권 구매하러가기 (0)돌아가기>");
				int sel3 = ScanUtil.nextInt();
				switch(sel3) {
				case 1: return View.BUYPAGE;
				case 0: return View.MAIN;
				}
		}
			}else {
				System.out.println("[결제비밀번호가 틀립니다]");
				return View.MAIN;
			}
		}else return View.MAIN;
		return View.MAIN;
	}
}




















