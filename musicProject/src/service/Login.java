package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dao.LoginDao;
import util.ScanUtil;
import util.View;

public class Login {
	private Login() {
	}

	private static Login instance;

	public static Login getInstance() {
		if (instance == null) {
			instance = new Login();
		}
		return instance;

	}

	public static Map<String, Object> loginMember;
	private LoginDao loginDao = LoginDao.getInstance();

	public int loginpage() {
		System.out.println();
		System.out.print("(1)로그인 (2)회원가입 (0)메인화면>");
		int num = ScanUtil.nextInt();
		switch (num) {
		case 1:
			return View.LOGIN;
		case 2:
			return View.JOIN;
		case 0:
			return View.HOME;
		}
		return View.LOGIN_PAGE;
	}

	public int login() {
		System.out.println("============= 로그인 =============");
		System.out.print("아이디: ");
		String id = ScanUtil.nextLine();
		System.out.print("비밀번호: ");
		String pwd = ScanUtil.nextLine();
		Map<String, Object> member = loginDao.selectMember(id, pwd);

		
		if (member == null) {
			System.out.println("[아이디 혹은 비밀번호를 잘못 입력하셨습니다]");
			return View.LOGIN_PAGE;
		} else {
			System.out.println("["+member.get("ID") + "님 환영합니다]");
			loginMember = member;
			return View.MAIN;
		}
	}

