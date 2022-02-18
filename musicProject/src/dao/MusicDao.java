package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import service.Login;
import service.MusicService;
import util.JDBCUtil;
import util.View;

public class MusicDao {  //데이터베이스 접속
private MusicDao() {
		
	}
	private static MusicDao instance;
	public static MusicDao getInstance() {
		if(instance == null) {
			instance = new MusicDao();
		
	}
		return instance;
}	
	
	
	//데이터베이스에서 음악 검색
	public List<Map<String,Object>> search(String searching) {
		String sql = "SELECT * FROM TB_MUSIC"
				+ " WHERE M_NAME LIKE ? "
				+ " OR M_SINGER LIKE ?";
      List<Object> param = new ArrayList<Object>();
      param.add("%"+searching+"%");
      param.add("%"+searching+"%");
      
      return JDBCUtil.selectList(sql, param);
	}
	
	public Map<String,Object> selectMusic(int input) {
		String sql = "SELECT M_ADDR FROM TB_MUSIC "
				+ " WHERE M_NO =? ";
		List<Object> param = new ArrayList<Object>();
		param.add(input);
		return JDBCUtil.selectOne(sql, param);
		
		}
	public List<Map<String, Object>> chart() {//조회수 순으로 인기차트 
	String sql = "SELECT * FROM (SELECT * FROM TB_MUSIC ORDER BY M_COUNT DESC)";
	   return JDBCUtil.selectList(sql);
	}
	public List<Map<String, Object>> recentSelect() {
		
	String sql = "SELECT * "
			+ " FROM (SELECT * FROM TB_MUSIC ORDER BY M_DATE DESC)";
	
	return JDBCUtil.selectList(sql);
	}

	public Map<String, Object> readLyrics() {
		String sql = "SELECT DBMS_LOB.SUBSTR(M_LYRICS ,DBMS_LOB.GETLENGTH(M_LYRICS),1) AS M_LYRICS FROM TB_MUSIC WHERE M_ADDR = ?";
		List<Object> param = new ArrayList<Object>();
		param.add(MusicService.currentmus.get("M_ADDR"));
		return JDBCUtil.selectOne(sql, param);
	}

	public List<Map<String,Object>> selectgen(int gen) {
		String sql = "SELECT * FROM TB_MUSIC WHERE G_CO = ?";
		List<Object> param = new ArrayList<Object>();
		param.add(gen);
		
		return JDBCUtil.selectList(sql, param);
		
	}

	public List<Map<String,Object>> selectfeel(int feel) {
		String sql = "SELECT * FROM TB_MUSIC WHERE F_NO =?";
		List<Object> param = new ArrayList<Object>();
		param.add(feel);
		return JDBCUtil.selectList(sql, param);
	}
	
	public int addcount() { //노래 재생하면 조회  올라가기
		String sql = "UPDATE TB_MUSIC SET M_COUNT = NVL(M_COUNT,1) +1 WHERE M_ADDR = ? ";
		List<Object> param = new ArrayList<Object>();
		param.add(MusicService.currentmus.get("M_ADDR"));
		return JDBCUtil.update(sql, param);
	}
	public void addLike() { //좋아요 테이블
		if(Login.loginMember==null) {
			System.out.println("비회원은 ♥좋아요 불가");
		}
		if(Login.loginMember != null) {
		String sql = "INSERT INTO TB_LIKE ( ID, M_NO, M_LIKE ) "
				+ " SELECT ?,?,1 FROM DUAL A"
				+ " WHERE NOT EXISTS("
				+ " SELECT 0"
				+ " FROM TB_LIKE"
				+ " WHERE ID = ?"
				+ " AND M_NO = ?"
				+ " )";
		
		List<Object> param = new ArrayList<Object>();
		
		String sql2 = "SELECT M_NO FROM TB_MUSIC WHERE M_ADDR = ?";
		List<Object> param2 = new ArrayList<Object>();
		param2.add(MusicService.currentmus.get("M_ADDR"));
		Map<String, Object> map =JDBCUtil.selectOne(sql2, param2);
		
		param.add(Login.loginMember.get("ID"));
		param.add(Integer.parseInt(String.valueOf(map.get("M_NO"))));
		
		param.add(Login.loginMember.get("ID"));
		param.add(Integer.parseInt(String.valueOf(map.get("M_NO"))));
		int result = JDBCUtil.update(sql, param);	
		if(result<1) {
			System.out.println("이미 ♥좋아요를 누르셨습니다");
		}else if(result >0) {
			System.out.println("♥좋아요");
		}
	}	
	}
	
