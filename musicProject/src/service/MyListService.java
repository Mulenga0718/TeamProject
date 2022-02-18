package service;

import java.util.List;
import java.util.Map;

import dao.MusicDao;
import dao.MyListDao;
import util.MusicPlayMethod;
import util.MyListPlayMethod;

public class MyListService {

	
	private MyListPlayMethod myListPlayMethod = MyListPlayMethod.getInstance();
	
	private MyListService() {
	}

	private static MyListService instance;

	public static MyListService getInstance() {
		if (instance == null) {
			instance = new MyListService();
		}
		return instance;
	}
	
	private MyListDao myListDao = MyListDao.getInstance(); // 객체 생성
	private MusicPlayMethod musicPlayMethod = MusicPlayMethod.getInstance();// 객체 생성
	
	public void addMyList() { // 내 음악에 넣기
		
		if (Login.loginMember == null) {
			System.out.println("[비회원은 플레이리스트 추가 불가]");
		} else {
			int result = myListDao.addList(); // 실행
			if (result > 0) {
				System.out.println("[내 음악에 추가되었습니다]");
			} else {
				System.out.println("[이미 내 음악에 존재합니다]");
			}
		}
	}

	public int myList() { // 내 음악 출력 
		
		MusicService.mlist = myListDao.selectMyList();
		int result = myListPlayMethod.myPlayMethod();
		return result;
	}

}
