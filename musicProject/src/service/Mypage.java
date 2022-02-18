package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.MypageDao;
import util.ScanUtil;
import util.View;

public class Mypage {
	private Mypage() {
	}

	private static Mypage instance;

	public static Mypage getInstance() {
		if (instance == null) {
			instance = new Mypage();
		}
		return instance;

	}

	private MypageDao mypageDao = MypageDao.getInstance();

	public int memberInfo() {
		List<Map<String, Object>> memberInfo = mypageDao.memberInfo(Login.loginMember);

		System.out.println("============ 내정보 ===========");
		for (Map<String, Object> member : memberInfo) {
			System.out.println("아이디 : " + member.get("ID") 
			          + "\n" + "비밀번호 : " + member.get("PWD") 
			          + "\n" + "이름 : "   + member.get("NAME") 
			          + "\n" + "이메일 : " + member.get("EMAIL") 
			          + "\n" + "생년월일 : "+ member.get("BIRTH") 
		    	      + "\n" + "주소 : " + member.get("ADDR") 
			          + "\n" + "카드번호 : " + member.get("CARD_NO")
			          + "\n" + "카드비밀번호 : " + member.get("CARD_PWD")
			          + "\n" + "잔여쿠폰개수 : " + member.get("COU_REST")
			          + "\n" + "회원가입날짜 : " + member.get("JOINDATE") 
			          + "\n" + "구독권정보 : "+ member.get("P_NAME"));
		}
		System.out.println("=============================");
		System.out.print("(1)회원정보수정 (0)뒤로가기>");
		int sel = ScanUtil.nextInt();
		switch (sel) {
		case 1:
			return View.MYPAGE_UPDATESEL;
		case 0:
			return View.MAIN;
		}

		return View.MAIN;
	}

	public int updateSel() {
		System.out.print("(1)비밀번호 수정 (2)주소 수정 (3)카드번호 수정 (0)뒤로가기>");
		int sel = ScanUtil.nextInt();
		switch (sel) {
		case 1: return View.MYPAGE_PWDUPDATE;
		case 2: return View.MYPAGE_ADDRUPDATE;
		case 3: return View.MYPAGE_CARDNOUPDATE;
		case 0: return View.MAIN;
		}
		return View.MAIN;
	}

	public int pwdUpdate() {
		System.out.print("현재 비밀번호>");
		String curPassword = ScanUtil.nextLine();
		if (curPassword.equals(Login.loginMember.get("PWD"))) {
			System.out.print("수정할 비밀번호>");
			String password = ScanUtil.nextLine();
			System.out.print("비밀번호 확인>");
			String password2 = ScanUtil.nextLine();
			String memId = (String) Login.loginMember.get("ID");
			Map<String, Object> param = new HashMap<String, Object>();

			if (password.equals(password2)) {
				param.put("PWD", password);
				param.put("ID", memId);
			} else {
				System.out.println("[비밀번호가 일치하지 않습니다]");
				return View.MAIN;
			}

			int result = mypageDao.updatePwdInfo(param);
			if (0 < result) {
				System.out.println("[비밀번호 수정이 완료되었습니다]");
			} else {
				System.out.println("[비밀번호 수정에 실패하였습니다]");
			}
		} else {
			System.out.println("[현재 비밀번호가 틀리기에 수정할 수 없습니다]");
			return View.MAIN;
		}
		return View.MAIN;
	}
	
	public int addrUpdate() {
		Map<String, Object> param = new HashMap<String, Object>();
		List<Map<String, Object>> memberInfo = mypageDao.memberInfo(Login.loginMember);
		for(Map<String, Object>member : memberInfo) {
		System.out.println("현재 등록되어진 주소는 : [" + member.get("ADDR") + "] 입니다" );
		}
		System.out.print("변경할 주소를 입력해주세요>");
		String newaddr = ScanUtil.nextLine();
		String memId = (String) Login.loginMember.get("ID");
		System.out.print("변경할 주소가 [" + newaddr + "] 맞습니까(Y/N)?>");
		String sel = ScanUtil.nextLine();
		if(sel.equals("Y")||sel.equals("y")) {
		param.put("ADDR", newaddr);
		param.put("ID", memId);
		int result = mypageDao.updateAddrInfo(param);
		if (0 < result) {
			System.out.println("[주소 수정이 완료되었습니다]");
		} else {
			System.out.println("[주소 수정에 실패하였습니다]");
		}
		return View.MAIN;
		}else {
			System.out.println("[다시 시도해주십시오]");
			return View.MAIN;
		}
	}
	
	public int cardNoUpdate() {
		System.out.print("변경할 카드번호를 입력해주세요>");
		String newCardNo = ScanUtil.nextLine();
		String memId = (String) Login.loginMember.get("ID");
		Map<String, Object>param = new HashMap<String, Object>();
		
		param.put("CARD_NO",newCardNo);
		param.put("ID", memId);
		
		int result = mypageDao.updateCardNoInfo(param);
		if (0 < result) {
			System.out.println("[카드번호 수정이 완료되었습니다]");
		} else {
			System.out.println("[카드번호 수정에 실패하였습니다]");
		}
		return View.MAIN;
	}
}













