package com.buzhishi.synctool.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.aliyun.alidns20150109.Client;
import com.aliyun.alidns20150109.models.*;
import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.TeaException;
import com.buzhishi.synctool.config.AliyunProperties;
import com.buzhishi.synctool.config.SmsProperties;
import com.buzhishi.synctool.service.AliyunService;
import darabonba.core.client.ClientOverrideConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * 阿里云的相关操作
 *
 * @author <a href="mailto:1309387239@qq.com">zafir zhong</a>
 * @version 1.0.0
 * @date created at 2023/4/15 10:36; update at 2023/4/15 10:36
 */
@Service
@Slf4j
public class AliyunServiceImpl implements AliyunService {
    @Autowired
    private AliyunProperties aliyun;
    @Autowired
    private SmsProperties smsProperties;
    private AsyncClient smsClient;
    private Client dnsClient;

    @PostConstruct
    public void init() throws Exception {
        // Configure Credentials authentication information, including ak, secret, token
        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                .accessKeyId(aliyun.getAccessKey())
                .accessKeySecret(aliyun.getAccessSercet())
                //.securityToken("<your-token>") // use STS token
                .build());
        // Configure the Client
        smsClient = AsyncClient.builder()
                .region("cn-hangzhou") // Region ID
                //.httpClient(httpClient) // Use the configured HttpClient, otherwise use the default HttpClient (Apache HttpClient)
                .credentialsProvider(provider)
                //.serviceConfiguration(Configuration.create()) // Service-level configuration
                // Client-level configuration rewrite, can set Endpoint, Http request parameters, etc.
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride("dysmsapi.aliyuncs.com")
                        //.setConnectTimeout(Duration.ofSeconds(30))
                )
                .build();

        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(aliyun.getAccessKey())
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(aliyun.getAccessSercet());
        // 访问的域名
        config.endpoint = "alidns.cn-hangzhou.aliyuncs.com";
        dnsClient = new com.aliyun.alidns20150109.Client(config);
    }

    @Override
    public void sendSms(Map<String, String> body, String phones) {
        // Parameter settings for API request
        SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                .phoneNumbers(phones)
                .templateCode(smsProperties.getTemplateCode())
                .templateParam(JSONUtil.toJsonStr(body))
                .signName(smsProperties.getSign())
                // Request-level configuration rewrite, can set Http request parameters, etc.
                // .requestConfiguration(RequestConfiguration.create().setHttpHeaders(new HttpHeaders()))
                .build();

        // Asynchronously get the return value of the API request
        CompletableFuture<SendSmsResponse> response = smsClient.sendSms(sendSmsRequest);
        try {
            // Synchronously get the return value of the API request
            SendSmsResponse resp = response.get();
            log.info("sms resp:{}", JSONUtil.toJsonStr(resp));
            // Asynchronous processing of return values
        /*response.thenAccept(resp -> {
            System.out.println(new Gson().toJson(resp));
        }).exceptionally(throwable -> { // Handling exceptions
            System.out.println(throwable.getMessage());
            return null;
        });*/
        } catch (Exception exception) {
            log.error("send sms fail:", exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void updateDNS(String domain, String subDamian, String type, String text) {
        // 获取dns记录
        try {
            DescribeDomainRecordsRequest listReq = new DescribeDomainRecordsRequest();
            listReq.setDomainName(domain)
                    .setRRKeyWord(subDamian);
            DescribeDomainRecordsResponse describeDomainRecordsResponse = dnsClient.describeDomainRecords(listReq);

            boolean hadRecords = false;
            String recordId = null;
            if (describeDomainRecordsResponse != null
                    && describeDomainRecordsResponse.body != null
                    && describeDomainRecordsResponse.body.domainRecords != null) {
                List<DescribeDomainRecordsResponseBody.DescribeDomainRecordsResponseBodyDomainRecordsRecord> records = describeDomainRecordsResponse.body.domainRecords.getRecord();
                if (CollectionUtil.isNotEmpty(records)) {
                    for (DescribeDomainRecordsResponseBody.DescribeDomainRecordsResponseBodyDomainRecordsRecord record : records) {
                        String rr = record.getRR();
                        if (Objects.equals(rr, subDamian)) {
                            hadRecords = true;
                            recordId = record.getRecordId();
                            if (Objects.equals(record.getValue(), text)) {
                                return;
                            }
                            break;
                        }
                    }
                }
            }
            // 检查是否有对应的记录
            if (!hadRecords) {
                // 没有就增加
                AddDomainRecordRequest addReq = new AddDomainRecordRequest();
                addReq.setDomainName(domain)
                        .setRR(subDamian)
                        .setType(type)
                        .setValue(text);
                dnsClient.addDomainRecord(addReq);
                return;
            }
            // 有则修改
            UpdateDomainRecordRequest updateReq = new UpdateDomainRecordRequest();
            updateReq.setRR(subDamian).setRecordId(recordId).setType(type).setValue(text);
            dnsClient.updateDomainRecord(updateReq);
        } catch (Exception exception) {
            log.error("aliyun error:", exception);
            throw new RuntimeException(exception);
        }
    }
}
