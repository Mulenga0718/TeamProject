package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dao.AdminDao;
import util.ScanUtil;
import util.View;

public class AdminPage {
	private AdminPage() {
	}

	private static AdminPage instance;

	public static AdminPage getInstance() {
		if (instance == null) {
			instance = new AdminPage();
		}
		return instance;

	}

	private Map<String, Object> musicinfo;
	private Map<String, Object> couinfo;
	private Map<String, Object> subinfo;
	private AdminDao adminDao = AdminDao.getInstance();

	public int adminMain() {
		System.out.print("(1)회원정보 관리 (2)수입 확인 (3)음악 정보 관리 (4)쿠폰 정보 관리 (5)구독권 정보 관리 (0)뒤로 가기 > ");
		int result = ScanUtil.nextInt();
		switch (result) {
		case 1:
			return View.ADMINMEMBER;
		case 2:
			return View.ADMININCOME;
		case 3:
			return View.ADMINMUSIC;
		case 4:
			return View.ADMINCOU;
		case 5:
			return View.ADMINSUB;
		case 0:
			return View.MAIN;
		}
		return View.ADMINMAIN;
	}

	public int adminMember() {
		System.out.print("(1)전체 회원 조회 (2)회원 검색 (0) 뒤로가기 > ");
		int result = ScanUtil.nextInt();
		switch (result) {
		case 1:
			return View.AMEMBERALL;
		case 2:
			return View.AMEMBERSEARCH;
		case 0:
			return View.ADMINMAIN;
		}
		return View.ADMINMEMBER;
	}

	public int aMemberAll() {
		List<Map<String, Object>> list = adminDao.aMemberAll();
		int ten = list.size() / 10;
		int rest = 0;
		int count = 0;
		System.out.println("===================== 전체 회원 조회 ===================");
		System.out.println("번호\t아이디\t이름\t생년월일");
		for (int i = 0; i < ten + 1; i++) {
			if (i < ten) {
				count = 10;
			} else {
				count = 0;
				rest = list.size() % 10;
			}
			for (int j = i * 10; j < (count + (10 * i)) + rest; j++) {
				System.out.print(list.size() - j);
				System.out.print("\t" + list.get(j).get("ID"));
				System.out.print("\t" + list.get(j).get("NAME"));
				System.out.print("\t" + list.get(j).get("BIRTH") + "\n");
			}
			if (i == 0) {
				System.out.print("(1)회원정보 상세 조회 (2)다음 페이지 (0)뒤로가기 >");
				int result = ScanUtil.nextInt();
				switch (result) {
				case 1:
					System.out.print("몇번을 조회하시겠습니까? > ");
					int choice = ScanUtil.nextInt();
					Map<String, Object> board = list.get(list.size() - choice);
					search(board);
					i -= 1;
					break;
				case 2:
					break;
				case 0:
					return View.ADMINMEMBER;
				}
			} else if (i == ten) {
				System.out.print("(1)회원정보 상세 조회 (2)이전 페이지 (0)뒤로가기 >");
				int result = ScanUtil.nextInt();
				switch (result) {
				case 1:
					System.out.print("몇번을 조회하시겠습니까? > ");
					int choice = ScanUtil.nextInt();
					Map<String, Object> board = list.get(list.size() - choice);
					search(board);
					i -= 1;
					break;
				case 2:
					i -= 2;
					break;
				case 0:
					return View.ADMINMEMBER;
				}
			} else {
				System.out.print("(1)회원정보 상세 조회 (2)다음 페이지 (3)이전 페이지 (0)뒤로가기 >");
				int result = ScanUtil.nextInt();
				switch (result) {
				case 1:
					System.out.print("몇번을 조회하시겠습니까? > ");
					int choice = ScanUtil.nextInt();
					Map<String, Object> board = list.get(list.size() - choice);
					search(board);
					i -= 1;
					break;
				case 2:
					break;
				case 3:
					i -= 2;
					break;
				case 0:
					return View.ADMINMEMBER;
				}
			}
		}
		return View.AMEMBERALL;
	}

	public int aMemberSearch() {
		System.out.println("===================== 회원 검색 ===================");
		System.out.print("(1)아이디 검색 (2)이름 검색 (0)뒤로 가기 >");
		int result = ScanUtil.nextInt();
		switch (result) {
		case 1:
			return View.IDSEARCH;
		case 2:
			return View.NAMESEARCH;
		case 0:
			return View.ADMINMEMBER;
		}
		return View.AMEMBERSEARCH;
	}

