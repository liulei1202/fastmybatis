package com.myapp.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.gitee.fastmybatis.core.mapper.CrudMapper;
import com.myapp.entity.TUser;

public interface TUserMapper extends CrudMapper<TUser, Integer> {

    // 自定义sql，官方自带，不需要写xml
    /**
     * 修改用户名
     * @param id
     * @param username
     * @return 返回影响行数
     */
    @Update("update t_user set username = #{username} where id = #{id}")
    int updateById(@Param("id") int id, @Param("username") String username);

    // 自定义sql，写在xml中
    TUser selectByName(@Param("username") String username);  
    
 // 自定义sql，写在xml中
    TUser selectByName2(@Param("username") String username);  
    // 自定义sql，写在xml中
    TUser selectByName3(@Param("username") String username);  
}