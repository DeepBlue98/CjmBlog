package com.cjm.blog.service;

import com.cjm.blog.po.Tag;
import com.cjm.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITagService {
    Tag saveTag(Tag tag);

    void deleteTag(Long id);

    Tag getTag(Long id);

    Page<Tag> listTag(Pageable pageable);

    Tag updateTag(Long id,Tag tag);

    Tag getTagByName(String name);

    List<Tag> getTagList();

    List<Tag> listTag(String ids);

    List<Tag> listTagTop(Integer size);


}
