package com.buzhishi.synctool.service;

/**
 * 花生壳相关的操作
 *
 * @author <a href="mailto:1309387239@qq.com">zafir zhong</a>
 * @version 1.0.0
 * @date created at 2023/4/15 10:53; update at 2023/4/15 10:53
 */
public interface OrayService {
    /**
     * 把指定的配置ddns里面的修改成这个记录
     * @param text
     */
    void updateDDNS(String text);
}
