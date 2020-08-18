package com.cjm.blog.controller.admin;

import com.cjm.blog.service.CommentService;
import com.cjm.blog.service.IEmailOpenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminCommentController {

    @Autowired
    IEmailOpenService emailOpenService;

    @Autowired
    CommentService commentService;

    @GetMapping("/comments")
    public String getallcomments(Model model,
                                 @PageableDefault(size = 10,sort = {"createTime"},direction = Sort.Direction.DESC) Pageable pageable){
        model.addAttribute("comments",commentService.findAllComments(pageable));
        Long l = new Long((long)1);
        model.addAttribute("email",emailOpenService.findopen(l));
        return "admin/comments";
    }

    @GetMapping("/comments/{id}/ban")
    public String ban(@PathVariable Long id,  RedirectAttributes attributes){
          int res=  commentService.updatecommtoban(id);
          if (res<=0) {
              attributes.addFlashAttribute("message", "屏蔽失败");
          }else {
              attributes.addFlashAttribute("message", "屏蔽成功");
          }

          return "redirect:/admin/comments";
    }

    @GetMapping("/comments/{id}/notban")
    public String notban(@PathVariable Long id,  RedirectAttributes attributes){
        int res=  commentService.updatecommtonotban(id);
        if (res<=0) {
            attributes.addFlashAttribute("message", "解除屏蔽失败");
        }else {
            attributes.addFlashAttribute("message", "解除屏蔽成功");
        }

        return "redirect:/admin/comments";
    }


    @GetMapping("/comments/{id}/look")
    public String look(@PathVariable Long id,  RedirectAttributes attributes){
        int res=  commentService.updatecommtoview(id);
        if (res<=0) {
            attributes.addFlashAttribute("message", "查看失败");
        }else {
            attributes.addFlashAttribute("message", "查看成功");
        }

        return "redirect:/admin/comments";
    }

    @GetMapping("/open")
    public String open(RedirectAttributes attributes){
        Long l = new Long((long)1);
        int i=emailOpenService.toopen(l);
        if (i>0){
            attributes.addFlashAttribute("message", "开启成功");
        }else{
            attributes.addFlashAttribute("message", "开启失败");
        }
        return "redirect:/admin/comments";

    }
    @GetMapping("/close")
    public String close(RedirectAttributes attributes){
        Long l = new Long((long)1);
        int i=emailOpenService.toclose(l);
        if (i>0){
            attributes.addFlashAttribute("message", "关闭成功");
        }else{
            attributes.addFlashAttribute("message", "关闭失败");
        }
        return "redirect:/admin/comments";

    }




}
