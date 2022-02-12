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
	public Map<String, Object> selectMember(String memId, String password){
		String sql = "SELECT ID "
				+ "		   , PWD"
				+ "	  FROM TB_MEMBER"
				+ "	  WHERE ID =? "
				+ "	  AND PWD =? ";
		List<Object> param = new ArrayList<Object>();
		param.add(memId);
		param.add(password);
	
		return JDBCUtil.selectOne(sql, param);
	}
	
}