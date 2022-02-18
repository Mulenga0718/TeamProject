package service;

import java.util.HashMap;
import java.util.Map;

import dao.BuySubDao;
import util.ScanUtil;
import util.View;

public class BuySubscribe {
	private BuySubscribe() {
	}

	private static BuySubscribe instance;

	public static BuySubscribe getInstance() {
		if (instance == null) {
			instance = new BuySubscribe();
		}
		return instance;
	}
	
	private BuySubDao buysubdao = BuySubDao.getInstance();
	
	public int buySubscribe() {
		String memberPwd = String.valueOf(Login.loginMember.get("CARD_PWD"));
		System.out.println("구매할 구독권을 선택하세요.");
		System.out.print("【1.1개월 구독권 2.3개월 구독권 3.6개월 구독권 4.1년 구독권】>");
		String subSel = ScanUtil.nextLine();
		if(Login.loginMember == null) {
			System.out.println("[로그인후 구매가능합니다]");
			return View.HOME;
		}
		System.out.print("결제비밀번호(카드비밀번호) 입력>");
		String cardPwd = ScanUtil.nextLine();
		if(cardPwd.equals(memberPwd))
		{
		Map<String, Object>param = new HashMap<String, Object>();
		String memId = (String)Login.loginMember.get("ID");
		param.put("P_NO", subSel);
		param.put("ID", memId);
		
		int result = buysubdao.buySubscribe(param);
		if (0 < result) {
			System.out.println("[구독권구매가 성공적으로 완료되었습니다]");
		} else {
			System.out.println("[구독권구매에 실패하였습니다]");
		}
		}else {
			System.out.println("[결제비밀번호가 틀립니다]");
			return View.MAIN;
		}
		return View.MAIN;
	}
	
}














