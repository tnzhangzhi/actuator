package com.ymkj.im.config.durid;


import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import javax.sql.DataSource;


/**
 * Created by zz1987 on 2018/3/14 0014.
 */
@Configuration
@EnableConfigurationProperties(DruidProperties.class)
@ConditionalOnClass(DruidDataSource.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
public class DruidConfig {

    @Value("${druid.ds.url}")
    private String crawerUrl;

    @Value("${druid.ds.username}")
    private String crawerUsername;

    @Value("${druid.ds.password}")
    private String crawerPassword;


    @Autowired
    private DruidProperties druidProperties;

    @Bean(name="dataSource")
    public DataSource dataSource(){
        DruidDataSource crawer = new DruidDataSource();
        crawer.setUrl(crawerUrl);
        crawer.setUsername(crawerUsername);
        crawer.setPassword(crawerPassword);
        crawer.setTestOnBorrow(druidProperties.isTestOnBorrow());
        crawer.setDriverClassName(druidProperties.getDriverClass());

        if(druidProperties.getInitialSize()>0){
            crawer.setInitialSize(druidProperties.getInitialSize());
        }
        if(druidProperties.getMinIdle()>0){
            crawer.setMinIdle(druidProperties.getMinIdle());
        }
        if(druidProperties.getMaxActive()>0){
            crawer.setMaxActive(druidProperties.getMaxActive());
        }
        return crawer;
    }

//    @Bean(name = "transactionManager")
//    public DataSourceTransactionManager transactionManager() {
//        return new DataSourceTransactionManager(dataSource());
//    }

}
