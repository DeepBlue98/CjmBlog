package com.cjm.blog.service;

import com.cjm.blog.po.Blog;
import com.cjm.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface IBlogService {
    Blog getBlog(Long id);

    Blog getAndConvert(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(Long tagId,Pageable pageable);

    //搜索的接口
    Page<Blog> listBlog(String query, Pageable pageable);


    //最新推荐的接口
    List<Blog> listRecommendBlogTop(Integer size);

    //根据年份分类查出来  放入map里面
    Map<String, List<Blog>> archiveBlog();

    Long countBlog();

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);


    //List<Blog> listBlogTop(Integer size);
}
