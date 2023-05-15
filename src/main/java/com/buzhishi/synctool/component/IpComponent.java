package com.buzhishi.synctool.component;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.net.Ipv4Util;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.buzhishi.synctool.config.DdnsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.net.util.IPAddressUtil;

import java.net.InetAddress;
import java.util.List;

/**
 * ip获取的帮助工具
 *
 * @author <a href="mailto:1309387239@qq.com">zafir zhong</a>
 * @version 1.0.0
 * @date created at 2023/4/15 10:41; update at 2023/4/15 10:41
 */
@Component
@Slf4j
public class IpComponent {
    @Autowired
    private DdnsProperties properties;

    public String getIpNow(){
        List<String> ipUrl = properties.getIpUrl();
        if(CollectionUtil.isNotEmpty(ipUrl)){
            for (String ip : ipUrl) {
                if(StrUtil.isEmpty(ip)){
                    continue;
                }
                try {
                    HttpResponse resp = HttpRequest.get(ip).execute();
                    if(!resp.isOk()){
                        continue;
                    }
                    String body = resp.body();
                    log.info("query local network ip:{}", body);
                    if(body.length() > 15){
                       continue;
                    }
                    if(body.startsWith("192") || body.startsWith("127")){
                        continue;
                    }
                    return body;
                }catch (Exception exception){
                    log.error("query local network ip error:",exception);
                }
            }
        }
        throw new RuntimeException("error query ip");
    }

}
