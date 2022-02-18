package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import service.Login;
import service.MusicService;
import service.MyListService;
import util.JDBCUtil;

public class MyListDao {

	private MyListDao() {
	}

	private static MyListDao instance;

	public static MyListDao getInstance() {
		if (instance == null) {
			instance = new MyListDao();
		}
		return instance;
	}

	public int addList() { // 내 음악에 추가하기
		String sql = "INSERT INTO TB_LIST (ID,M_NO,L_DATE)" + " SELECT ?,?,SYSDATE FROM DUAL A" + " WHERE NOT EXISTS("
				+ " SELECT 0" + " FROM TB_LIST" + " WHERE ID =?" + " AND M_NO =?" + " )";
		String sql2 = "SELECT M_NO FROM TB_MUSIC WHERE M_ADDR = ?";

		List<Object> param2 = new ArrayList<Object>();
		param2.add(MusicService.currentmus.get("M_ADDR"));
		Map<String, Object> map = JDBCUtil.selectOne(sql2, param2);

		List<Object> param = new ArrayList<Object>();
		param.add(Login.loginMember.get("ID"));
		param.add(map.get("M_NO"));
		param.add(Login.loginMember.get("ID"));
		param.add(map.get("M_NO"));
		return JDBCUtil.update(sql, param);
	}

	//내 음악 리스트 셀렉
	public List<Map<String, Object>> selectMyList() { // 아이디가 00인 사람의 플레이리스트에서
		// 곡명, 가수 뽑아서 플레이 뮤직
		String sql = "SELECT * FROM TB_MUSIC  WHERE M_NO IN (SELECT M_NO FROM TB_LIST WHERE ID =?) ";
		List<Object> param = new ArrayList<Object>();
		param.add(Login.loginMember.get("ID"));
		return JDBCUtil.selectList(sql, param);
	}

	public List<Map<String, Object>> listMixName() { //이름별로 섞기
		String sql = "SELECT * FROM TB_MUSIC WHERE M_NO IN (SELECT M_NO FROM TB_LIST WHERE ID = ?) ORDER BY M_NAME ";
	List<Object> param = new ArrayList<Object>();
	param.add(Login.loginMember.get("ID"));
		return	JDBCUtil.selectList(sql,param);
	}

	public List<Map<String, Object>> listMixDate() { //등록별로 섞기
		String sql = "SELECT * FROM TB_MUSIC WHERE M_NO IN (SELECT M_NO FROM TB_LIST WHERE ID = ?) ORDER BY M_SINGER";
		List<Object> param = new ArrayList<Object>();
		param.add(Login.loginMember.get("ID"));
		
		return JDBCUtil.selectList(sql,param);
	}

	public List<Map<String, Object>> listMixSinger() { //가수별로 섞기
		String sql = "SELECT * FROM TB_MUSIC WHERE M_NO IN (SELECT M_NO FROM TB_LIST WHERE ID = ?) ORDER BY M_SINGER";
		List<Object> param = new ArrayList<Object>();
		param.add(Login.loginMember.get("ID"));
		return JDBCUtil.selectList(sql,param);
		
	}
	public List<Map<String, Object>> selectBuyMusic() {
		// 곡명, 가수 뽑아서 플레이 뮤직
				String sql = "SELECT * FROM TB_MUSIC  WHERE M_NO IN (SELECT M_NO FROM TB_ORDER_MUSIC WHERE ID =?) ";
				List<Object> param = new ArrayList<Object>();
				param.add(Login.loginMember.get("ID"));
				return JDBCUtil.selectList(sql, param);
					
	}


}
