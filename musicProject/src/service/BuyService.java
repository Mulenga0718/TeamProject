package service;

import util.ScanUtil;
import util.View;

public class BuyService {
	private BuyService() {
	}

	private static BuyService instance;

	public static BuyService getInstance() {
		if (instance == null) {
			instance = new BuyService();
		}
		return instance;

	}
	
	public int BuyProd() {
		int memPno = Integer.parseInt(String.valueOf(Login.loginMember.get("P_NO")));   
		System.out.println();
		System.out.println("[구매할 상품을 선택해주세요]");
		System.out.print("(1)쿠폰 (2)구독권 (0)돌아가기>");
	int sel = ScanUtil.nextInt();
	switch(sel) {
	case 1: return View.BUYCOUPON;
	case 2: if(memPno > 0) {
		System.out.println("[이미 구독권을 보유중입니다]");
		return View.MAIN;
	}else
		return View.BUYSUBSCRIBE;
	case 3: if(Login.loginMember==null) {
			return View.HOME;		
			}else {
			return View.MAIN;
	}
	}
	return View.MAIN;
	}
}
