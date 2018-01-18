package com.itiaoling.config.druid;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;

@Configuration
public abstract class AbstractDruidDBConfig {

	private Logger logger = LoggerFactory.getLogger(AbstractDruidDBConfig.class);
	
	public DruidDataSource createDataSource(String url, String username, String password){
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		 dataSource.setDriverClassName("com.mysql.jdbc.Driver");

		// 初始化连接大小
		dataSource.setInitialSize(10);
		// 连接池最大使用连接数量
		dataSource.setMaxActive(100);
		// 最小连接池数量
		dataSource.setMinIdle(10);
		// 获取连接最大等待时间
		dataSource.setMaxWait(60000);
		dataSource.setTestWhileIdle(true);
		// 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
		dataSource.setTimeBetweenEvictionRunsMillis(60000);
		// 配置一个连接在池中最小生存的时间，单位是毫秒
		dataSource.setMinEvictableIdleTimeMillis(25200000);
		dataSource.setTestWhileIdle(true);
		dataSource.setTestOnBorrow(false);
		dataSource.setTestOnReturn(false);
		// 打开removeAbandoned功能
		dataSource.setRemoveAbandoned(true);
		dataSource.setRemoveAbandonedTimeout(1800);
		// 连接时输出错误日志
		dataSource.setLogAbandoned(true);
		// 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。
		dataSource.setPoolPreparedStatements(false);
		try {
			dataSource.setFilters("mergeStat,config,wall");
        } catch (SQLException e) {
            logger.error("druid configuration initialization filter", e);
        }
		dataSource.setConnectionProperties("config.decrypt=false");
		return dataSource;
	}
	
	
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
		return createSqlSessionFactory(dataSource, "classpath:mybatis/*.xml");
	}
	
	
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource, String mapperLocations) throws Exception {
        return createSqlSessionFactory(dataSource, mapperLocations);
    }
	
	
	private SqlSessionFactory createSqlSessionFactory(DataSource dataSource, String mappperLocations) throws Exception{
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		
		PageHelper pageHelper = new PageHelper();
		Properties props = new Properties();
		props.setProperty("dialect", "mysql");
        props.setProperty("reasonable", "true");
        props.setProperty("supportMethodsArguments", "true");
        props.setProperty("returnPageInfo", "check");
        props.setProperty("params", "count=countSql");
        pageHelper.setProperties(props);
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageHelper});
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(mappperLocations));
        return sqlSessionFactoryBean.getObject();
	}
}
