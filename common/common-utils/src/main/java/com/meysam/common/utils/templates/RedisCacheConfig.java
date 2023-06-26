package com.meysam.common.utils.templates;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;


@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {

//    @Value("${spring.data.redis.host}")
    private String REDIS_HOST="localhost";
//    @Value("${spring.data.redis.port}")
    private int REDIS_PORT=6379;
//    @Value("${spring.redis.password}")
//    private String REDIS_PASSWORD;
//    @Value("${spring.data.redis.connect-timeout}")
    private Long JEDDIS_CONNECTION_TIME_OUT=10000L;
//    @Value("${spring.data.redis.timeout}")
    private Long JEDDIS_READ_TIME_OUT=10000L;


    /*@Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(1800))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer(Object.class)));
    }*/


//    @Bean
//    JedisConnectionFactory jedisConnectionFactory() {
//        return new JedisConnectionFactory();
//    }

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
        jedisClientConfiguration.connectTimeout(Duration.ofSeconds(JEDDIS_CONNECTION_TIME_OUT));
        jedisClientConfiguration.readTimeout(Duration.ofSeconds(JEDDIS_READ_TIME_OUT));

        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory(redisStandaloneConfiguration,
                jedisClientConfiguration.build());

        return jedisConFactory;
    }

}