	public Map<String, Object> countlike(int no){
		String sql = "SELECT COUNT(*) as LIKECOUNT FROM TB_LIKE "
				+ " WHERE M_NO = ?"
				+ " GROUP BY M_NO ";
		List<Object> param = new ArrayList<Object>();
		param.add(no);
		return JDBCUtil.selectOne(sql, param);
		
	}
	

	public List<Map<String, Object>> musiccomment(){
		String sql = "SELECT A.M_CNO AS M_CNO, "
				+ "			 A.ID AS ID, "
				+ "			 A.M_COMMENT AS M_COMMENT"
				+ " FROM  TB_COMMENT A,"
				+ " TB_MUSIC B "
				+ "WHERE A.M_NO = B.M_NO "
				+ " AND M_ADDR = ? ";
		List<Object> param = new ArrayList<Object>();
		param.add(MusicService.currentmus.get("M_ADDR"));
	 return	JDBCUtil.selectList(sql, param);
	}
	public int commentadd(String comment) {
		String sql = "INSERT INTO TB_COMMENT(M_CNO, M_NO, ID, M_COMMENT)"
				+ " VALUES((SELECT NVL(MAX(M_CNO), 0) + 1 FROM TB_COMMENT),"
				+ " (SELECT M_NO FROM TB_MUSIC WHERE M_ADDR = ?),?,?)";
					
		List<Object> param = new ArrayList<Object>();
		param.add(MusicService.currentmus.get("M_ADDR"));
		param.add(Login.loginMember.get("ID"));
		param.add(comment);
		return JDBCUtil.update(sql, param);
	}
	public int commentdel(int no) {
	String sql = "DELETE FROM TB_COMMENT WHERE M_CNO = ?";
		List<Object> param = new ArrayList<Object>();
		param.add(no);
		return JDBCUtil.update(sql, param);
		
	}
	public Map<String,Object> subcheck(){
		String sql = "select TO_DATE(sub_expire,'YYYY-MM-DD') - TO_DATE(sysdate,'YYYY-MM-DD') AS SUBCHECK "
				+ " from tb_order_sub where id = ?"
				+ " UNION ALL"
				+ " SELECT NULL AS COLUMN_NAME"
				+ " FROM DUAL"
				+ " WHERE NOT EXISTS(select TO_DATE(sub_expire,'YYYY-MM-DD') - TO_DATE(sysdate,'YYYY-MM-DD') AS SUBCHECK "
				+ " from tb_order_sub where id = ?)";
		
		List<Object> param = new ArrayList<Object>();
		param.add(Login.loginMember.get("ID"));
		param.add(Login.loginMember.get("ID"));
		return JDBCUtil.selectOne(sql, param);
	}
	

