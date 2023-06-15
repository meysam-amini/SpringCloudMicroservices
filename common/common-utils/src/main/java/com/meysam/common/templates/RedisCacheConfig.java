package com.meysam.common.templates;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

/**
 * @author GS
 * @date 2017年12月29日
 */
@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {

    @Value("${spring.redis.host}")
    private String REDIS_HOST;
    @Value("${spring.redis.port}")
    private int REDIS_PORT;
    @Value("${spring.redis.password}")
    private String REDIS_PASSWORD;
    @Value("${spring.redis.connect-timeout}")
    private Long JEDDIS_CONNECTION_TIME_OUT;
    @Value("${spring.redis.timeout}")
    private Long JEDDIS_READ_TIME_OUT;


    /*@Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(1800))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer(Object.class)));
    }*/

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
        redisStandaloneConfiguration.setPassword(RedisPassword.of(REDIS_PASSWORD));

        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
        jedisClientConfiguration.connectTimeout(Duration.ofSeconds(JEDDIS_CONNECTION_TIME_OUT));
        jedisClientConfiguration.readTimeout(Duration.ofSeconds(JEDDIS_READ_TIME_OUT));

        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory(redisStandaloneConfiguration,
                jedisClientConfiguration.build());

        return jedisConFactory;
    }

}
