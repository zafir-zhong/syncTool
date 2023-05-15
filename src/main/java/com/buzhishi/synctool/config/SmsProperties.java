package com.buzhishi.synctool.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云短信配置
 *
 * @author <a href="mailto:1309387239@qq.com">zafir zhong</a>
 * @version 1
 * @date created at 2023/4/15 10:30; update at 2023/4/15 10:30
 */
@ConfigurationProperties(prefix = "sms")
@Configuration
@Data
public class SmsProperties {
    private String templateCode;
    private String sign;
}
