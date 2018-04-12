package com.zheng.config;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import java.util.Properties;
@Configuration
@ComponentScan("com.zheng")
@EnableTransactionManagement
public class HibernateConfig {
    @Autowired
    private JdbcConfig jdbcConfig;
    @Bean
    public HikariDataSource dataSource(){
        HikariDataSource hikariDataSource=new HikariDataSource();
        hikariDataSource.setDriverClassName(jdbcConfig.getDriverClassName());
        hikariDataSource.setJdbcUrl(jdbcConfig.getUrl());
        hikariDataSource.setPassword(jdbcConfig.getPassword());
        hikariDataSource.setUsername(jdbcConfig.getUsername());
        return hikariDataSource;
    }
    @Bean
    public LocalSessionFactoryBean localSessionFactoryBean(){
        LocalSessionFactoryBean localSessionFactoryBean=new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource());
        Properties properties=new Properties();
        properties.setProperty("hibernate.dialect","org.hibernate.dialect.MySQL5InnoDBDialect");
        properties.setProperty("hibernate.format_sql","true");
        properties.setProperty("hibernate.show_sql","true");
        properties.setProperty("hibernate.hbm2ddl.auto","update");
        localSessionFactoryBean.setHibernateProperties(properties);
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver=new PathMatchingResourcePatternResolver();
        localSessionFactoryBean.setMappingLocations(pathMatchingResourcePatternResolver.getResource("hibernate.hbm.xml"));
       return localSessionFactoryBean;
    }

    @Autowired
    SessionFactory localSessionFactoryBean;
    @Bean
    public HibernateTemplate hibernateTemplate(){
        HibernateTemplate hibernateTemplate=new HibernateTemplate(localSessionFactoryBean);
        return hibernateTemplate;
    }
}
