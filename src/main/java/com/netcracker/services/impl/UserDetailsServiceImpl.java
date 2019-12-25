package com.netcracker.services.impl;

import com.netcracker.dao.UserDao;
import com.netcracker.models.User;
import com.netcracker.services.utils.ObjectsCheckUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserDao userDao;

    private static final Logger logger = Logger.getLogger(UserDetailsServiceImpl.class);

    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.getUserByEmail(s);

        ObjectsCheckUtils.isNotNull(user);

        logger.debug(user.geteMail());

        return new org.springframework.security.core.userdetails.User(
                user.geteMail(),
                user.getPassword(),
                AuthorityUtils.createAuthorityList("USER"));
    }
}
