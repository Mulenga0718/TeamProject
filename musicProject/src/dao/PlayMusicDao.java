package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class PlayMusicDao {
	private PlayMusicDao() {
		
	}
	private static PlayMusicDao instance; 

	public static PlayMusicDao getInstance() { 
		if (instance == null) {
			instance = new PlayMusicDao();
		}
		return instance;
	}
	
	public int pluscount(String fmusic){
		String sql = " UPDATE MUSIC SET M_COUNT = M_COUNT +1 WHERE M_ADDR = ?";
		List<Object> param = new ArrayList<Object>();
		param.add(fmusic);
		return JDBCUtil.update(sql, param);
	}
	public List<Map<String, Object>> search(List<Object> param){
		String sql = "SELECT * FROM MUSIC   WHERE M_NAME = ? OR  M_SINGER = ? OR M_NAME LIKE ?  "
				+ "OR M_NAME LIKE ? OR M_NAME LIKE ? OR  M_SINGER LIKE ? OR  M_SINGER LIKE ? OR  M_SINGER LIKE ? ";
		param = new ArrayList<Object>();
		System.out.print("검색 > ");
		String find = util.ScanUtil.nextLine();
		param.add(find);
		param.add(find);
		param.add("%" + find);
		param.add(find + "%");
		param.add("%" + find + "%");
		param.add("%" + find);
		param.add(find + "%");
		param.add("%" + find + "%");
		List<Map<String, Object>> list = JDBCUtil.selectList(sql, param);
		
		return null;
		
	}
	
}
