package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class BoardDao {
	private BoardDao() {
		
	}
	private static BoardDao instance; 

	public static BoardDao getInstance() { 
		if (instance == null) {
			instance = new BoardDao();
		}
		return instance;
	}
	public int addQuestion(String title, String content) {
		String sql = "INSERT INTO TB_QUESTION(Q_NO, ID,  Q_NAME, Q_CONTENT, Q_DATE) "
				+ "VALUES((SELECT NVL(MAX(Q_NO), 0) + 1 FROM TB_QUESTION), ?,?,?, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI')) ";
		List<Object> param = new ArrayList<Object>();
		param.add("admin");
		param.add(title);
		param.add(content);
		
		return JDBCUtil.update(sql, param); 
	}
	public Map<String, Object> selectQuestion(){
		String sql = "select Q_NO,"
				+ "          ID,"
				+ "   		 Q_NAME,"
				+ " 		 Q_CONTENT,"
				+ "			 TO_CHAR(Q_DATE, 'YYYY-MM-DD HH24:MI') AS QUESTIONDATE"
				+ " from TB_QUESTION WHERE ID = ?";
		List<Object> param = new ArrayList<Object>();
		param.add("admin"); //아이디
		return JDBCUtil.selectOne(sql, param);
	}
	public Map<String, Object> selectAnswer(){
		String sql = "select A.Q_NO "
				+ "			,A.ID "
				+ "			,A.Q_CONTENT"
				+ "			,A.Q_NAME"
				+ "			,TO_CHAR(A.Q_DATE, 'YYYY-MM-DD HH24:MI') "
				+ "			,B.Q_ANSWER"
				+ "			,TO_CHAR(B.Q_ADATE, 'YYYY-MM-DD HH24:MI') "
				+ "from TB_QUESTION A, TB_ANSWER B "
				+ "where A.Q_NO = B.Q_NO "
				+ "AND ID = ? ";
		List<Object> param = new ArrayList<Object>();
		param.add("admin");
		
		return JDBCUtil.selectOne(sql, param);
	}
	public List<Map<String, Object>> selectFAQ(){
		String sql = "select * from TB_FAQ ";
		return JDBCUtil.selectList(sql);
	}
	public List<Map<String, Object>> selectBoard(){
		String sql = "select ARTICLENO"
				+ "			,TITLE"
				+ "			,CONTENT"
				+ "			,TO_CHAR(WRITEDATE, 'YYYY-MM-DD') AS WRITEDATE	"
				+ " from TB_BOARD ";
		return JDBCUtil.selectList(sql);
	}
}
