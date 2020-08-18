package com.cjm.blog.dao;

import com.cjm.blog.po.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag,Long> {
    Tag findTagByName(String name);

    @Query("select t from Tag t")
    List<Tag> findtop(Pageable pageable);
}