	public int idSearch() {
		System.out.println("===================== 회원 검색 ===================");
		System.out.print("검색할 아이디: ");
		String id = ScanUtil.nextLine();
		List<Map<String, Object>> list = adminDao.idSearch(id);
		System.out.println("번호\t아이디\t이름\t생년월일");
		for (int i = 0; i < list.size(); i++) {
			System.out.print(i+1 + "\t");
			System.out.print(list.get(i).get("ID") + "\t");
			System.out.print(list.get(i).get("NAME") + "\t");
			System.out.println(list.get(i).get("BIRTH") + "\t");
		}
		System.out.print("(1)조회 (0)뒤로가기 >");
		int result = ScanUtil.nextInt();
		switch (result) {
		case 1:
			System.out.print("몇번을 조회하시겠습니까? >");
			int choice = ScanUtil.nextInt() - 1;
			Map<String, Object> board = list.get(choice);
			search(board);
			return View.AMEMBERSEARCH;
		case 0:
			return View.AMEMBERSEARCH;
		}
		return View.IDSEARCH;
	}

	public int nameSearch() {
		System.out.println("===================== 회원 검색 ===================");
		System.out.println("검색할 이름: ");
		String name = ScanUtil.nextLine();
		List<Map<String, Object>> list = adminDao.nameSearch(name);
		System.out.println("번호\t아이디\t이름\t생년월일");
		for (int i = 0; i < list.size(); i++) {
			System.out.print(i+1 + "\t");
			System.out.print(list.get(i).get("ID") + "\t");
			System.out.print(list.get(i).get("NAME") + "\t");
			System.out.println(list.get(i).get("BIRTH") + "\t");
		}
		System.out.print("(1)조회 (0)뒤로가기 >");
		int result = ScanUtil.nextInt();
		switch (result) {
		case 1:
			System.out.print("몇번을 조회하시겠습니까? >");
			int choice = ScanUtil.nextInt() - 1;
			Map<String, Object> board = list.get(choice);
			search(board);
			return View.AMEMBERSEARCH;
		case 0:
			return View.AMEMBERSEARCH;
		}
		return View.NAMESEARCH;
	}

	public void search(Map<String, Object> board) {
		System.out.print("아이디: ");
		System.out.println(board.get("ID"));
		System.out.print("비밀번호: ");
		System.out.println(board.get("PWD"));
		System.out.print("이름: ");
		System.out.println(board.get("NAME"));
		System.out.print("이메일: ");
		System.out.println(board.get("EMAIL"));
		System.out.print("생년월일: ");
		System.out.println(board.get("BIRTH"));
		System.out.print("주소: ");
		System.out.println(board.get("ADDR"));
		System.out.print("가입일: ");
		System.out.println(board.get("JOINDATE"));
		System.out.print("남은 쿠폰 개수: ");
		System.out.println(board.get("COU_REST"));
		System.out.print("구독권 : "); // 구독권 이름과 남은 구독일도 조회가능하게
		System.out.println(board.get("P_NAME"));
		System.out.print("(0) 뒤로가기 > ");
		ScanUtil.nextInt();
	}

	public int adminIncome() {
		System.out.print("(1)기간 조회 (0)뒤로가기 > ");
		int result = ScanUtil.nextInt();
		switch (result) {
		case 1:
			return View.DAYINCOME;
		case 0:
			return View.ADMINMAIN;
		}
		return View.ADMININCOME;
	}

