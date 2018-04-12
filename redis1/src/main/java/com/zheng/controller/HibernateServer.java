package com.zheng.controller;

import com.zheng.entity.ClassesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lenovo on 2018/4/12.
 */
@Service
public class HibernateServer {
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Transactional
    public int save(ClassesEntity classesEntity) {
        hibernateTemplate.save(classesEntity);
        return classesEntity.getId();
    }

    @Cacheable(key = "#id", cacheNames = "zhuohua", cacheManager = "cacheManager", condition = "#id==1")
    public ClassesEntity listStudents(Integer id) {
        return hibernateTemplate.get(ClassesEntity.class, id);
    }

    @Transactional
    @CachePut(key = "#id", cacheNames = "zhuohua", cacheManager = "cacheManager")
    public ClassesEntity updateStudent(Integer id) {
        ClassesEntity classesEntity = new ClassesEntity();
        classesEntity.setId(id);
        classesEntity.setClssName("tests");
        hibernateTemplate.update(classesEntity);
        return classesEntity;
    }

    @CacheEvict(key = "#id", cacheNames = "zhuohua")
    @Transactional
    public ClassesEntity deleteStudent(Integer id) {
        ClassesEntity classesEntity = hibernateTemplate.load(ClassesEntity.class, id);
        hibernateTemplate.delete(classesEntity);
        return classesEntity;
    }
}
