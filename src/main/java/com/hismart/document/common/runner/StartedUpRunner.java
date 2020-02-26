package com.hismart.document.common.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Order
@Slf4j
@Component
public class StartedUpRunner implements ApplicationRunner {

    private final ConfigurableApplicationContext context;

    @Autowired
    public StartedUpRunner(ConfigurableApplicationContext context) {
        this.context = context;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (context.isActive()) {
            log.info("HISMART 启动完毕，时间：" + LocalDateTime.now());
        }
    }
}
