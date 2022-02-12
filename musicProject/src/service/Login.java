package service;

import java.util.List;
import java.util.Map;

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

	public int login() {

		System.out.print("아이디: ");
		String id = ScanUtil.nextLine();
		System.out.print("비밀번호: ");
		String pwd = ScanUtil.nextLine();
		Map<String, Object> member = loginDao.selectMember(id, pwd);

		if (member == null) {
			System.out.println("아이디와 비밀번호를 잘못 입력하셨습니다.");
		} else {
			System.out.println(member.get("ID") + "님 환영합니다.");
			loginMember = member;
			return View.MAIN;
		}
		return View.LOGIN;
	}

}
