package com.myapp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.gitee.fastmybatis.core.PageInfo;
import com.gitee.fastmybatis.core.query.Column;
import com.gitee.fastmybatis.core.query.EnumColumn;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.query.Sort;
import com.gitee.fastmybatis.core.util.MapperUtil;
import com.myapp.dao.TUserMapper;
import com.myapp.entity.TUser;

/**
 * mapper测试
 */
public class TUserMapperTest extends FastmybatisSpringbootApplicationTests {

	@Resource
	TUserMapper mapper;
	@Resource
	TransactionTemplate transactionTemplate;
	
	// 根据主键查询
	@Test
	public void testGet() {
		TUser user = mapper.getById(3);
		print(user);
	}
	
	@Test
    public void testMap() {
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("id", 3);
	    map.put("username", "李四");
        List<TUser> list = mapper.findByMap(map);
        print(list);
        
        TUser user = mapper.getByMap(map);
        this.print(user);
    }
	
	// 根据字段查询一条记录
	@Test
	public void testGetByProperty() {
		TUser user = mapper.getByProperty("username", "王五");
		print(user);
	}
	
	// 根据条件查询一条记录
	@Test
	public void testGetByExpression() {
		// 查询ID=3的用户
		Query query = Query.build().eq("id", 3);
		
		TUser user = mapper.getByQuery(query);
		
		print(user);
	}
	
	// 条件查询
	// 查询username='张三'的用户
	@Test
	public void testFind() {
		Query query = new Query();
		// 添加查询条件
		query.eq("username", "张三");
		
		List<TUser> list = mapper.list(query); // 获取结果集
		long count = mapper.countTotal(query); // 获取总数
		print("count:" + count);
		for (TUser user : list) {
			System.out.println(user);
		}
	}
	
	// 分页查询
	/*MYSQL语句:
	 * 
	 * SELECT t.`id` , t.`username` , t.`state` , t.`isdel` , t.`remark` , t.`add_time` , t.`money` , t.`left_money` 
	 * FROM `t_user` t 
	 * ORDER BY id DESC 
	 * LIMIT ?,? 
	 * 
	 * Parameters: 2(Integer), 2(Integer)
	 */
	@Test
	public void testPage() {
		Query query = new Query();
		
		query.page(2, 2) // 设置pageIndex，pageSize
			.addSort("id", Sort.DESC); // 添加排序
		
		// 查询后的结果，包含总记录数，结果集，总页数等信息
		PageInfo<TUser> pageInfo = MapperUtil.query(mapper, query);
		
		List<TUser> rows = pageInfo.getList();
		for (TUser user : rows) {
			System.out.println(user);
		}
	}
	
	// 自定义返回字段查询，只返回两个字段
	// SELECT t.id,t.username FROM `t_user` t LIMIT 0,10
	@Test
	public void testSelfColumns() {
		Query query = new Query();
		// 只返回id,username
		query.setColumns(Arrays.asList("t.id","t.username"));
		
		List<TUser> list = mapper.list(query);
		
		for (TUser user : list) {
			System.out.println(user);
		}
	}
	
	/**
     * 联表分页
     * SELECT t.`id` , t.`username` , t.`state` , t.`isdel` , t.`remark` , t.`add_time` , t.`money` , t.`left_money` 
     * FROM `t_user` t 
     * LEFT JOIN user_info t2 ON t.id = t2.user_id 
     * WHERE t.isdel = 0 LIMIT ?,? 
     */
    @Test
    public void testJoinPage() {
        Query query = Query.build()
             // 左连接查询,主表的alias默认为t
                .join("LEFT JOIN user_info t2 ON t.id = t2.user_id")
                .page(1, 5); 
        
        List<TUser> list = mapper.list(query);
        
        System.out.println("==============");
        for (TUser user : list) {
            System.out.println(
                user.getId() 
                + " " + user.getUsername() 
            );
        }
        System.out.println("==============");
    }
    
    @Test
    public void testJoinPageXml() {
        Query query = Query.build()
                    .page(1, 5); 
            
        List<TUser> list = mapper.findJoinPage(query);
        
        System.out.println("==============");
        for (TUser user : list) {
            System.out.println(
                user.getId() 
                + " " + user.getUsername() 
            );
        }
        System.out.println("==============");
    }
	
	// 自定义sql，见TUserMapper.xml
	// 注意mybatis的mapper必须跟Mapper类一致
	@Test
	public void testSelfSql() {
		TUser user = mapper.selectByName("张三");
		
		print(user);
	}
	
	// 添加-保存所有字段
	@Test
	public void testInsert() {
		TUser user = new TUser();
		user.setId(30);
		user.setAddTime(new Date());
		user.setIsdel(false);
		user.setLeftMoney(22.1F);
		user.setMoney(new BigDecimal(100.5));
		user.setRemark("备注");
		user.setState((byte)0);
		user.setUsername("张三");
		
		mapper.save(user);
		
		print("添加后的主键:" + user.getId());
		print(user);
	}
	
	// 添加-保存非空字段
	@Test
	public void testInsertIgnoreNull() {
		TUser user = new TUser();
		user.setAddTime(new Date());
		user.setIsdel(true);
		user.setMoney(new BigDecimal(100.5));
		user.setState((byte)0);
		user.setUsername("张三notnull");
		user.setLeftMoney(null);
		user.setRemark(null);
		
		mapper.saveIgnoreNull(user);
		
		print("添加后的主键:" + user.getId());
		print(user);
	}
	
