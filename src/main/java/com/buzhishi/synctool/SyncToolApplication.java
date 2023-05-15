package com.buzhishi.synctool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@Slf4j
public class SyncToolApplication {
    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(SyncToolApplication.class);
        builder.headless(false).run(args);
        log.info("system log4j2.configurationFile:{}",System.getProperty("log4j2.configurationFile"));
    }
}