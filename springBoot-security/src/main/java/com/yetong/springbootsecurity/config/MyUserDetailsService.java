package com.yetong.springbootsecurity.config;

import com.yetong.springbootsecurity.dao.UserDao;
import com.yetong.springbootsecurity.entity.Role;
import com.yetong.springbootsecurity.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.loadUserByUsername(username);
        List<Role> rolesByUid = userDao.getRolesByUid(user.getId());
        user.setRoles(rolesByUid);
        return user;
    }
}
