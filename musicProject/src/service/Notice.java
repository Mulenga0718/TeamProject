package service;

import java.util.List;
import java.util.Map;

import dao.BoardDao;
import util.ScanUtil;
import util.View;

public class Notice {
	private Notice() {
	}

	private static Notice instance;

	public static Notice getInstance() {
		if (instance == null) {
			instance = new Notice();
		}
		return instance;
	}

	private BoardDao boardDao = BoardDao.getInstance();

	public int noticeBoard() {
		List<Map<String, Object>> list = boardDao.selectBoard();
		System.out.println("===============공지 사항================");
		System.out.println("NO"+"   "+"제목\t\t\t\t작성일");
		for (int i = 0; i < list.size(); i++) {
			System.out.printf("%-4s %-20s\t%-10s",list.get(i).get("ARTICLENO"),list.get(i).get("TITLE"),list.get(i).get("WRITEDATE"));
			System.out.println();
		}
		try {
			if (("admin").equals((String) Login.loginMember.get("ID"))) {
				System.out.print("(1)조회 (2)공지등록 (0)뒤로가기");
				int result = ScanUtil.nextInt();
				switch (result) {
				case 1:
					return View.NOTICECONTENT;
				case 2:
					return View.NOTICEADD;
				case 0:
					return View.MAIN;
				}
			} else {
				System.out.print("(1)조회 (0)뒤로가기 > ");
				int result = ScanUtil.nextInt();
				switch (result) {
				case 1:
					return View.NOTICECONTENT;
				case 0:
					if(Login.loginMember == null) {
						return View.HOME;
					}else {
					return View.MAIN;}
				}
			}
		} catch (Exception e) {
			System.out.print("(1)조회 (0)뒤로가기 > ");
			int result = ScanUtil.nextInt();
			switch (result) {
			case 1:
				return View.NOTICECONTENT;
			case 0:
				if(Login.loginMember == null) {
					return View.HOME;
				}else {
				return View.MAIN;}
			}
			}
		

		return View.NOTICEBOARD;
	}

	public int noticecontent() {
		List<Map<String, Object>> list = boardDao.selectBoard();

		System.out.print("몇번을 조회하시겠습니까? ");
		int result = list.size() - ScanUtil.nextInt() ;
		System.out.println("게시판 코드: " + list.get(result).get("ARTICLENO"));
		System.out.println("제목: " + list.get(result).get("TITLE"));
		System.out.println("내용: " + list.get(result).get("CONTENT"));
		System.out.println("게시일: " + list.get(result).get("WRITEDATE"));

		System.out.print("(1)뒤로가기 (0)홈화면으로 ");
		result = ScanUtil.nextInt();
		switch (result) {
		case 1:
			return View.NOTICEBOARD;
		case 0:
			if(Login.loginMember == null) {
				return View.HOME;
			}else {
			return View.MAIN;}
		}

		return View.NOTICECONTENT;
	}

	public int noticeadd() {

		System.out.print("제목: ");
		String title = ScanUtil.nextLine();
		System.out.print("내용: ");
		String content = ScanUtil.nextLine();
		int result = boardDao.noticeadd(title, content);
		if (result > 0) {
			System.out.println("[등록되었습니다]");
		} else {
			System.out.println("[등록에 실패하였습니다]");
		}

		return View.NOTICEBOARD;
	}
}
