package com.myapp;

import com.gitee.fastmybatis.core.PageInfo;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.query.Sort;
import com.gitee.fastmybatis.core.util.MapperUtil;
import com.gitee.fastmybatis.core.util.MyBeanUtil;
import com.myapp.entity.TUser;
import com.myapp.mapper.TUserMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * mapper测试
 */
public class TUserMapperTest extends TestBase {

    @Autowired
    TUserMapper mapper;
    @Autowired
    TransactionTemplate transactionTemplate;

    /**
     * 根据主键查询
     * 
     * <pre>
     * SELECT t.`id` , t.`username` , t.`state` , t.`isdel` , t.`remark` , t.`add_time` , t.`money` , t.`left_money` 
     * FROM `t_user` t 
     * WHERE `id` = ? LIMIT 1
     * </pre>
     */
    @Test
    public void testGetById() {
        TUser user = mapper.getById(3);
        print(user);
    }

    /**
     * 根据条件查询一条记录
     * 
     * <pre>
     * SELECT t.`id` , t.`username` , t.`state` , t.`isdel` , t.`remark` , t.`add_time` , t.`money` , t.`left_money` 
     * FROM `t_user` t 
     * WHERE id = ? AND money > ? LIMIT 1
     * </pre>
     */
    @Test
    public void testGetByQuery() {
        // 查询ID=3,金额大于1的用户
        Query query = new Query().eq("id", 3).gt("money", 1);

        TUser user = mapper.getByQuery(query);
        print(user);
    }

    /**
     * 根据字段查询一条记录
     * 
     * <pre>
     * SELECT t.`id` , t.`username` , t.`state` , t.`isdel` , t.`remark` , t.`add_time` , t.`money` , t.`left_money` 
     * FROM `t_user` t 
     * WHERE t.`username` = ? LIMIT 1
     * </pre>
     */
    @Test
    public void testGetByColumn() {
        TUser user = mapper.getByColumn("username", "王五");
        print(user);
    }

    /**
     * 根据条件查询列表
     * 
     * <pre>
     * SELECT t.`id` , t.`username` , t.`state` , t.`isdel` , t.`remark` , t.`add_time` , t.`money` , t.`left_money` 
     * FROM `t_user` t 
     * WHERE state = ? AND money IN ( ? , ? , ? )
     * </pre>
     */
    @Test
    public void testList() {
        Query query = new Query()
                .eq("state", 0)
                .in("money", Arrays.asList(100, 1.0, 3))
                .orderby("state",Sort.DESC);
        List<TUser> list = mapper.list(query);
        for (TUser tUser : list) {
            print(tUser);
        }
    }

    /**
     * 返回自定义字段
     * 
     * <pre>
     * SELECT t.id , t.username as username FROM `t_user` t WHERE username = ?
     * </pre>
     */
    @Test
    public void testListMap() {
        Query query = new Query();
        // 添加查询条件
        query.eq("username", "张三");

        // 自定义字段
        List<String> columns = Arrays.asList("t.id", "t.username as username","t.add_time as addTime", "t.money");
        // 查询，返回一个Map集合
        List<Map<String, Object>> list = mapper.listMap(columns, query);

        for (Map<String, Object> map : list) {
            System.out.println(map);
        }
        // 将map集合转换成实体类集合
        List<TUser> userList = MyBeanUtil.mapListToObjList(list, TUser.class);
        System.out.println("userList.size():" + userList.size());
        for (TUser tUser : userList) {
            System.out.println("id:" + tUser.getId() + ",username:" + tUser.getUsername());
        }
    }

    /**
     * 获取记录数
     * 
     * <pre>
     * SELECT count(*) FROM `t_user` t WHERE username = ?
     * </pre>
     */
    @Test
    public void testGetCount() {
        Query query = new Query();
        // 添加查询条件
        query.eq("username", "张三");

        long total = mapper.getCount(query); // 获取总数

        print("total:" + total);
    }
    
    @Test
    public void testLike() {
        Query query = new Query();
        // 添加查询条件
        query.gt("id", 1).ge("id", 2).le("id", 32).notEq("id", 12);

        long total = mapper.getCount(query); // 获取总数

        print("total:" + total);
    }

    /**
     * 分页查询
     * 
     * <pre>
     * SELECT t.`id` , t.`username` , t.`state` , t.`isdel` , t.`remark` , t.`add_time` , t.`money` , t.`left_money` 
     * FROM `t_user` t 
     * WHERE username = ? LIMIT ?,?
     * </pre>
     */
    @Test
    public void testPageInfo() {
        Query query = new Query();
        // 添加查询条件
        query
                .eq("username", "张三")
                .page(1, 2) // 分页查询，按页码分，通常使用这种。
        // .limit(start, offset) // 分页查询，这种是偏移量分页
        ;

        // 分页信息
        PageInfo<TUser> pageInfo = MapperUtil.query(mapper, query);

        List<TUser> list = pageInfo.getList(); // 结果集
        long total = pageInfo.getTotal(); // 总记录数
        int pageCount = pageInfo.getPageCount(); // 共几页

        System.out.println("total:" + total);
        System.out.println("pageCount:" + pageCount);
        print(list);
    }

    /**
     * 排序
     * 
     * <pre>
     * SELECT t.`id` , t.`username` , t.`state` , t.`isdel` , t.`remark` , t.`add_time` , t.`money` , t.`left_money` 
     * FROM `t_user` t 
     * ORDER BY id ASC,state DESC
     * </pre>
     */
    @Test
    public void testOrder() {
        Query query = new Query().addSort("id", Sort.ASC).addSort("state", Sort.DESC);

        List<TUser> list = mapper.list(query);
        print(list);
    }

