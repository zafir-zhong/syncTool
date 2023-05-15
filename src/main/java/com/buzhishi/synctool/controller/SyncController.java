package com.buzhishi.synctool.controller;

import com.buzhishi.synctool.component.IpComponent;
import com.buzhishi.synctool.model.EmailReq;
import com.buzhishi.synctool.service.AliyunService;
import com.buzhishi.synctool.service.OrayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 用于一些同步的入库
 *
 * @author <a href="mailto:1309387239@qq.com">zafir zhong</a>
 * @version 1.0.0
 * @date created at 2023/4/15 10:32; update at 2023/4/15 10:32
 */
@RestController
public class SyncController {
    @Autowired
    private AliyunService aliyunService;
    @Autowired
    private OrayService orayService;
    @Autowired
    private IpComponent ipComponent;

    @PostMapping("/ddns/oray")
    public String ddnsOray(){
        orayService.updateDDNS(ipComponent.getIpNow());
        return "success";
    }
    @PostMapping("/ddns/aliyun")
    public String ddnsAliyun(String domain, String subDamian){
        aliyunService.updateDNS(domain,subDamian,"A",ipComponent.getIpNow());
        return "success";
    }
    @PostMapping("/updateDomainRecord")
    public String updateDomainRecord(String domain, String subDamian, String type, String text){
        aliyunService.updateDNS(domain, subDamian, type, text);
        return "success";
    }
}