	// 批量添加
	/*
	 * 支持mysql,sqlserver2008。如需支持其它数据库使用saveMulti方法
	 * INSERT INTO person (id, name, age)
		VALUES
    	(1, 'Kelvin', 22),
    	(2, 'ini_always', 23);
	 */
	@Test
	public void testInsertBatch() {
		List<TUser> users = new ArrayList<>();
		
		for (int i = 0; i < 3; i++) { // 创建3个对象
			TUser user = new TUser();
			user.setUsername("username" + i);
			user.setMoney(new BigDecimal(i));
			user.setRemark("remark" + i);
			user.setState((byte)0);
			user.setIsdel(false);
			user.setAddTime(new Date());
			user.setLeftMoney(200F);
			users.add(user);
		}
		
		int i = mapper.saveBatch(users); // 返回成功数
		
		System.out.println("saveBatch --> " + i);
	}
	
	/**
	 * 批量添加,兼容更多数据库版本,采用union all
	 * INSERT INTO [t_user] ( [username] , [state] , [isdel] , [remark] , [add_time] , [money] , [left_money] ) 
	 * SELECT ? , ? , ? , ? , ? , ? , ? 
	 * UNION ALL SELECT ? , ? , ? , ? , ? , ? , ? 
	 * UNION ALL SELECT ? , ? , ? , ? , ? , ? , ? 
	 */
	@Test
	public void testInsertMulti() {
		List<TUser> users = new ArrayList<>();
		
		for (int i = 0; i < 3; i++) { // 创建3个对象
			TUser user = new TUser();
			user.setUsername("username" + i);
			user.setMoney(new BigDecimal(i));
			user.setRemark("remark" + i);
			user.setState((byte)0);
			user.setIsdel(false);
			user.setAddTime(new Date());
			user.setLeftMoney(200F);
			users.add(user);
		}
		
		int i = mapper.saveMulti(users); // 返回成功数
		
		System.out.println("saveMulti --> " + i);
	}
	
	// 批量添加指定字段,仅支持msyql，sqlserver2008，如需支持其它数据库使用saveMulti方法
	@Test
	public void testInsertBatchWithColumns() {
		List<TUser> users = new ArrayList<>();
		
		for (int i = 0; i < 3; i++) { // 创建3个对象
			TUser user = new TUser();
			user.setUsername("usernameWithColumns" + i);
			user.setMoney(new BigDecimal(i));
			user.setAddTime(new Date());
			
			users.add(user);
		}
		
		/*
		 * INSERT INTO `t_user` ( username , money , add_time ) 
		 * VALUES ( ? , ? , ? ) , ( ? , ? , ? ) , ( ? , ? , ? ) 
		 */
		int i= mapper.saveBatchWithColumns(Arrays.asList(
				new Column("username", "username") // 第一个是数据库字段,第二个是java字段
				,new Column("money", "money")
				,new Column("add_time", "addTime")
				), users);
		
		System.out.println("saveBatchWithColumns --> " + i);
		
	}
	
	// 事务回滚
	@Test
	public void testUpdateTran() {
		TUser user = transactionTemplate.execute(new TransactionCallback<TUser>() {
			@Override
			public TUser doInTransaction(TransactionStatus arg0) {
				try{
					TUser user = mapper.getById(3);
					user.setUsername("王五1111");
					user.setMoney(user.getMoney().add(new BigDecimal(0.1)));
					user.setIsdel(true);
					
					int i = mapper.update(user);
					print("testUpdate --> " + i);
					int j = 1/0; // 模拟错误
					return user;
				}catch(Exception e) {
					e.printStackTrace();
					throw e;
				}
			}
			
		});
		
		print(user);
	}
	
	// 更新所有字段
	@Test
	public void testUpdate() {
		TUser user = mapper.getById(3);
		user.setUsername("李四");
		user.setMoney(user.getMoney().add(new BigDecimal(0.1)));
		user.setState((byte)1);
		user.setIsdel(true);
		
		int i = mapper.update(user);
		print("testUpdate --> " + i);
	}
	
	// 更新不为null的字段
	/*
	 *UPDATE [t_user] SET [username]=?, [isdel]=? WHERE [id] = ?  
	 */
	@Test
	public void updateIgnoreNull() {
		TUser user = new TUser();
		user.setId(3);
		user.setUsername("王五");
		user.setState((byte)2);
		user.setIsdel(false);
		int i = mapper.updateIgnoreNull(user);
		print("updateNotNull --> " + i);
	}
	
	// 删除
	@Test
	public void testDel() {
		TUser user = new TUser();
		user.setId(14);
		int i = mapper.delete(user);
		print("del --> " + i);
	}
	
	// 删除
    @Test
    public void testDelById() {
        int i = mapper.deleteById(15);
        print("deleteById --> " + i);
    }
	
	// 根据条件删除
	// DELETE FROM `t_user` WHERE state = ? 
	@Test
	public void deleteByQuery() {
		Query query = new Query();
		query.eq("state", 4);
		int i = mapper.deleteByQuery(query);
		print("deleteByQuery --> " + i);
	}
	
}
