package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class BuyCouDao {
	private BuyCouDao() {
	}

	private static BuyCouDao instance;

	public static BuyCouDao getInstance() {
		if (instance == null) {
			instance = new BuyCouDao();
		}
		return instance;
	}
	
	public  int buyCoupon(Map<String, Object>param) {
		String sql = "INSERT INTO TB_ORDER_COU"
				+ "     VALUES("
				+ "     (SELECT NVL(MAX(O_COUNO),0) + 1"   //자동으로 올라가는 쿠폰번호
				+ "        FROM TB_ORDER_COU),"
				+ "        ?,"
				+ "        ?,"
				+ "        (SELECT COU_PRICE"
				+ "           FROM TB_COUPON"
				+ "          WHERE COU_NO = ?),"
				+ "        TO_CHAR(SYSDATE,'YYYY/MM/DD'))";
		List<Object>_param = new ArrayList<Object>();
		_param.add(param.get("COU_NO"));
		_param.add(param.get("ID"));
		_param.add(param.get("COU_NO"));
		
		return JDBCUtil.update(sql, _param);
	}
}




















