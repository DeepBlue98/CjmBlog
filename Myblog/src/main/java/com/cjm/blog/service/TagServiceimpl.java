package com.cjm.blog.service;

import com.cjm.blog.NotFoundException;
import com.cjm.blog.dao.TagRepository;
import com.cjm.blog.po.Tag;
import com.cjm.blog.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceimpl implements ITagService {
    @Autowired
    TagRepository tagRepository;

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public List<Tag> getTagList() {
        return tagRepository.findAll();
    }

    @Override
    public Tag getTag(Long id) {
        return tagRepository.getOne(id);
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort=Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable= PageRequest.of(0,size,sort);
        return tagRepository.findtop(pageable);

    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag tag1=tagRepository.getOne(id);
        if (tag1==null){
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(tag,tag1);
        return tagRepository.save(tag1);
    }

    @Override
    public List<Tag> listTag(String ids) { //1,2,3
        List<Tag> lists=new ArrayList<>();
        List<Long> ids_list=convertToList(ids);
        for(Long id:ids_list){
            Tag tag=tagRepository.getOne(id);
            lists.add(tag);
        }

        return lists;
//        return tagRepository.find
//        return tagRepository.findAll(convertToList(ids));
    }

    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }


    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findTagByName(name);
    }
}
