package component.app;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.support.PageEasyui;
import com.gitee.fastmybatis.core.util.MapperUtil;
import com.myapp.entity.TUser;
import com.myapp.mapper.TUserMapper;

public class EasyuiSupportTest extends TestBase {

	@Autowired
	private TUserMapper userMapper;

	/**
	 * 支持easyui的datagrid表格返回json
	 */
	@Test
	public void testEasyuiGrid() {

		Query query = Query.build().eq("state", 0);

		PageEasyui<TUser> page = MapperUtil.query(userMapper, query, PageEasyui.class);

		/*
		 * {"rows":[{"addTime":1487644664000,"id":1,"isdel":false,"leftMoney":22
		 * .1,"money":101.10,"remark":"批量修改备注","state":0,"username":"王五"},{
		 * "addTime":1487644811000,"id":2,"isdel":false,"leftMoney":22.1,"money"
		 * :100.50,"remark":"批量修改备注","state":0,"username":"张三"},{"addTime":
		 * 1487644811000,"id":4,"isdel":false,"leftMoney":22.1,"money":100.50,
		 * "remark":"批量修改备注","state":0,"username":"张三"},{"addTime":1487644811000
		 * ,"id":5,"isdel":false,"leftMoney":22.1,"money":100.50,"remark":
		 * "批量修改备注","state":0,"username":"张三"},{"addTime":1487644811000,"id":6,
		 * "isdel":false,"leftMoney":22.1,"money":100.50,"remark":"批量修改备注",
		 * "state":0,"username":"张三"},{"id":10,"isdel":false,"money":0.00,
		 * "remark":"批量修改备注","state":0,"username":"username0"},{"id":11,"isdel":
		 * false,"money":1.00,"remark":"批量修改备注","state":0,"username":"username1"
		 * },{"id":12,"isdel":false,"money":2.00,"remark":"批量修改备注","state":0,
		 * "username":"username2"},{"addTime":1503287254000,"id":13,"isdel":
		 * false,"leftMoney":200,"money":0.00,"remark":"批量修改备注","state":0,
		 * "username":"username0"},{"addTime":1503287254000,"id":15,"isdel":
		 * false,"leftMoney":200,"money":2.00,"remark":"批量修改备注","state":0,
		 * "username":"username2"}],"total":15}
		 */
		System.out.println("==================");
		System.out.println(JSON.toJSONString(page));
		System.out.println("==================");

	}
}
