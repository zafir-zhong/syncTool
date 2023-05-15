package com.buzhishi.synctool.service.impl;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.buzhishi.synctool.config.DdnsProperties;
import com.buzhishi.synctool.service.OrayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Objects;

/**
 * 花生壳相关的操作
 *
 * @author <a href="mailto:1309387239@qq.com">zafir zhong</a>
 * @version 1.0.0
 * @date created at 2023/4/15 10:53; update at 2023/4/15 10:53
 */
@Service
@Slf4j
public class OrayServiceImp implements OrayService {
    @Autowired
    private DdnsProperties ddnsProperties;
    @Override
    public void updateDDNS(String text) {
        UrlBuilder of =
                UrlBuilder.of(ddnsProperties.getOrayUrl(), Charset.defaultCharset())
                        .addQuery("myip", text);
        HttpResponse resp = HttpRequest.get(of.build()).header("Authorization","Basic "+ddnsProperties.getOrayAuth()).execute();
        log.info("oray ddns resp:{}",resp);
        if(!resp.isOk()){
            throw new RuntimeException(resp.toString());
        }
        String body = resp.body();
        if(StrUtil.isEmpty(body)){
            throw new RuntimeException(body);
        }
        if(!StrUtil.contains(body,"good")
                && !StrUtil.contains(body,"nochg")){
            throw new RuntimeException(body);
        }
    }
}
