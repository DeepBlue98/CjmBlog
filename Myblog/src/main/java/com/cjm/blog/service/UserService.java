package com.cjm.blog.service;

import com.cjm.blog.po.User;

public interface UserService {
    User checkUser(String username,String password);
}
