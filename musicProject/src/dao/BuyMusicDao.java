package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class BuyMusicDao {
	private BuyMusicDao() {
	}

	private static BuyMusicDao instance;

	public static BuyMusicDao getInstance() {
		if (instance == null) {
			instance = new BuyMusicDao();
		}
		return instance;
	}
	
	public int buyMusic(Map<String, Object>param) {
		String sql = "INSERT INTO TB_ORDER_MUSIC"
				+ "   VALUES((SELECT NVL(MAX(O_MUSNO),0)+1"
				+ "             FROM TB_ORDER_MUSIC),"
				+ "             ?," //ID
				+ "             ?," //음악번호
				+ "      TO_CHAR(SYSDATE,'YYYY/MM/DD'),"
				+ "            500)";
		List<Object>_param = new ArrayList<Object>();
		_param.add(param.get("ID"));
		_param.add(param.get("M_NO"));		
		return JDBCUtil.update(sql, _param);
	}
	
}












