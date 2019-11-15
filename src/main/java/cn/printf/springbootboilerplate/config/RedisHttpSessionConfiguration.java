package cn.printf.springbootboilerplate.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@ConditionalOnProperty(name = "spring.session.store-type", havingValue = "redis")
@EnableRedisHttpSession(redisNamespace = "${spring.session.redis.namespace}")
public class RedisHttpSessionConfiguration {

}
