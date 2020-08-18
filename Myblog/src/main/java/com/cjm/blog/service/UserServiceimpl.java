package com.cjm.blog.service;

import com.cjm.blog.dao.UserRepository;
import com.cjm.blog.po.User;
import com.cjm.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceimpl implements  UserService{
    @Autowired
    UserRepository userRepository;
    @Override
    public User checkUser(String username,String password) {
        User user=userRepository.findByUsernameAndPassword(username, MD5Utils.code(password) );

        return user;
    }
}
