package com.yetong.dao;

import com.yetong.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuDao {
    List<Menu> getAllMenu(String pattern);
}
