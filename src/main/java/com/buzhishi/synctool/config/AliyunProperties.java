package com.buzhishi.synctool.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云的配置项
 *
 * @author <a href="mailto:1309387239@qq.com">zafir zhong</a>
 * @version 1
 * @date created at 2023/4/15 10:23; update at 2023/4/15 10:23
 */
@Configuration
@ConfigurationProperties(prefix = "aliyun")
@Data
public class AliyunProperties {

    private String accessKey;

    private String accessSercet;

}
