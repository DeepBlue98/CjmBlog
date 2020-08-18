package com.cjm.blog.service;

import com.cjm.blog.NotFoundException;
import com.cjm.blog.dao.TypeReposity;
import com.cjm.blog.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceimpl implements ITypeService {

    @Autowired
    TypeReposity typeReposity;

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort=Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable= PageRequest.of(0,size,sort);
        return typeReposity.findTop(pageable);

    }

    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeReposity.save(type);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeReposity.deleteById(id);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeReposity.getOne(id);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeReposity.findAll(pageable);
    }

    @Override
    public Type getTypebyname(String name) {
        return typeReposity.findTypeByName(name);
    }

    @Transactional
    @Override
    public Type updateType(Long id,Type type) {
        Type t=typeReposity.getOne(id);
        if (t==null){
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type,t);
        return typeReposity.save(t);
    }

    @Override
    public List<Type> getTypeList() {
        return typeReposity.findAll();
    }
}
