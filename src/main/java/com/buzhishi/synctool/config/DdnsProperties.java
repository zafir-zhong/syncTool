package com.buzhishi.synctool.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author <a href="mailto:1309387239@qq.com">zafir zhong</a>
 * @date created at 2023/4/15 10:26; update at 2023/4/15 10:26
 */
@ConfigurationProperties(prefix = "ddns")
@Configuration
@Data
public class DdnsProperties {

    private String orayUrl;
    private String orayAuth;
    private List<String> ipUrl;

}
