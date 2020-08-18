package com.cjm.blog.controller;


import com.cjm.blog.po.Comment;
import com.cjm.blog.po.EmailServer;
import com.cjm.blog.po.User;
import com.cjm.blog.service.CommentService;
import com.cjm.blog.service.IBlogService;
import com.cjm.blog.service.IEmailOpenService;
import com.cjm.blog.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
public class CommentController {

    private ExecutorService executor = Executors.newCachedThreadPool() ;



    @Autowired
    private IEmailOpenService emailOpenService;

    @Autowired
    private IEmailService emailService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private IBlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;



    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }


    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        User user = (User) session.getAttribute("user");
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
            comment.setHaveView(true);
        } else {
            comment.setAvatar(avatar);
            comment.setHaveView(false);
        }
        comment.setBan(false);

        //先判断是否开了邮件服务

        Long l = new Long((long)1);
        EmailServer e= emailOpenService.findopen(l);
        Thread thread=null;
        if (e.isOpen()){ //开启了邮件服务
            try {
                String comm=comment.getEmail();
                if (comm !=null){
                    Comment res=commentService.findByEmail(comm);
                    if(res==null){  //第一次评论
                        //调用邮件发送接口
                        //因为邮件调用api执行的时间太长，采用多线程解决
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(5000);
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                                //Thread.sleep(5000);
                                emailService.sendTemplateEmail(comm,"Thank You For Your Comment","thymeleafTemplate.html");
                            }
                        });

                    }
                }

            }catch (Exception ex){
                ex.printStackTrace();
                System.out.println("message="+ex.getMessage());
                System.out.println("ex="+e);
            }


        }

        if (thread!=null) thread.start();


        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }
}
