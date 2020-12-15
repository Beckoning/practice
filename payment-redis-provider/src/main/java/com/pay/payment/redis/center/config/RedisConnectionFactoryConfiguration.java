////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.pay.payment.redis.com.pay.payment.center.config;
//
//import java.util.ArrayList;
//import java.util.List;
//import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisClusterConfiguration;
//import org.springframework.data.redis.connection.RedisConfiguration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
//
//@Configuration
//public class RedisConnectionFactoryConfiguration extends CachingConfigurerSupport {
//    @Value("${spring.redis.cluster.nodes}")
//    private String clusterNodes;
//    @Value("${spring.redis.cluster.max-redirects}")
//    private int maxRedirects;
//    @Value("${spring.redis.is-cluster:true}")
//    private boolean isCluster;
//    @Value("${spring.redis.host:127.0.0.1}")
//    private String host;
//    @Value("${spring.redis.port:6379}")
//    private int port;
//    @Value("${spring.redis.password:}")
//    private String password;
//
//    public RedisConnectionFactoryConfiguration() {
//    }
//
//    @Bean
//    public RedisConfiguration redisConfiguration() {
//        if (this.isCluster) {
//            List<String> nodes = new ArrayList();
//            nodes.add(this.clusterNodes);
//            RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(nodes);
//            redisClusterConfiguration.setMaxRedirects(this.maxRedirects);
//            return redisClusterConfiguration;
//        } else {
//            RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(this.host, this.port);
//            redisStandaloneConfiguration.setPassword(this.password);
//            return redisStandaloneConfiguration;
//        }
//    }
//
//    @Bean
//    @ConfigurationProperties(
//        prefix = "spring.redis.lettuce.pool"
//    )
//    public GenericObjectPoolConfig genericObjectPoolConfig() {
//        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
//        return poolConfig;
//    }
//
//    @Bean
//    public LettuceClientConfiguration lettuceClientConfiguration(GenericObjectPoolConfig genericObjectPoolConfig) {
//        return LettucePoolingClientConfiguration.builder().poolConfig(genericObjectPoolConfig).build();
//    }
//
//    @Bean
//    public RedisConnectionFactory connectionFactory(RedisConfiguration redisConfiguration, LettuceClientConfiguration lettuceClientConfiguration) {
//        return new LettuceConnectionFactory(redisConfiguration, lettuceClientConfiguration);
//    }
//}
