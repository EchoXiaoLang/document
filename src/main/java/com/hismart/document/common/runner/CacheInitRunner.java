package com.hismart.document.common.runner;

import com.hismart.document.common.exception.RedisConnectException;
import com.hismart.document.modules.system.manager.UserManager;
import com.hismart.document.modules.system.service.UserService;
import com.hismart.document.common.service.CacheService;
import com.hismart.document.modules.system.entity.SinkUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 缓存初始化
 */
@Slf4j
@Component
public class CacheInitRunner implements ApplicationRunner {

    private final CacheService cacheService;

    private final ConfigurableApplicationContext context;
    private final UserService userService;
    private final UserManager userManager;

    public CacheInitRunner(CacheService cacheService, ConfigurableApplicationContext context, UserService userService, UserManager userManager) {
        this.cacheService = cacheService;
        this.context = context;
        this.userService = userService;
        this.userManager = userManager;
    }

    @Override
    public void run(ApplicationArguments args) {
        try {
            log.info("Redis连接中 ······");
            cacheService.testConnect();
            log.info("缓存初始化 ······");
            log.info("缓存用户数据 ······");
            List<SinkUser> list = this.userService.list();
            for (SinkUser user : list) {
                userManager.loadUserRedisCache(user);
            }
        } catch (Exception e) {
            log.error(" ____   __    _   _ ");
            log.error("| |_   / /\\  | | | |");
            log.error("|_|   /_/--\\ |_| |_|__");
            log.error("                        ");
            log.error("HISMART启动失败 ，{}", e.getMessage());
            if (e instanceof RedisConnectException) {
                log.error("Redis连接异常，请检查Redis连接配置并确保Redis服务已启动");
            }
            // 关闭 HISMART
            context.close();
        }
    }
}
