package com.hismart.document.common.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 主要用于定时删除 Redis中 key为 hismart.user.active 中
 * 已经过期的 score
 */
@Slf4j
@Component
public class CacheTask {

  /*  private final RedisService redisService;

    @Autowired
    public CacheTask(RedisService redisService) {
        this.redisService = redisService;
    }

    @Scheduled(fixedRate = 3600000)
    public void run() {
        try {
            String now = DateUtil.formatFullTime(LocalDateTime.now());
            redisService.zremrangeByScore(HismartConstant.ACTIVE_USERS_ZSET_PREFIX, "-inf", now);
            log.info("delete expired user");
        } catch (Exception ignore) {
        }
    }*/
}
