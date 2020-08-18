package com.cjm.blog.service;

import com.cjm.blog.dao.EmailRepository;
import com.cjm.blog.po.EmailServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailOpenServiceimpl implements IEmailOpenService {
    @Autowired
    EmailRepository emailRepository;

    @Override
    public int toopen(Long id) {
        return emailRepository.toopen(id);
    }

    @Override
    public int toclose(Long id) {
        return emailRepository.toclose(id);
    }

    @Override
    public EmailServer findopen(Long id) {
        return emailRepository.getOne(id);
    }
}
