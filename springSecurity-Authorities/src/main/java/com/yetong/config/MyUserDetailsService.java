package com.yetong.config;

import com.yetong.dao.UserDao;
import com.yetong.entity.Role;
import com.yetong.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyUserDetailsService implements UserDetailsService, UserDetailsPasswordService {

    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.loadUserByUsername(username);
        List<Role> rolesByUid = userDao.getRolesByUid(user.getId());
        user.setRoles(rolesByUid);
        return user;
    }


    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        Integer integer = userDao.updatePassword(user.getUsername(), newPassword);
        if (integer == 1) {
            ((User) user).setPassword(newPassword);
        }
        return user;
    }
}
