package com.cjm.blog.service;

import com.cjm.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITypeService {
    Type saveType(Type type);

    void deleteType(Long id);

    Type getType(Long id);

    Page<Type> listType(Pageable pageable);

    Type updateType(Long id,Type type);

    Type getTypebyname(String name);

    List<Type> getTypeList();
    List<Type> listTypeTop(Integer size);
}