	public int dayIncome() {
		System.out.print("(1) 조회 (0) 뒤로가기> ");
		int result = ScanUtil.nextInt();
		switch (result) {
		case 1:
			System.out.println("조회할 기간을 입력해주세요.(ex, 20210203 - 20210302) ");// 정규식 표현 추가
			System.out.print("조회 시작일: ");
			String start = ScanUtil.nextLine();
			System.out.print("조회 종료일: ");
			String end = ScanUtil.nextLine();
			List<Map<String, Object>> list = adminDao.dayIncomeM(start, end);
			List<Map<String, Object>> list1 = adminDao.dayIncomeS(start, end);
			List<Map<String, Object>> list2 = adminDao.dayIncomeC(start, end);
			for (int i = 0; i < list1.size(); i++) {
				list.add(list1.get(i));
			}
			for (int i = 0; i < list2.size(); i++) {
				list.add(list2.get(i));
			}
			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j < list.size(); j++) {
					if (Integer.parseInt(String.valueOf(list.get(i).get("MDATE"))) > Integer
							.parseInt(String.valueOf(list.get(j).get("MDATE")))) {
						list.set(j, list.set(i, list.get(j)));
					}
				}
			}
			System.out.println("구매자\t구매내역\t\t구매액\t구매일시");
			for (int i = 0; i < list.size(); i++) {
				System.out.print(list.get(i).get("ID") + "\t");
				System.out.printf("%-15s", list.get(i).get("CONTENT"));
				System.out.print(list.get(i).get("INCOME") + "\t");
				System.out.println(list.get(i).get("MDATE"));
			}
			int total = 0;
			for (int i = 0; i < list.size(); i++) {
				total += Integer.parseInt(String.valueOf(list.get(i).get("INCOME")));
			}
			System.out.println("수입 합계: " + total+"원");
			System.out.print("(0)뒤로가기 > ");
			ScanUtil.nextInt();
			
		}
		return View.ADMININCOME;
	}

	public int adminMusic() {
		List<Map<String, Object>> list = adminDao.adminMselect();
		int ten = list.size() / 10;
		int rest = 0;
		int count = 0;
		for (int i = 0; i < ten + 1; i++) {
			if (i < ten) {
				count = 10;
			} else {
				count = 0;
				rest = list.size() % 10;
			}
			System.out.println("곡번호\t곡명\t\t\t가수\t\t발매일");

			for (int j = i * 10; j < (count + (10 * i)) + rest; j++) {
				System.out.print(list.get(j).get("M_NO") + "\t");
				System.out.printf("%-25s", list.get(j).get("M_NAME"));
				System.out.printf("%-15s", list.get(j).get("M_SINGER"));
				System.out.print(list.get(j).get("M_DATE") + "\n");
			}
			if (i == 0) {
				System.out.print("(1)음악 상세 조회 (2)다음 페이지 (3)음악 등록 (0)뒤로가기 >");

				int result = ScanUtil.nextInt();
				switch (result) {
				case 1:
					detailselect(list);
					return View.ADMINMSELECT;
				case 2:
					break;
				case 3:
					return View.ADMINMADD;
				case 0:
					return View.ADMINMAIN;
				}
			} else if (i == ten) {
				System.out.print("(1)음악 상세 조회 (2)이전 페이지 (3)음악 등록 (0)뒤로가기 >");
				int result = ScanUtil.nextInt();
				switch (result) {
				case 1:
					detailselect(list);
					return View.ADMINMSELECT;
				case 2:
					i -= 2;
					break;
				case 3:
					return View.ADMINMADD;
				case 0:
					return View.ADMINMAIN;
				}
			} else {

				System.out.print("(1)음악 상세 조회 (2)다음 페이지 (3)이전 페이지 (4)음악 등록 (0)뒤로가기 >");
				int result = ScanUtil.nextInt();
				switch (result) {
				case 1:
					detailselect(list);
					return View.ADMINMSELECT;
				case 2:
					break;
				case 3:
					i -= 2;
					break;
				case 4:
					return View.ADMINMADD;
				case 0:
					return View.ADMINMAIN;
				}

			}
		}
		return View.ADMINMUSIC;
	}

	public void detailselect(List<Map<String, Object>> list) {

		System.out.print("몇번을 조회하시겠습니까? > ");
		int choice = ScanUtil.nextInt();
		int choice1 = 0;
		for (int k = 0; k < list.size(); k++) {
			if (choice == Integer.parseInt(String.valueOf(list.get(k).get("M_NO")))) {
				choice1 = k;
				break;
			}
		}
		musicinfo = list.get(choice1);
	}

	public int adminMselect() {
		Map<String, Object> board = musicinfo;
		System.out.println("=============음악 상세 조회 ============");
		System.out.println("NO: " + board.get("M_NO"));
		System.out.println("곡명: " + board.get("M_NAME"));
		System.out.println("가수: " + board.get("M_SINGER"));
		System.out.println("발매일: " + board.get("M_DATE"));
		System.out.println("곡주소: " + board.get("M_ADDR"));
		System.out.println("장르코드: " + board.get("G_CO"));
		System.out.println("감정코드: " + board.get("F_NO"));
		System.out.println("선호 나이대: " + board.get("M_AGE") + "0대");
		System.out.println("플레이 수 :" + board.get("M_COUNT"));
		System.out.println("(1) 수정 (0)뒤로가기");
		int result = ScanUtil.nextInt();
		switch (result) {
		case 1:
			return View.ADMINMMOD;
		case 0:
			return View.ADMINMUSIC;
		}
		return View.ADMINMSELECT;

	}

	public int adminMmod() {
		List<Object> param = new ArrayList<Object>();
		System.out.print("곡 번호: ");
		param.add(ScanUtil.nextInt());
		System.out.print("곡 명: ");
		param.add(ScanUtil.nextLine());
		System.out.print("가수: ");
		param.add(ScanUtil.nextLine());
		System.out.print("가사 : ");
		param.add(ScanUtil.nextLine());
		System.out.print("발매일: ");
		param.add(ScanUtil.nextLine());
		System.out.print("곡 위치: ");
		param.add(ScanUtil.nextLine());
		System.out.print("장르 코드: ");
		param.add(ScanUtil.nextInt());
		System.out.print("분위기 코드: ");
		param.add(ScanUtil.nextInt());
		System.out.print("선호 나이대: ");
		param.add(ScanUtil.nextInt());
		param.add(Integer.parseInt(String.valueOf(musicinfo.get("M_NO"))));
		int result = adminDao.adminmmod(param);
		if (result > 0) {
			System.out.println("[등록이 완료되었습니다]");
		} else {
			System.out.println("[등록에 실패하였습니다]");
		}
		return View.ADMINMUSIC;
	}

	public int adminMadd() {
		List<Object> param = new ArrayList<Object>();
		System.out.print("곡 번호: ");
		param.add(ScanUtil.nextInt());
		System.out.print("곡 명: ");
		param.add(ScanUtil.nextLine());
		System.out.print("가수: ");
		param.add(ScanUtil.nextLine());
		System.out.print("가사 : ");
		param.add(ScanUtil.nextLine());
		System.out.print("발매일: ");
		param.add(ScanUtil.nextLine());
		System.out.print("곡 위치: ");
		param.add(ScanUtil.nextLine());
		System.out.print("장르 코드: ");
		param.add(ScanUtil.nextInt());
		System.out.print("분위기 코드: ");
		param.add(ScanUtil.nextInt());
		System.out.print("선호 나이대: ");
		param.add(ScanUtil.nextInt());
		int result = adminDao.adminmadd(param);
		if (result > 0) {
			System.out.println("[등록이 완료되었습니다]");
		} else {
			System.out.println("[등록에 실패하였습니다]");
		}
		return View.ADMINMUSIC;
	}

	public int admincou() {
		List<Map<String, Object>> list = adminDao.admincou();
		for (int i = 0; i < list.size(); i++) {
			System.out.print(list.get(i).get("COU_NO") + "\t");
			System.out.print(list.get(i).get("COU_NAME") + "\t");
			System.out.print(list.get(i).get("COU_PRICE") + "\t");
			System.out.println(list.get(i).get("COU_SIZE"));
		}
		System.out.print("(1)조회 (2)쿠폰 등록 (0)뒤로가기 > ");
		int result = ScanUtil.nextInt();
		switch (result) {
		case 1:
			System.out.print("몇 번을 조회하시겠습니까? > ");
			int choice = ScanUtil.nextInt();
			int choice1 = 0;
			for (int k = 0; k < list.size(); k++) {
				if (choice == Integer.parseInt(String.valueOf(list.get(k).get("COU_NO")))) {
					choice1 = k;
					break;
				}
			}
			couinfo = list.get(choice1);
			return View.ADMINCOUSELECT;
		case 2:
			return View.ADMINCOUADD;
		case 3:
			return View.ADMINMAIN;
		}
		return View.ADMINCOU;
	}

	public int admincouselect() {
		Map<String, Object> board = couinfo;
		System.out.println("쿠폰 번호: " + board.get("COU_NO"));
		System.out.println("쿠폰명: " + board.get("COU_NAME"));
		System.out.println("쿠폰 가격: " + board.get("COU_PRICE"));
		System.out.println("쿠폰 내용: " + board.get("COU_SIZE") + "개");
		System.out.print("(1)수정 (0)뒤로가기 > ");
		int result = ScanUtil.nextInt();
		switch (result) {
		case 1:
			List<Object> param = new ArrayList<Object>();
			System.out.print("쿠폰 명: ");
			param.add(ScanUtil.nextLine());
			System.out.print("쿠폰 가격: ");
			param.add(ScanUtil.nextInt());
			System.out.print("쿠폰 내용: ");
			param.add(ScanUtil.nextInt());
			param.add(board.get("COU_NO"));
			result = adminDao.admincoumod(param);
			if (result > 0) {
				System.out.println("수정을 완료하였습니다.");
			} else {
				System.out.println("수정에 실패하였습니다.");
			}
			return View.ADMINCOU;
		case 0:
			return View.ADMINCOU;
		}
		return View.ADMINCOUSELECT;
	}

	public int admincouadd() {
		List<Object> param = new ArrayList<Object>();
		System.out.print("쿠폰 명: ");
		param.add(ScanUtil.nextLine());
		System.out.print("쿠폰 가격: ");
		param.add(ScanUtil.nextInt());
		System.out.print("쿠폰 내용: ");
		param.add(ScanUtil.nextInt());
		int result = adminDao.admincouadd(param);
		if (result > 0) {
			System.out.println("등록을 완료하였습니다.");
		} else {
			System.out.println("등록에 실패하였습니다.");
		}
		return View.ADMINCOU;
	}

	public int adminsub() {
		List<Map<String, Object>> list = adminDao.adminsub();
		for (int i = 0; i < list.size(); i++) {
			System.out.print(list.get(i).get("P_NO") + "\t");
			System.out.print(list.get(i).get("P_NAME") + "\t");
			System.out.print(list.get(i).get("P_COST") + "\t");
			System.out.println(list.get(i).get("P_PERIOD"));
		}
		System.out.print("(1)조회 (2)구독권 등록 (0)뒤로가기 > ");
		int result = ScanUtil.nextInt();
		switch (result) {
		case 1:
			System.out.print("몇 번을 조회하시겠습니까? > ");
			int choice = ScanUtil.nextInt();
			int choice1 = 0;
			for (int k = 0; k < list.size(); k++) {
				if (choice == Integer.parseInt(String.valueOf(list.get(k).get("P_NO")))) {
					choice1 = k;
					break;
				}
			}
			subinfo = list.get(choice1);
			return View.ADMINSUBSELECT;
		case 2:
			return View.ADMINSUBADD;
		case 0:
			return View.ADMINMAIN;
		}
		return View.ADMINSUB;
	}

	public int adminsubselect() {
		Map<String, Object> board = subinfo;
		System.out.println("쿠폰 번호: " + board.get("P_NO"));
		System.out.println("쿠폰명: " + board.get("P_NAME"));
		System.out.println("쿠폰 가격: " + board.get("P_COST"));
		System.out.println("쿠폰 내용: " + board.get("P_PERIOD") + "일");
		System.out.println("(1)수정 (0)뒤로가기 > ");
		int result = ScanUtil.nextInt();
		switch (result) {
		case 1:
			List<Object> param = new ArrayList<Object>();
			System.out.print("구독권 명: ");
			param.add(ScanUtil.nextLine());
			System.out.print("구독권 가격: ");
			param.add(ScanUtil.nextInt());
			System.out.print("구독권 기한: ");
			param.add(ScanUtil.nextInt());
			param.add(board.get("P_NO"));
			result = adminDao.adminsubmod(param);
			if (result > 0) {
				System.out.println("[수정을 완료하였습니다]");
			} else {
				System.out.println("[수정에 실패하였습니다]");
			}
			return View.ADMINSUB;
		case 0:
			return View.ADMINSUB;
		}
		return View.ADMINSUBSELECT;
	}

	public int adminsubadd() {
		List<Object> param = new ArrayList<Object>();
		System.out.print("구독권 명: ");
		param.add(ScanUtil.nextLine());
		System.out.print("구독권 가격: ");
		param.add(ScanUtil.nextInt());
		System.out.print("구독권 기한: ");
		param.add(ScanUtil.nextInt());
		int result = adminDao.adminsubadd(param);
		if (result > 0) {
			System.out.println("등록을 완료하였습니다.");
		} else {
			System.out.println("등록에 실패하였습니다.");
		}
		return View.ADMINSUB;
	}
}
