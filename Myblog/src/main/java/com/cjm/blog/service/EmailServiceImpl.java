package com.cjm.blog.service;

import com.cjm.blog.email.EmailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private EmailConfig emailConfig;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;


    @Override
    public void sendStringEmail(String receiverName, String title, String content) {

    }

    @Override
    public void sendBigEmail(String receiverName, String title, String content, File file) {

    }

    @Override
    public void sendTemplateEmail(String receiverName, String title, String information) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom(emailConfig.getEmailFrom());
            helper.setTo(receiverName);
            helper.setSubject(title);

            Context context = new Context();
            String thymeleafHtml = templateEngine.process("thymeleafTemplate", context);
            helper.setText(thymeleafHtml,true);

        }catch (Exception e){
            e.printStackTrace();
        }

        mailSender.send(message);
    }


    }

