package com.buzhishi.synctool.service.impl;

import com.buzhishi.synctool.service.EmailSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 邮件发送相关的配置
 *
 * @author <a href="mailto:1309387239@qq.com">zafir zhong</a>
 * @version 1.0.0
 * @date created at 2023/4/15 10:55; update at 2023/4/15 10:55
 */
@Service
public class EmailSerivceImpl implements EmailSerivce {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username:}")
    private String from;

    @Override
    public void sendEmail(String subject, String content, List<String> emails) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        simpleMailMessage.setFrom(from);
        String[] emailsStr = emails.toArray(new String[]{});
        simpleMailMessage.setTo(emailsStr);
        javaMailSender.send(simpleMailMessage);
    }
}
