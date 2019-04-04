package cn.printf.springbootboilerplate.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@ConditionalOnProperty(name = "spring.session.store-type", havingValue = "redis")
@EnableRedisHttpSession(redisNamespace = "${spring.session.redis.namespace}")
//with redisNamespace, we can reuse redis instance for multiple environments
public class RedisHttpSessionConfiguration {

}
