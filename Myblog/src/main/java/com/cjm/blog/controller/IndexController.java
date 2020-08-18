package com.cjm.blog.controller;

import com.cjm.blog.service.IBlogService;
import com.cjm.blog.service.ITagService;
import com.cjm.blog.service.ITypeService;
import com.cjm.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jws.WebParam;

@Controller
public class IndexController {
    @Autowired
    IBlogService blogService;

    @Autowired
    ITypeService typeService;

    @Autowired
    ITagService tagService;

    @GetMapping("/")
    public String Index(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(8));
        model.addAttribute("tags",tagService.listTagTop(8));
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(8));
        return "index";
    }


    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         Model model, @RequestParam String query){
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        model.addAttribute("blog",blogService.getAndConvert(id));
        return "blog";
    }
}
