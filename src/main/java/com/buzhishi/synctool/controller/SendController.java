package com.buzhishi.synctool.controller;

import com.buzhishi.synctool.model.EmailReq;
import com.buzhishi.synctool.service.AliyunService;
import com.buzhishi.synctool.service.EmailSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 用于发送消息的控制器
 *
 * @author <a href="mailto:1309387239@qq.com">zafir zhong</a>
 * @version 1.0.0
 * @date created at 2023/4/15 10:33; update at 2023/4/15 10:33
 */
@RestController
@RequestMapping("/send")
public class SendController {
    @Autowired
    private AliyunService aliyunService;
    @Autowired
    private EmailSerivce emailSerivce;
    @PostMapping("/sms")
    public String sendSMS(@RequestBody Map<String,String> body,String phones){
        aliyunService.sendSms(body,phones);
        return "success";
    }
    @PostMapping("/email")
    public String sendEmail(@RequestBody EmailReq req){
        emailSerivce.sendEmail(req.getSubject(),req.getContent(),req.getEmails());
        return "success";
    }

}
