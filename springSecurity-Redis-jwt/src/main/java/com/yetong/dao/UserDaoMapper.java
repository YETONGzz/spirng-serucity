package com.yetong.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yetong.entity.LoginUser;
import com.yetong.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserDaoMapper extends BaseMapper<LoginUser> {


    //提供根据用户名返回用户方法
    LoginUser loadUserByUsername(String username);

    //提供根据用户id查询用户角色信息方法
    List<Role> getRolesByUid(Integer uid);

    Integer updatePassword(@Param("username") String username, @Param("password") String password);
}
