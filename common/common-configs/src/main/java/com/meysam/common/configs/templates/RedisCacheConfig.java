package com.meysam.common.configs.templates;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;


@Configuration
//@EnableCaching
public class RedisCacheConfig {

    @Value("${spring.data.redis.host}")
    private String REDIS_HOST="localhost";
    @Value("${spring.data.redis.port}")
    private int REDIS_PORT=6379;
//    @Value("${spring.data.redis.password}")
//    private String REDIS_PASSWORD;
    @Value("${spring.data.redis.connect-timeout}")
    private Long REDIS_CONNECTION_TIME_OUT =10000L;
    @Value("${spring.data.redis.timeout}")
    private Long REDIS_READ_TIME_OUT =10000L;
    @Value("${spring.data.redis.jedis.pool.min-idle}")
    private int JEDIS_MIN_IDL=10000;
    @Value("${spring.data.redis.jedis.pool.max-idle}")
    private int JEDIS_MAX_IDL=10000;


    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }


    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(REDIS_HOST);
        redisStandaloneConfiguration.setPort(REDIS_PORT);
        redisStandaloneConfiguration.setDatabase(0);
//        redisStandaloneConfiguration.setPassword(RedisPassword.of(REDIS_PASSWORD));

        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
        jedisClientConfiguration.connectTimeout(Duration.ofSeconds(REDIS_CONNECTION_TIME_OUT));
        jedisClientConfiguration.readTimeout(Duration.ofSeconds(REDIS_READ_TIME_OUT));

        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory(redisStandaloneConfiguration,
                jedisClientConfiguration.build());
        jedisConFactory.getPoolConfig().setMinIdle(JEDIS_MIN_IDL);
        jedisConFactory.getPoolConfig().setMaxIdle(JEDIS_MAX_IDL);
        return jedisConFactory;
    }

}
