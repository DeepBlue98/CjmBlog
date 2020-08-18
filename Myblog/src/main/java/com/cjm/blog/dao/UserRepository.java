package com.cjm.blog.dao;

import com.cjm.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    //方法名字遵守命名规范就可以用
    User findByUsernameAndPassword(String username,String password);
}
