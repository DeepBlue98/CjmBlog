package com.cjm.blog.service;

import com.cjm.blog.po.Comment;
import com.cjm.blog.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {
    Comment findByEmail(String email);

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);

    Page<Comment>  findAllComments(Pageable pageable);

    int updatecommtoban(Long id);

    int updatecommtonotban(Long id);

    int updatecommtoview(Long id);

}
