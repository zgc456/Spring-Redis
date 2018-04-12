package com.zheng.config;


import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
@ComponentScan("com.zheng")
@Configuration
@Import({JdbcConfig.class, HibernateConfig.class})
@EnableCaching
public class RedisConfig {
    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration(){
        RedisStandaloneConfiguration redisStandaloneConfiguration=new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(0);
        redisStandaloneConfiguration.setHostName("localhost");
        redisStandaloneConfiguration.setPassword(RedisPassword.of("123456"));
        redisStandaloneConfiguration.setPort(6379);
        return redisStandaloneConfiguration;
    }
    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        JedisConnectionFactory jedisConnectionFactory=new JedisConnectionFactory(redisStandaloneConfiguration());
        return jedisConnectionFactory;
    }
    @Bean
  public StringRedisTemplate  stringRedisTemplate(){
      StringRedisTemplate redisTemplate=new StringRedisTemplate(jedisConnectionFactory());
      return redisTemplate;
  }
  @Bean
  public RedisCacheManager cacheManager(){
      RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().
              serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
                      new GenericJackson2JsonRedisSerializer()));
      return RedisCacheManager.builder(jedisConnectionFactory()).
              transactionAware().cacheDefaults(cacheConfiguration).build();
  }
  //@Cacheable(key = "#id",cacheNames = "student",cacheManager = "cacheManager")

}
