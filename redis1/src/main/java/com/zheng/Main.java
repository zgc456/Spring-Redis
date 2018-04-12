package com.zheng;

import com.zheng.config.RedisConfig;
import com.zheng.controller.HibernateServer;
import com.zheng.entity.ClassesEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;



/**
 * Created by lenovo on 2018/4/10.
 */

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext=new AnnotationConfigApplicationContext(RedisConfig.class);
        HibernateServer hibernateServer=annotationConfigApplicationContext.getBean(HibernateServer.class);
       hibernateServer.listStudents(1);
    }
}
