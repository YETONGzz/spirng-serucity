package com.yetong.config;

import com.yetong.dao.UserDaoMapper;
import com.yetong.entity.LoginUser;
import com.yetong.entity.Role;
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
    UserDaoMapper userDaoMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUser user = userDaoMapper.loadUserByUsername(username);
        List<Role> rolesByUid = userDaoMapper.getRolesByUid(user.getId());
        user.setRoles(rolesByUid);
        return user;
    }


    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        Integer integer = userDaoMapper.updatePassword(user.getUsername(), newPassword);
        if (integer == 1) {
            ((LoginUser) user).setPassword(newPassword);
        }
        return user;
    }
}
