# 这个是一个简单的同步工具服务
## ddns功能
- 花生壳：
1. 类方法：``com.buzhishi.synctool.controller.SyncController.ddnsOray``
2. 逻辑：通过读取当前服务的外网ip调用花生壳的ddns方法写入
3. 配置：
   1. 获取ip的url  ``ddns.ip-url``
   2. 请求花生壳的url ``ddns.oray-url``
   3. 请求头，具体为base64的``username:password`` ``ddns.oray-auth``
4. 请求示例：``curl --location --request POST "http://127.0.01:6666/ddns/oray"``
- 阿里云
1. 类方法：``com.buzhishi.synctool.controller.SyncController.ddnsAliyun``
2. 逻辑：通过读取当前服务的外网ip，调用阿里云的解析查询和解析更新方法，把对于域名的a记录写入阿里云中
3. 配置：
   1. 阿里云的accesskey ``aliyun.access-key``
   2. 阿里云的access-sercet ``aliyun.access-sercet``
   3. 获取ip的url  ``ddns.ip-url``
4. 请求示例：``curl --location --request POST "http://192.168.3.201:6666/ddns/aliyun?domain=buzhishi.com&subDamian=test"``
## 修改解析记录
1. 类方法：``com.buzhishi.synctool.controller.SyncController.updateDomainRecord``
2. 逻辑：通过读取参数，调用阿里云的解析查询和解析更新方法，把对于域名的记录写入阿里云中
3. 配置：
    1. 阿里云的accesskey ``aliyun.access-key``
    2. 阿里云的access-sercet ``aliyun.access-sercet``
4. 请求示例：``curl --location --request POST "http://127.0.0.1:6666/updateDomainRecord?domain=buzhishi.com&subDamian=test&type=A&text=127.0.0.1"``
## 阿里云发送短信
1. 类方法：``com.buzhishi.synctool.controller.SendController.sendSMS``
2. 逻辑：使用阿里云的发送模板短信进行短信发送
3. 配置：
    1. 阿里云的accesskey ``aliyun.access-key``
    2. 阿里云的access-sercet ``aliyun.access-sercet``
    3. 阿里云短信的签名 ``sms.sign``
    4. 阿里云的短信的模板id ``sms.template-code``
4. 请求示例：``curl --location --request POST "http://127.0.0.1:6666/send/sms?phones=13077598960" -d '{"code":"123456"}'``
## 发送邮件
1. 类方法：``com.buzhishi.synctool.controller.SendController.sendEmail``
2. 逻辑：使用smtp进行邮件的发送
3. 配置：
    1. 邮箱配置：``spring.mail.*``
4. 请求示例：``curl --location --request POST "http://127.0.0.1:6666/send/email" -d '{"emails":["1309387239@qq.com"],"sub":"test-title","content":"message"}'``

