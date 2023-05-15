package com.buzhishi.synctool.service;

import java.util.Map;

/**
 * 阿里云的相关操作
 *
 * @author <a href="mailto:1309387239@qq.com">zafir zhong</a>
 * @version 1.0.0
 * @date created at 2023/4/15 10:36; update at 2023/4/15 10:36
 */
public interface AliyunService {

    /**
     * 使用已有的模板发送指定内容的消息
     * @param body 具体内容
     * @param phones 手机号，逗号隔开
     */
    void sendSms(Map<String,String> body,String phones);

    /**
     * 修改域名映射
     *
     * @param domain    一级域名
     * @param subDamian 二级域名
     * @param type
     * @param text      修改成的实际A记录
     */
    void updateDNS(String domain, String subDamian, String type, String text);
}
