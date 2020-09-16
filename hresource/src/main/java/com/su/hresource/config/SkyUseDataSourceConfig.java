//package com.su.hresource.config;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import com.alibaba.druid.support.http.StatViewServlet;
//import com.alibaba.druid.support.http.WebStatFilter;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author tianyu
// * @date 2020年7月15日15:10:18
// * */
//@Configuration
//public class SkyUseDataSourceConfig {
//
////    @ConfigurationProperties(prefix = "spring.datasource")    //这俩都不好使 我迷了
//    @ConfigurationProperties(prefix = "spring.datasource.druid")
//    @Bean
//    public DataSource druidDataSource() {
//        DruidDataSource druidDataSource = new DruidDataSource();
//        druidDataSource.setUrl("jdbc:oracle:thin:@192.168.1.100:1521:TIENON");
//        druidDataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
//        druidDataSource.setUsername("tienon");
//        druidDataSource.setPassword("Tienon1501B");
//        druidDataSource.setMaxActive(216);
//        druidDataSource.setInitialSize(1);
//        druidDataSource.setMinIdle(3);
//        druidDataSource.setMaxWait(30000L);
//        druidDataSource.setTimeBetweenEvictionRunsMillis(30000L);
//        druidDataSource.setMinEvictableIdleTimeMillis(30000L);
//        druidDataSource.setValidationQuery("SELECT 1 FROM DUAL");
//        druidDataSource.setTestWhileIdle(true);
//        druidDataSource.setTestOnBorrow(false);
//        druidDataSource.setTestOnReturn(false);
//        druidDataSource.setPoolPreparedStatements(true);
//        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
//        return druidDataSource;
//    }
//
//    // 配置一个管理后台的Servlet
//    @Bean
//    public ServletRegistrationBean statViewServlet(){
//        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
//
//        //还可以顺便往map里添加一些配置信息
//        Map<String, String> initParms = new HashMap<String, String>();
//
//        initParms.put("logingUserName","admin");
//        initParms.put("loginPassWord","123456");
//        initParms.put("allow","");
//        initParms.put("deny","172.20.10.14");
//
//        bean.setInitParameters(initParms);
//        return bean;
//    }
//
//    //还要配置一个filter
//    @Bean
//    public FilterRegistrationBean webStatFilter(){
//        FilterRegistrationBean bean = new FilterRegistrationBean();
//        bean.setFilter(new WebStatFilter());
//
//        Map<String, String> initParms = new HashMap<String, String>();
//        initParms.put("exclusions","*.js,*.css,/druid/*");
//
//        bean.setInitParameters(initParms);
//        bean.setUrlPatterns(Arrays.asList("/*"));
//        return bean;
//    }
//
//}
