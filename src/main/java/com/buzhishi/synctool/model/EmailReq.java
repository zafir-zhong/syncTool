package com.buzhishi.synctool.model;

import lombok.Data;

import java.util.List;

/**
 * email的请求体
 *
 * @author <a href="mailto:1309387239@qq.com">zafir zhong</a>
 * @version 1.0.0
 * @date created at 2023/4/15 12:31; update at 2023/4/15 12:31
 */
@Data
public class EmailReq {
    private String subject;
    private String content;
    private List<String> emails;
}
