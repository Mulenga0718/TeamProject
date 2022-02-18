package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class MypageDao {
private MypageDao() {
		
	}
	private static MypageDao instance; 

	public static MypageDao getInstance() { 
		if (instance == null) {
			instance = new MypageDao();
		}
		return instance;
	}
	
	public List<Map<String, Object>> memberInfo(Map<String, Object>param){
		String sql = "SELECT A.ID,"
				+ "          LPAD('*',LENGTH(A.PWD),'*') AS PWD," //비밀번호의 맨 오른쪽값을 *로하고 나머지 왼쪽의 값들도 *로 바꿈
				+ "          A.NAME,"
				+ "          A.EMAIL,"
				+ "          A.BIRTH,"
				+ "          A.ADDR,"
				+ "          A.CARD_NO,"
				+ "          LPAD('*',LENGTH(A.CARD_PWD),'*') AS CARD_PWD,"
				+ "          A.COU_REST,"
				+ "          TO_CHAR(A.JOINDATE, 'YYYY/MM/DD HH24:MI') AS JOINDATE,"
				+ "          B.P_NAME"
				+ "    FROM TB_MEMBER A, TB_SUBPROD B"
				+ "   WHERE A.P_NO = B.P_NO"
				+ "     AND ID = ?";
		List<Object> _param = new ArrayList<Object>();
		_param.add(param.get("ID"));
		
		return JDBCUtil.selectList(sql, _param);
	}
	
	public int updatePwdInfo(Map<String, Object>param) {
		String sql = "UPDATE TB_MEMBER"
				+ "      SET PWD = ?"
				+ "    WHERE ID = ?";
		List<Object> _param = new ArrayList<Object>();
		_param.add(param.get("PWD"));
		_param.add(param.get("ID"));
		
		return JDBCUtil.update(sql, _param);
	}
		
	public int updateAddrInfo(Map<String, Object>param) {
		String sql = "UPDATE TB_MEMBER"
				+ "      SET ADDR = ?"
				+ "    WHERE ID = ?";
		List<Object> _param = new ArrayList<Object>();
		_param.add(param.get("ADDR"));
		_param.add(param.get("ID"));
		
		
		return JDBCUtil.update(sql, _param);
		
	}

	
	public int updateCardNoInfo(Map<String, Object>param) {
		String sql = "UPDATE TB_MEMBER"
				+ "      SET CARD_NO = ?"
				+ "    WHERE ID = ?";
		List<Object> _param = new ArrayList<Object>();
		_param.add(param.get("CARD_NO"));
		_param.add(param.get("ID"));
		
		
		return JDBCUtil.update(sql, _param);
		
	}
	}

