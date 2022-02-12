package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import service.Login;
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
		String sql = "INSERT INTO TB_QUESTION(Q_NO, ID,  Q_NAME, Q_CONTENT) "
				+ "VALUES((SELECT NVL(MAX(Q_NO), 0) + 1 FROM TB_QUESTION), ?,?,?) ";
		List<Object> param = new ArrayList<Object>();
		param.add((String)Login.loginMember.get("ID"));
		param.add(title);
		param.add(content);
		
		return JDBCUtil.update(sql, param); 
	}
	public Map<String, Object> selectQuestion(String id){
		String sql = "select Q_NO,"
				+ "          ID,"
				+ "   		 Q_NAME,"
				+ " 		 Q_CONTENT,"
				+ "			 TO_CHAR(Q_DATE, 'YYYY-MM-DD HH24:MI') AS QUESTIONDATE"
				+ " from TB_QUESTION WHERE ID = ?";
		List<Object> param = new ArrayList<Object>();
		param.add((String)Login.loginMember.get("ID")); //아이디
		return JDBCUtil.selectOne(sql, param);
	}
	public Map<String, Object> selectAnswer(String id){
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
		param.add((String)Login.loginMember.get("ID"));
		
		return JDBCUtil.selectOne(sql, param);
	}
	public List<Map<String, Object>> qustiononeadmin(){
		String sql = "select Q_NO "
				+ "			 ,ID"
				+ "			 ,Q_NAME"
				+"			 ,Q_CONTENT"
				+ "			 ,TO_CHAR(Q_DATE, 'YYYY_MM_DD HH24:MI') AS QDATE	"
				+ " from TB_QUESTION ORDER BY Q_NO DESC";
		return JDBCUtil.selectList(sql);
		
	}
	public int qadminanswer(List<Object> param) {
		String sql = "INSERT INTO TB_ANSWER(Q_NO, Q_ANSWER) "
				+ "					   VALUES(?, ?)  ";
		
		return JDBCUtil.update(sql, param);
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
	public int noticeadd(String title, String content) {
		String sql = "insert into TB_BOARD (ARTICLENO, TITLE, CONTENT)"
				+ "				  VALUES((SELECT NVL(MAX(ARTICLENO), 0) + 1 FROM TB_BOARD),?,?) ";
		List<Object> param = new ArrayList<Object>();
		param.add(title);
		param.add(content);
		return JDBCUtil.update(sql, param);
		
	}
	public int faqadd(String title, String content) {
		String sql = "insert into TB_FAQ "
				+ "				  VALUES((SELECT NVL(MAX(FQ_NO), 0) + 1 FROM TB_FAQ),?,?) ";
		List<Object> param = new ArrayList<Object>();
		param.add(title);
		param.add(content);
		return JDBCUtil.update(sql, param);
		
		
	}
}
