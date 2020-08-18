package com.cjm.blog.dao;

import com.cjm.blog.po.EmailServer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface EmailRepository extends JpaRepository<EmailServer,Long> {

    @Transactional
    @Modifying
    @Query("update EmailServer e  set e.open =true where e.id = ?1")
    int toopen(Long id);

    @Transactional
    @Modifying
    @Query("update EmailServer e  set e.open =false where e.id = ?1  ")
    int toclose(Long id);
}
