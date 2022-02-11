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
	System.out.println("게시판 코드\t제목\t\t작성일");
	if(list.size() !=0 ) {
	for(int i=0; i<list.size(); i++) {
		System.out.print(list.get(i).get("ARTICLENO")+"\t");
		System.out.print(list.get(i).get("TITLE")+"\t\t");
		System.out.print(list.get(i).get("DATE")+"\t");
	}
	System.out.print("(1)조회 (2)뒤로가기 > ");
	int result = ScanUtil.nextInt();
	switch(result) {
	case 1: return View.NOTICECONTENT;
	case 2: return View.Home;
	}}else {
		System.out.println("등록된 글이 없습니다.");
		
	}
	
	 return View.Home;
}
public int noticecontent() {
	List<Map<String, Object>> list = boardDao.selectBoard();
	
	System.out.print("몇번을 조회하시겠습니까? ");
	int result = ScanUtil.nextInt();
	System.out.println("게시판 코드: "+ list.get(result).get("ARTICLENO"));
	System.out.println("게시판 코드: "+ list.get(result).get("TITLE"));
	System.out.println("게시판 코드: "+ list.get(result).get("CONTENT"));
	System.out.println("게시판 코드: "+ list.get(result).get("WRITEDATE"));
	
	System.out.println("(1)뒤로가기 (2)홈화면으로 ");
	result =  ScanUtil.nextInt();
	switch(result) {
	case 1: return View.NOTICEBOARD;
	case 2: return View.Home;
 
	}
	return View.NOTICECONTENT;
}
}
