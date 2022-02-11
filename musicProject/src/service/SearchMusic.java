package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dao.PlayMusicDao;
import util.JDBCUtil;
import util.ScanUtil;


public class SearchMusic {

private SearchMusic() {
		
	}
	private static SearchMusic instance; 

	public static SearchMusic getInstance() { 
		if (instance == null) {
			instance = new SearchMusic();
		}
		return instance;
	}
	private PlayMusicDao palymusicDao = PlayMusicDao.getInstance();
	public int searchMusic() {
		String sql = "SELECT * FROM MUSIC   WHERE M_NAME = ? OR  M_SINGER = ? OR M_NAME LIKE ?  "
				+ "OR M_NAME LIKE ? OR M_NAME LIKE ? OR  M_SINGER LIKE ? OR  M_SINGER LIKE ? OR  M_SINGER LIKE ? ";
		List<Object> param = new ArrayList<Object>();
		System.out.print("검색 > ");
		String find = ScanUtil.nextLine();
		param.add(find);
		param.add(find);
		param.add("%" + find);
		param.add(find + "%");
		param.add("%" + find + "%");
		param.add("%" + find);
		param.add(find + "%");
		param.add("%" + find + "%");
		try {
		List<Map<String, Object>> list = JDBCUtil.selectList(sql, param);
		System.out.println("번호\t곡목\t가수");
		for (int i = 0; i < list.size(); i++) {
			System.out.print((i + 1) + "\t");
			System.out.println(list.get(i).get("M_NAME") + "\t" + list.get(i).get("M_SINGER"));
		}
		System.out.print("(1) 곡 선택 (2) 뒤로가기 >");
		int result = ScanUtil.nextInt();
		switch(result){
		case 1 : System.out.print("곡을 선택해주세요 >");
		 result = ScanUtil.nextInt();
		String fmusic = String.valueOf(list.get(result - 1).get("M_ADDR"));
		
		
		}
		}catch(IndexOutOfBoundsException e) {
			System.out.println("검색 결과가 없습니다.");}
		return 0;
		
		
		
		}
		
	}
	