    /**
     * 联表分页
     * 
     * <pre>
     * SELECT t.`id` , t.`username` , t.`state` , t.`isdel` , t.`remark` , t.`add_time` , t.`money` , t.`left_money` 
     * FROM `t_user` t LEFT JOIN user_info t2 ON t.id = t2.user_id 
     * WHERE t.isdel = 0 LIMIT ?,?
     * </pre>
     */
    @Test
    public void testJoinPage() {
        Query query = new Query()
                // 左连接查询,主表的alias默认为t
                .join("LEFT JOIN user_info t2 ON t.id = t2.user_id").page(1, 5);

        List<TUser> list = mapper.list(query);

        System.out.println("==============");
        for (TUser user : list) {
            System.out.println(user.getId() + " " + user.getUsername());
        }
        System.out.println("==============");
    }
    
    /**
     * 联表查询，并返回指定字段
     * <pre>
     * SELECT t2.user_id userId , t.username , t2.city
     * FROM `t_user` t 
     * LEFT JOIN user_info t2 ON t.id = t2.user_id WHERE t.isdel = 0 
     * </pre>
     */
    @Test
    public void testJoinColumn() {
        Query query = new Query();
        // 左连接查询,主表的alias默认为t
        query.join("LEFT JOIN user_info t2 ON t.id = t2.user_id");
        // 指定返回字段
        List<String> column = Arrays.asList("t2.user_id userId", "t.username", "t2.city");
        // 查询结果返回到map中
        List<Map<String, Object>> mapList = mapper.listMap(column, query);
        // 再将map转换成实体bean
        List<UserInfoVo> list = MyBeanUtil.mapListToObjList(mapList, UserInfoVo.class);
        
        this.print(list);
    }

    /**
     * 自定义sql方式1
     */
    @Test
    public void testSelfSql1() {
        int i = mapper.updateById(1, "张三");
        print("updateById--> " + i);
    }

    /**
     * 自定义sql方式2，见TUserMapper.xml
     */
    @Test
    public void testSelfSql2() {
        TUser user = mapper.selectByName3("张三");
        print(user);
    }

    /**
     * 添加-保存所有字段
     */
    @Test
    public void testSave() {
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

    /**
     * 添加-保存非空字段
     */
    @Test
    public void testSaveIgnoreNull() {
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

    /**
     * 批量添加.支持mysql,sqlserver2008。如需支持其它数据库使用saveMulti方法
     * 
     * <pre>
     * INSERT INTO person (id, name, age) VALUES (1, 'Kelvin', 22), (2, 'ini_always', 23);
     * </pre>
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
     * 
     * <pre>
     * INSERT INTO `t_user` ( `username` , `state` , `isdel` , `remark` , `add_time` , `money` , `left_money` ) 
     * SELECT ? , ? , ? , ? , ? , ? , ? 
     * UNION ALL 
     * SELECT ? , ? , ? , ? , ? , ? , ? 
     * UNION ALL 
     * SELECT ? , ? , ? , ? , ? , ? , ?
     * </pre>
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

    /**
     * 事务回滚
     */
    @Test
    public void testUpdateTran() {
        TUser user = transactionTemplate.execute(new TransactionCallback<TUser>() {
            @Override
            public TUser doInTransaction(TransactionStatus arg0) {
                try {
                    TUser user = mapper.getById(3);
                    user.setUsername("王五1");
                    user.setMoney(user.getMoney().add(new BigDecimal(0.1)));
                    user.setIsdel(true);

                    int i = mapper.update(user);
                    print("testUpdate --> " + i);
                    int j = 1 / 0; // 模拟错误
                    return user;
                } catch (Exception e) {
                    e.printStackTrace();
                    arg0.setRollbackOnly();
                    return null;
                }
            }

        });

        print(user);
    }

    /**
     * 更新所有字段
     */
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

    /**
     * 更新不为null的字段 UPDATE [t_user] SET [username]=?, [isdel]=? WHERE [id] = ?
     */
    @Test
    public void testUpdateIgnoreNull() {
        TUser user = new TUser();
        user.setId(3);
        user.setUsername("王五");
        user.setState((byte)2);
        user.setIsdel(false);
        int i = mapper.updateIgnoreNull(user);
        print("updateNotNull --> " + i);
    }
    
    /**
     * 根据条件更新。将状态为2的数据姓名更新为李四
     * UPDATE `t_user` SET `username`=?, `add_time`=? WHERE state = ? 
     */
    @Test
    public void testUpdateByQuery() {
        Query query = new Query().eq("state", 2);
        // 方式1
        TUser user = new TUser();
        user.setUsername("李四");
        int i = mapper.updateByQuery(user, query);
        print("updateByQuery --> " + i);
        
       /* // 方式2
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", "李四2");
        i = mapper.updateByQuery(map, query);
        print("updateByQuery --> " + i);*/
    }

    /**
     * 根据主键删除
     */
    @Test
    public void testDeleteById() {
        int i = mapper.deleteById(14);
        print("del --> " + i);
    }

    /**
     * 根据对象删除
     */
    @Test
    public void testDelete() {
        TUser user = new TUser();
        user.setId(15);
        int i = mapper.delete(user);
        print("del --> " + i);
    }

    /**
     * 根据条件删除 DELETE FROM `t_user` WHERE state = ?
     */
    @Test
    public void testDeleteByQuery() {
        Query query = new Query();
        query.eq("state", 3);
        int i = mapper.deleteByQuery(query);
        print("deleteByQuery --> " + i);
    }
    
    /**
     * 强力查询，将无视逻辑删除字段
     */
    @Test
    public void testForceQuery() {
        Query query = new Query().eq("id", 3)
                .enableForceQuery()
                ;
        TUser user = mapper.getByQuery(query);
        this.print(user);
    }

}
