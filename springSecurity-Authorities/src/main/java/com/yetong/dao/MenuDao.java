package com.yetong.dao;

import com.yetong.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuDao {
    Menu getMenuRole(String pattern);
}
