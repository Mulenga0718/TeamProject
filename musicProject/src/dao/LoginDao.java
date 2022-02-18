package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class LoginDao {
private LoginDao() {
		
	}
	private static LoginDao instance; 

	public static LoginDao getInstance() { 
		if (instance == null) {
			instance = new LoginDao();
		}
		return instance;
	}
	public int insertMember(Map<String, Object>param) {
		String sql = "INSERT INTO TB_MEMBER "
				+ "   VALUES(?, ?, ?, ?, "
				+ "         TO_CHAR(TO_DATE(?, 'YYYY/MM/DD'), 'YYYY/MM/DD'),"
				+ "         ?, ?, ?,"	
				+ "         0,"
				+ "         SYSDATE,"
				+ "         0)";
		List<Object> _param = new ArrayList<Object>();
		_param.add(param.get("ID"));
		_param.add(param.get("PWD"));
		_param.add(param.get("NAME"));
		_param.add(param.get("EMAIL"));
		_param.add(param.get("BIRTH"));
		_param.add(param.get("ADDR"));
		_param.add(param.get("CARD_NO"));
		_param.add(param.get("CARD_PWD"));
		
		return JDBCUtil.update(sql, _param);
	}
	
	public Map<String, Object> selectMember(String memId, String password){
		String sql = "SELECT ID, "
				+ "		     PWD,"
				+ "          COU_REST,"
				+ "          CARD_PWD,"
				+ "			 P_NO"
				+ "	  FROM TB_MEMBER"
				+ "	  WHERE ID =? "
				+ "	  AND PWD =?";
		List<Object> param = new ArrayList<Object>();
		param.add(memId);
		param.add(password);
		return JDBCUtil.selectOne(sql, param);
	}
	
	public List<Map<String, Object>> duplicationId(String memId){
		String sql = "SELECT ID"
				+ "     FROM TB_MEMBER";
		List<Object> param = new ArrayList<Object>();	
		return JDBCUtil.selectList(sql, param);
	}
}