	public Map<String,Object> musiccheck(){
		String sql = "select B.M_ADDR AS MUSICCHECK"
				+ "  FROM TB_ORDER_MUSIC A, TB_MUSIC B"
				+ " WHERE A.M_NO = B.M_NO AND A.ID = ? "
						+" UNION ALL "
						+ " SELECT NULL AS COLUMN_NAME"
						+ " FROM DUAL"
						+ " WHERE NOT EXISTS("
				+" select B.M_ADDR AS MUSICCHECK"
				+ "  FROM TB_ORDER_MUSIC A, TB_MUSIC B"
				+ " WHERE A.M_NO = B.M_NO AND A.ID = ? ) ";
		List<Object> param = new ArrayList<Object>();
		param.add(Login.loginMember.get("ID"));
		param.add(Login.loginMember.get("ID"));
		return JDBCUtil.selectOne(sql, param);		
	}
	public List<Map<String,Object>>  recomAge() { // case when <1이면 2   567 이면 4로 >
		String sql = "SELECT TO_NUMBER(SUBSTR(TO_CHAR(EXTRACT(YEAR FROM SYSDATE) - EXTRACT(YEAR FROM TO_DATE((SELECT BIRTH FROM TB_MEMBER WHERE ID = ? ),'YYYY-MM-DD'))),0,1)) AGE_T,"
				+ "    CASE WHEN TO_NUMBER(SUBSTR(TO_CHAR(EXTRACT(YEAR FROM SYSDATE) - EXTRACT(YEAR FROM TO_DATE((SELECT BIRTH FROM TB_MEMBER WHERE ID = ? ),'YYYY-MM-DD'))),0,1)) =1 THEN 2"
				+ "    WHEN TO_NUMBER(SUBSTR(TO_CHAR(EXTRACT(YEAR FROM SYSDATE) - EXTRACT(YEAR FROM TO_DATE((SELECT BIRTH FROM TB_MEMBER WHERE ID = ? ),'YYYY-MM-DD'))),0,1)) = 5 THEN 4"
				+ "    WHEN TO_NUMBER(SUBSTR(TO_CHAR(EXTRACT(YEAR FROM SYSDATE) - EXTRACT(YEAR FROM TO_DATE((SELECT BIRTH FROM TB_MEMBER WHERE ID = ? ),'YYYY-MM-DD'))),0,1)) = 6 THEN 4"
				+ "    WHEN TO_NUMBER(SUBSTR(TO_CHAR(EXTRACT(YEAR FROM SYSDATE) - EXTRACT(YEAR FROM TO_DATE((SELECT BIRTH FROM TB_MEMBER WHERE ID = ? ),'YYYY-MM-DD'))),0,1)) = 7 THEN 4"
				+ "    ELSE TO_NUMBER(SUBSTR(TO_CHAR(EXTRACT(YEAR FROM SYSDATE) - EXTRACT(YEAR FROM TO_DATE((SELECT BIRTH FROM TB_MEMBER WHERE ID =  ? ),'YYYY-MM-DD'))),0,1)) END AGE"
				+ "    FROM DUAL";
		List<Object> param = new ArrayList<Object>();
		param.add(Login.loginMember.get("ID"));
		param.add(Login.loginMember.get("ID"));
		param.add(Login.loginMember.get("ID"));
		param.add(Login.loginMember.get("ID"));
		param.add(Login.loginMember.get("ID"));
		param.add(Login.loginMember.get("ID"));
		Map<String, Object> map =JDBCUtil.selectOne(sql, param); 
		sql = "SELECT * FROM TB_MUSIC WHERE M_AGE = ? ";
		param = new ArrayList<Object>();
		param.add(map.get("AGE"));
		return JDBCUtil.selectList(sql, param);
	}
	public List<Map<String, Object>> recomGF() {
		String sql = "SELECT E.FCOUNT,"
				+ "       E.FNO,"
				+ "       F.GCOUNT,"
				+ "       F.GNO"
				+ " FROM (SELECT COUNT(*) AS FCOUNT, B.F_NO AS FNO FROM TB_LIST A, TB_MUSIC B"
				+ " WHERE A.M_NO = B.M_NO"
				+ "   AND A.ID = ? "
				+ "  GROUP BY B.F_NO ORDER BY 1 DESC ) E, "
				+ "  (SELECT COUNT(*) AS GCOUNT, D.G_CO AS GNO FROM TB_LIST C, TB_MUSIC D"
				+ " WHERE C.M_NO = D.M_NO "
				+ "   AND C.ID = ? "
				+ "  GROUP BY D.G_CO ORDER BY 1 DESC) F "
				+ "  WHERE ROWNUM = 1 ";
		List<Object> param = new ArrayList<Object>();
		param.add(Login.loginMember.get("ID"));
		param.add(Login.loginMember.get("ID"));
		Map<String, Object> map =JDBCUtil.selectOne(sql, param);
		Random ran = new Random();
		sql = "SELECT * FROM TB_MUSIC WHERE G_CO = ? OR F_NO = ? ";
		param = new ArrayList<Object>();
		try {
		param.add(map.get("GCOUNT"));
		param.add(map.get("FCOUNT"));}
		catch(Exception e) {
			param.add(ran.nextInt(4)+1);
			param.add(ran.nextInt(4)+1);
		}
		return JDBCUtil.selectList(sql, param);
	}
}