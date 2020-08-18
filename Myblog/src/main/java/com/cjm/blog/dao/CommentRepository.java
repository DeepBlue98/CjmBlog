package com.cjm.blog.dao;

import com.cjm.blog.po.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {


    Comment findByEmail(String email);

    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);

    @Query("select c from Comment c")
    Page<Comment> findAllComments(Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Comment c set c.ban=true where c.id = ?1  ")
    int updatecommtoban(Long id);

    @Transactional
    @Modifying
    @Query("update Comment c set c.ban=false where c.id = ?1  ")
    int updatecommtonotban(Long id);

    @Transactional
    @Modifying
    @Query("update Comment c set c.haveView=true where c.id = ?1 ")
    int updatecommtoview(Long id);
}
