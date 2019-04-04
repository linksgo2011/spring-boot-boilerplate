package cn.printf.springbootboilerplate.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Ticker;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Map;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.stream.Collectors.toList;

@Slf4j
@EnableCaching
@Configuration
@ConfigurationProperties(prefix = "spring-boot-boilerplate.cache")
public class CacheConfiguration {

    @Getter
    @Setter
    private Map<String, CacheSpec> specs;

    @Primary
    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager manager = new SimpleCacheManager();
        if (specs != null) {
            manager.setCaches(
                specs.entrySet().stream()
                    .map(entry -> buildCache(entry.getKey(), entry.getValue()))
                    .collect(toList())
            );
        }
        return manager;
    }

    private CaffeineCache buildCache(String name, CacheSpec cacheSpec) {
        log.info("Cache {} specified {}", name, cacheSpec);
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
            .expireAfterWrite(cacheSpec.getExpireInMinutes(), MINUTES)
            .ticker(ticker())
            .maximumSize(cacheSpec.getMaxElements());
        if (cacheSpec.recordStats) {
            caffeine.recordStats();
        }
        return new CaffeineCache(name, caffeine.build(), cacheSpec.isAllowNullValues());
    }

    @Bean
    public Ticker ticker() {
        return Ticker.systemTicker();
    }


    @Data
    public static class CacheSpec {
        private int expireInMinutes = 60;
        private int maxElements = 1024; //by default
        private boolean recordStats = false;
        private boolean allowNullValues = true; // allow null is especially useful for scenario evaluations
    }
}
