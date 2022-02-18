package service;

import java.util.HashMap;
import java.util.Map;

import dao.BuyCouDao;
import dao.LoginDao;
import util.ScanUtil;
import util.View;

public class BuyCoupon {
	private BuyCoupon() {
	}

	private static BuyCoupon instance;

	public static BuyCoupon getInstance() {
		if (instance == null) {
			instance = new BuyCoupon();
		}
		return instance;
	}
	
	private BuyCouDao buycoudao = BuyCouDao.getInstance();
	private LoginDao loginDao = LoginDao.getInstance();
	
	public int buyCoupon() {
		String memberPwd = String.valueOf(Login.loginMember.get("CARD_PWD"));
		System.out.println("【구매할 쿠폰을 선택해주세요】");
		System.out.print("1.1개쿠폰(400원) 2.3개쿠폰(1200원) 3.5개쿠폰(1900원) 4.10개쿠폰(3500원)>");
		String couSel = ScanUtil.nextLine();
		if(Login.loginMember == null) {
			System.out.println("[로그인후 구매가능합니다]");
			return View.HOME;
		}
		System.out.print("결제비밀번호(카드비밀번호) 입력>");
		String cardPwd = ScanUtil.nextLine();
		if(cardPwd.equals(memberPwd))
		{
		String memId = (String)Login.loginMember.get("ID");
		String memPwd = (String)Login.loginMember.get("PWD");
		Map<String, Object>param = new HashMap<String, Object>();
		param.put("COU_NO", couSel);
		param.put("ID", memId);
		
		int result = buycoudao.buyCoupon(param);
		if (0 < result) {
			System.out.println("[쿠폰구매가 성공적으로 완료되었습니다]");
			System.out.println();
		} else {
			System.out.println("[쿠폰구매에 실패하였습니다]");
			System.out.println();
		}
		Map<String, Object> member = loginDao.selectMember(memId, memPwd);
		Login.loginMember = member;
		}else {
			System.out.println("[결제비밀번호가 틀립니다]");
			return View.MAIN;
		}
		return View.MAIN;
	}
	
}
