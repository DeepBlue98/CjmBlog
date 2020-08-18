package com.cjm.blog.service;

import com.cjm.blog.po.EmailServer;

public interface IEmailOpenService {
    EmailServer findopen(Long id);

    int toopen(Long id);

    int toclose(Long id);
}