	public int join() {
		Map<String, Object> param = new HashMap<String, Object>();
		
		System.out.println("============= 회원가입 =============");
		while (true) {
			int num = 0;
			System.out.println();
			System.out.println("[cancel 입력시 회원가입 취소]");
			System.out.print("아이디(5글자이상 20글자이하, 숫자와 영문으로만 구성해주세요)>");
			String memId = ScanUtil.nextLine();
			if(memId.equals("cancel")) {
				return View.HOME;
			}
			String idCheck = "[a-z0-9]{5,20}";
			Pattern p1 = Pattern.compile(idCheck); // 패턴으로 분석해서 패턴으로 리턴
			Matcher m1 = p1.matcher(memId);
			List<Map<String, Object>> member = loginDao.duplicationId(memId);

			if (m1.matches() == false) { // 만약 정규식조건이 부합하지 않는다면.
				System.out.println("[올바른 아이디 형식이 아닙니다. 다시 입력해주세요]");
			}
			for (int i = 0; i < member.size(); i++) {
				if (memId.equals(member.get(i).get("ID"))) {
					System.out.println("[이미 등록된 아이디입니다!]");
					num++; //이미 등록된 아이디면은 num을 1올림
				}
			}
			if (m1.matches() == true && num == 0) { //정규표현식
				param.put("ID", memId);
				break;
			}
		}
		while(true) {
		int num = 0;
		System.out.println();
		System.out.println("[cancel 입력시 회원가입 취소]");
		System.out.print("비밀번호(5글자이상 20글자이하, 숫자와 영문으로 구성해주세요)>");
		String password = ScanUtil.nextLine();
		if(password.equals("cancel")) {
			return View.HOME;
		}
		String pwdCheck = "[a-z0-9]{5,20}";
		Pattern p2 = Pattern.compile(pwdCheck); // 패턴으로 분석해서 패턴으로 리턴
		Matcher m2 = p2.matcher(password);
		System.out.print("비밀번호 확인>");
		String password2 = ScanUtil.nextLine();
		if(!password.equals(password2)) {
			System.out.println("[비밀번호가 일치하지않습니다]");
			num++;
		}
		
		if (m2.matches() == false) { // 만약 정규식조건이 부합하지 않는다면.
			while (true) {
				System.out.println("[올바른 비밀번호 형식이 아닙니다. 다시 입력해주세요]");
				System.out.print("비밀번호(5글자이상 20글자이하, 숫자와 영문으로만 구성해주세요)>");
				password = ScanUtil.nextLine();
				m2 = p2.matcher(password);
				if (m2.matches() == true && num == 0) {
					param.put("PWD", password);
					break;
				}
			}
		} else if(num == 0) {
			param.put("PWD", password);
			break;
		}
		}
		System.out.println();
		System.out.println("[cancel 입력시 회원가입 취소]");
		System.out.print("이름>");
		String memName = ScanUtil.nextLine();
		if(memName.equals("cancel")) {
			return View.HOME;
		}
		String nameCheck = "[a-zA-Z가-힣 ]{2,30}";
		Pattern p3 = Pattern.compile(nameCheck);
		Matcher m3 = p3.matcher(memName);
		if (m3.matches() == false) { // 만약 정규식조건이 부합하지 않는다면.
			while (true) {
				System.out.println("[올바른 이름 형식이 아닙니다. 다시 입력해주세요]");
				System.out.println("이름은 한글 또는 영어로(2글자이상 30글자이하 형식입니다.)");
				System.out.print("이름>");
				memName = ScanUtil.nextLine();
				m3 = p3.matcher(memName);
				if (m3.matches() == true) {
					param.put("NAME", memName);
					break;
				}
			}
		} else {
			param.put("NAME", memName);
		}
		System.out.println();
		System.out.println("[cancel 입력시 회원가입 취소]");
		System.out.print("이메일주소>");
		String email = ScanUtil.nextLine();
		if(email.equals("cancel")) {
			return View.HOME;
		}
		String emailCheck = "[a-z0-9_-]{3,20}@[a-zA-Z]+\\.(?i)(com|net|org|([a-z]{2}\\.kr))$";
		Pattern p4 = Pattern.compile(emailCheck);
		Matcher m4 = p4.matcher(email);
		if (m4.matches() == false) {
			while (true) {
				System.out.println("[올바른 이메일 형식이 아닙니다. 다시 입력해주세요]");
				System.out.println("이메일 형식은 영어,숫자(3~20글자)@(이메일주소).(com, net, org, co.kr 등)입니다.");
				System.out.print("이메일주소>");
				email = ScanUtil.nextLine();
				m4 = p4.matcher(email);
				if (m4.matches() == true) {
					param.put("EMAIL", email);
					break;
				}
			}
		} else {
			param.put("EMAIL", email);
		}
		System.out.println();
		System.out.println("[cancel 입력시 회원가입 취소]");
		System.out.print("생년월일(숫자8자리)>");
		String birth = ScanUtil.nextLine();
		if(birth.equals("cancel")) {
			return View.HOME;
		}
		String birthCheck = "(19[0-9][0-9]|20[0-1]\\d{1})(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])";
		Pattern p5 = Pattern.compile(birthCheck);
		Matcher m5 = p5.matcher(birth);
		if (m5.matches() == false) {
			while (true) {
				System.out.println("[올바른 생년월일 형식이 아닙니다]");
				System.out.println("생년월일 형식은 숫자8자리 (19000101~20191231 형식입니다.");
				System.out.print("생년월일(숫자8자리)>");
				birth = ScanUtil.nextLine();
				m5 = p5.matcher(birth);
				if (m5.matches() == true) {
					param.put("BIRTH", birth);
					break;
				}
			}
		} else {
			param.put("BIRTH", birth);
		}
		System.out.println();
		System.out.println("[cancel 입력시 회원가입 취소]");
		System.out.print("주소>");
		String address = ScanUtil.nextLine();
		if(address.equals("cancel")) {
			return View.HOME;
		}
		System.out.print("입력하신주소 : " + address + " 맞습니까?(Y/N)>");
		String sel = ScanUtil.nextLine();
		if (sel.equals("Y") || sel.equals("y")) {
			param.put("ADDR", address);
		} else {
			while (true) {
				System.out.print("주소를 다시 입력해주세요>");
				address = ScanUtil.nextLine();
				System.out.print("입력하신주소 : " + address + "가 맞습니까?(Y/N)>");
				sel = ScanUtil.nextLine();
				if (sel.equals("Y") || sel.equals("y")) {
					param.put("ADDR", address);
					break;
				}
			}
		}
		System.out.println();
		System.out.println("[cancel 입력시 회원가입 취소]");
		System.out.print("결제할 카드 번호 16자리>");
		String cardNo = ScanUtil.nextLine();
		if(cardNo.equals("cancel")) {
			return View.HOME;
		}
		String cardNoCheck = "[0-9]{16}";
		Pattern p6 = Pattern.compile(cardNoCheck);
		Matcher m6 = p6.matcher(cardNo);
		if (m6.matches() == false) {
			while (true) {
				System.out.println("[올바른 카드번호 형식이 아닙니다]");
				System.out.print("카드번호 16자리를 입력해주세요>");
				cardNo = ScanUtil.nextLine();
				m6 = p6.matcher(cardNo);
				if (m6.matches() == true) {
					param.put("CARD_NO", cardNo);
					break;
				}
			}
		} else {
			param.put("CARD_NO", cardNo);
		}
		System.out.println();
		System.out.println("[cancel 입력시 회원가입 취소]");
		System.out.print("카드 비밀번호 4자리를 입력>");
		String cardPwd = ScanUtil.nextLine();
		if(cardPwd.equals("cancel")) {
			return View.HOME;
		}
		String cardPwdCheck = "[0-9]{4}";
		Pattern p7 = Pattern.compile(cardPwdCheck);
		Matcher m7 = p7.matcher(cardPwd);

		if (m7.matches() == false) {
			while (true) {
				System.out.println("[올바른 카드비밀번호 형식이 아닙니다]");
				System.out.print("카드비밀번호(숫자4자리)>");
				cardPwd = ScanUtil.nextLine();
				m7 = p7.matcher(cardPwd);
				if (m7.matches() == true) {
					param.put("CARD_PWD", cardPwd);
					break;
				}
			}
		} else {
			param.put("CARD_PWD", cardPwd);
		}

		// 아이디 중복 확인
		// 비밀번호 확인
		// 유효성 검사(정규표현식)

		int result = loginDao.insertMember(param);

		if (0 < result) {
			System.out.println("[회원가입 성공]");
		} else {
			System.out.println("[회원가입 실패]");
		}

		return View.HOME;

	}
}
