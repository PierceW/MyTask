package com.yks.phoenix.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class Phoenix1DataSource {
    @Autowired
    private Environment env;

    @Bean(name = "phoenixJdbcDataSource")
    @Qualifier("phoenixJdbcDataSource")
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(env.getProperty("phoenix1.url"));
        dataSource.setDriverClassName(env.getProperty("phoenix1.driver-class-name"));
        dataSource.setUsername(env.getProperty("phoenix1.username"));//phoenix1的用户名默认为空
        dataSource.setPassword(env.getProperty("phoenix1.password"));//phoenix1的密码默认为空
        dataSource.setDefaultAutoCommit(Boolean.valueOf(env.getProperty("phoenix1.default-auto-commit")));
        return dataSource;
    }

    @Bean(name = "phoenixJdbcTemplate")
    public JdbcTemplate phoenix1JdbcTemplate(@Qualifier("phoenixJdbcDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
    
    
    
    
}
