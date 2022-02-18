package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class AdminDao {
	private AdminDao() {
		
	}
	private static AdminDao instance; 

	public static AdminDao getInstance() { 
		if (instance == null) {
			instance = new AdminDao();
		}
		return instance;
	}
	
	public List<Map<String, Object>> aMemberAll() {
		String sql = "SELECT A.ID, A.PWD,A.NAME"
				+ " ,A.EMAIL ,A.BIRTH, A.ADDR ,"
				+ " A.CARD_NO ,A.CARD_PWD, A.COU_REST,"
				+ " TO_CHAR(A.JOINDATE, 'YYYY/MM/DD HH24:MI') AS JOINDATE ,A.P_NO"
				+ " ,B.P_NAME "
				+ "FROM TB_MEMBER A, TB_SUBPROD B"
				+ " WHERE A.P_NO = B.P_NO "
				+ "ORDER BY JOINDATE DESC" ;
		return JDBCUtil.selectList(sql);
	}
	public List<Map<String, Object>> idSearch(String id){

		String sql = "SELECT A.ID, A.PWD,A.NAME"
				+ " ,A.EMAIL ,A.BIRTH, A.ADDR ,"
				+ " A.CARD_NO ,A.CARD_PWD, A.COU_REST,"
				+ " TO_CHAR(A.JOINDATE, 'YYYY/MM/DD HH24:MI') AS JOINDATE ,A.P_NO"
				+ " ,B.P_NAME "
				+ "FROM TB_MEMBER A, TB_SUBPROD B"
				+ " WHERE A.P_NO = B.P_NO "
				+ " AND A.ID LIKE ?";
		List<Object> param = new ArrayList<Object>();
		param.add("%"+id+"%");
		return JDBCUtil.selectList(sql, param);
	}
	public List<Map<String, Object>> nameSearch(String name){

		String sql = "SELECT A.ID, A.PWD,A.NAME"
				+ " ,A.EMAIL ,A.BIRTH, A.ADDR ,"
				+ " A.CARD_NO ,A.CARD_PWD, A.COU_REST,"
				+ " TO_CHAR(A.JOINDATE, 'YYYY/MM/DD HH24:MI') AS JOINDATE ,A.P_NO"
				+ " ,B.P_NAME "
				+ "FROM TB_MEMBER A, TB_SUBPROD B"
				+ " WHERE A.P_NO = B.P_NO "
				+ " AND A.NAME LIKE ?";
		List<Object> param = new ArrayList<Object>();
		param.add("%"+name+"%");
		return JDBCUtil.selectList(sql, param);
	}
	public List<Map<String, Object>> dayIncomeM(String start, String end){
		String sql ="SELECT A.ID,"
				+ "         A.MUS_TOTAL AS INCOME , "
				+ "			SUBSTR(B.M_NAME, 1, 15) AS CONTENT, "
				+ "			TO_CHAR(A.MUS_DATE,'YYYYMMDD') AS MDATE"
				+ " FROM TB_ORDER_MUSIC A, TB_MUSIC B "
				+ " WHERE A.M_NO = B.M_NO  "
				+ "   AND A.MUS_DATE BETWEEN ? AND ?  "; 
		List<Object> param = new ArrayList<Object>();
		param.add(start);
		param.add(end);
	 return	JDBCUtil.selectList(sql, param);
	}
	public List<Map<String, Object>> dayIncomeS(String start, String end){
		String sql ="SELECT A.ID,"
				+ "         B.P_NAME AS CONTENT,  "
				+ "			A.SUB_TOTAL AS INCOME, "
				+ "			TO_CHAR(A.SUB_DATE,'YYYYMMDD') AS MDATE"
				+ " FROM TB_ORDER_SUB A, TB_SUBPROD B "
				+ " WHERE A.P_NO = B.P_NO "
				+ "   AND A.SUB_DATE BETWEEN ? AND ?  "; 
		List<Object> param = new ArrayList<Object>();
		param.add(start);
		param.add(end);
	 return	JDBCUtil.selectList(sql, param);
	}
	public List<Map<String, Object>> dayIncomeC(String start, String end){
		String sql ="SELECT A.ID,"
				+ "         A.COU_TOTAL AS INCOME, "
				+ "			B.COU_NAME AS CONTENT, "
				+ "			TO_CHAR(A.COU_DATE,'YYYYMMDD') AS MDATE"
				+ " FROM TB_ORDER_COU A, TB_COUPON B"
				+ " WHERE A.COU_NO = B.COU_NO "
				+ "  AND A.COU_DATE BETWEEN ? AND ?  "; 
		List<Object> param = new ArrayList<Object>();
		param.add(start);
		param.add(end);
	 return	JDBCUtil.selectList(sql, param);
	}
	public List<Map<String, Object>> adminMselect(){
		String sql = "SELECT M_NO"
				+ " 		,SUBSTR(M_NAME,1, 15) AS M_NAME "
				+ "			,SUBSTR(M_SINGER,1,15) AS M_SINGER"
				+ "			,M_LYRICS "
				+ "			,TO_CHAR(M_DATE, 'YYYY/MM/DD') as M_DATE"
				+ " 		,M_ADDR"
				+ "			,G_CO"
				+ "			,F_NO"
				+ "			,M_AGE"
				+ "			,M_COUNT "
				+ "FROM TB_MUSIC ORDER BY M_NO DESC";
		return JDBCUtil.selectList(sql);
	}
	public int adminmadd(List<Object> param) {
		String sql = "INSERT INTO TB_MUSIC VALUES(?,?,?,?,?,?,?,?,?, 0)";
		return JDBCUtil.update(sql, param);
		
	}
	public int adminmmod(List<Object> param) {
		String sql = "UPDATE TB_MUSIC SET M_NO = ?, M_NAME = ?,  "
				+ "	 M_SINGER = ?, M_LYRICS = ?, M_DATE = ?, M_ADDR =?, G_CO = ? "
				+ "  ,F_NO = ?, M_AGE = ? "
				+ "  WHERE M_ID = ?  ";
		return JDBCUtil.update(sql, param);
	}
	public List<Map<String, Object>> admincou() {
		String sql = "SELECT * FROM TB_COUPON";
		return JDBCUtil.selectList(sql);
	}
	public int admincoumod(List<Object> param) {
		String sql = "UPDATE TB_COUPON SET COU_NAME = ?, "
				+ "						   COU_PRICE = ?,"
				+ "						   COU_SIZE = ?"
				+ "				WHERE COU_NO = ?	";
		
		return JDBCUtil.update(sql, param);
	}
	public int admincouadd(List<Object> param) {
		String sql = "INSERT INTO TB_COUPON VALUES("
				+ "(SELECT NVL(MAX(COU_NO), 0) + 1 FROM TB_COUPON),?,?,? ) ";
		return JDBCUtil.update(sql, param);
	}
	public List<Map<String, Object>> adminsub(){
		String sql = "SELECT * FROM TB_SUBPROD";
		return JDBCUtil.selectList(sql);
	}
	public int adminsubmod(List<Object> param) {
		String sql = "UPDATE TB_SUBPROD SET P_NAME = ?, "
				+ "						   P_COST = ?,"
				+ "						   P_PERIOD = ?"
				+ "				WHERE P_NO = ?	";
		
		return JDBCUtil.update(sql, param);
	}
	public int adminsubadd(List<Object> param) {
		String sql = "INSERT INTO TB_SUBPROD VALUES("
				+ "(SELECT NVL(MAX(P_NO), 0) + 1 FROM TB_SUBPROD),?,?,? ) ";
		return JDBCUtil.update(sql, param);
	}
}
