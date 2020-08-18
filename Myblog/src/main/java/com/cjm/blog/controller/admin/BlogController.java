package com.cjm.blog.controller.admin;


import com.cjm.blog.po.Blog;
import com.cjm.blog.po.User;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private IBlogService blogService;
    @Autowired
    private ITypeService typeService;
    @Autowired
    private ITagService tagService;

    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";


    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model) {
        model.addAttribute("types", typeService.getTypeList());
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(8));
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blogs :: blogList";
    }

    private void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.getTypeList());
        model.addAttribute("tags", tagService.getTagList());
    }

    @GetMapping("/blogs/input")
    public String input(Model model) {
        setTypeAndTag(model);
        model.addAttribute("blog", new Blog());
        return INPUT;
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return INPUT;
    }
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session) {
        if(blog.getFlag()=="") blog.setFlag("原创");
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        if (blog.getId() == null) {
            b =  blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
        }

        if (b == null ) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return REDIRECT_LIST;
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return REDIRECT_LIST;
    }

}
