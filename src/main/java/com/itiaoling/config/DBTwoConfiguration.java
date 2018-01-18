package com.itiaoling.config;

import java.sql.SQLException;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.itiaoling.config.druid.AbstractDruidDBConfig;

@Configuration
@EnableTransactionManagement
@RefreshScope
public class DBTwoConfiguration extends AbstractDruidDBConfig{

	@Value("${spring.two.datasource.url}")
    private String url;

    @Value("${spring.two.datasource.username}")
    private String username;

    @Value("${spring.two.datasource.password}")
    private String password;
    
    @RefreshScope
    @Bean(name = "datasourceTwo", initMethod = "init", destroyMethod = "close")
	public DruidDataSource dataSource(){
		return super.createDataSource(url, username, password);
	}
    
    @Bean(name = "sqlSessionFactoryTwo")
    public SqlSessionFactory sqlSessionFactory() throws Exception{
    	return super.sqlSessionFactory(dataSource());
    }
    
    @Bean
    public PlatformTransactionManager transactionManager() throws SQLException {
        return new DataSourceTransactionManager(dataSource());
    }
}
