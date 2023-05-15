package com.buzhishi.synctool.service;

import java.util.List;

/**
 * 邮件发送相关的配置
 *
 * @author <a href="mailto:1309387239@qq.com">zafir zhong</a>
 * @version 1.0.0
 * @date created at 2023/4/15 10:55; update at 2023/4/15 10:55
 */
public interface EmailSerivce {

    /**
     * 发送邮件的内容
     * @param subject 主题
     * @param content 内容
     * @param emails 接收人
     */
    void sendEmail(String subject, String content, List<String> emails);
}
