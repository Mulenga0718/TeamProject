package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class BuySubDao {
	private BuySubDao() {
	}

	private static BuySubDao instance;

	public static BuySubDao getInstance() {
		if (instance == null) {
			instance = new BuySubDao();
		}
		return instance;
	}
	
	public  int buySubscribe(Map<String, Object>param) {
		String sql = "INSERT INTO TB_ORDER_SUB"
				+ "     VALUES("
				+ "     (SELECT NVL(MAX(O_SUBNO),0) + 1"   //자동으로 올라가는 쿠폰번호
				+ "        FROM TB_ORDER_SUB),"
				+ "        ?," //아이디 받는거
				+ "        ?," //구독권 번호 받는거 P_NO
				+ "        TO_CHAR(SYSDATE,'YYYY/MM/DD'),"
				+ "           (SELECT P_COST"
				+ "              FROM TB_SUBPROD"
				+ "             WHERE P_NO = ? )," //선택한 상품의 가격 P_NO
				+ "         (SELECT TO_CHAR(SYSDATE + P_PERIOD,'YYYY/MM/DD')"
				+ "            FROM TB_SUBPROD  "
				+ "           WHERE P_NO = ?))"; //선택한 상품의 만료일 P_NO
		List<Object>_param = new ArrayList<Object>();
		_param.add(param.get("ID"));
		_param.add(param.get("P_NO"));
		_param.add(param.get("P_NO"));
		_param.add(param.get("P_NO"));
		return JDBCUtil.update(sql, _param);
}
}
	
	
	
	
	
