 package com.bosssoft.dynamic.form.util;

import java.text.MessageFormat;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.druid.pool.DruidDataSource;
import com.bosssoft.dynamic.form.domain.DataSourceInfo;
import com.bosssoft.dynamic.form.mapper.DataSourceInfoMapper;
import com.bosssoft.dynamic.form.model.BasicConstant;
import com.bosssoft.dynamic.form.model.DataSourceElement;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification; 
/**
  * 数据源连接管理类
  * @author huangxw
  * @date 2020/01/05
  */
@Component
 public class ConnectedDataSourceHandler {
    
    private static Logger logger=LoggerFactory.getLogger(ConnectedDataSourceHandler.class); 


    private static DataSourceInfoMapper dataSourceInfoMapper;
    
    @Autowired
    private  DataSourceInfoMapper mapper;
     /**
      * 数据源对象的缓存
      */
     private static Cache<Long, DataSource> dataSourceCache;
     
     static{
         dataSourceCache = CacheBuilder.newBuilder()  
             //设置cache的初始大小为10，要合理设置该值  
             .initialCapacity(10)  
             //设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作  
             .concurrencyLevel(5)  
             .removalListener(new RemovalListener<Long, DataSource>() {

                @Override
                public void onRemoval(RemovalNotification<Long, DataSource> notification) {
                    logger.info("remove dataSource,id="+notification.getKey());
                 ((DruidDataSource)notification.getValue()).close();
                     
                }})
             //.expireAfterWrite(10, TimeUnit.SECONDS)
             //构建cache实例  
             .build();  

     }
     
     /**
      * 缓存中存在直接返回，不存在的话进行加载并且缓存
      * @param dataSourceId
      * @return
      * @throws ExecutionException
      */
     public  static DataSource applyDataSource(Long dataSourceId) throws ExecutionException{
         return dataSourceCache.get(dataSourceId, new Callable<DataSource>() {

            @Override
            public DataSource call() throws Exception {
               DataSourceInfo dataSourceInfo= dataSourceInfoMapper.selectByPrimaryKey(dataSourceId);
                 return createDataSource(dataSourceInfo);
            }});
     }
     
     /**
      * 从缓存中移除指定的数据源对象
      * @param dataSourceId
      */
     public static void removeDataSource(Long dataSourceId){
         dataSourceCache.invalidate(dataSourceId);
     }
     
     /**
      * 创建数据源
      * 
      * @param dataSourceInfo
      * @return
     * @throws Exception 
      */
     public static DataSource createDataSource(DataSourceInfo dataSourceInfo) throws Exception {
         logger.info("start createDataSource");
         String jdbcUrl = getJDBCUrl(dataSourceInfo.getDbType(), dataSourceInfo.getHostIp(), dataSourceInfo.getDbPort(),
             dataSourceInfo.getDbInstance());

         DruidDataSource druidDataSource=new DruidDataSource();
         druidDataSource.setDriverClassName(dataSourceInfo.getDbDriver());
         druidDataSource.setUrl(jdbcUrl);
         druidDataSource.setUsername(dataSourceInfo.getDbUsername());
         druidDataSource.setPassword(SecurityUtils.desEncrypt(dataSourceInfo.getDbPassword()));
         druidDataSource.setConnectionErrorRetryAttempts(BasicConstant.DATASOURCE_CONNECTIONERROR_RETRYS);
         druidDataSource.setNotFullTimeoutRetryCount(BasicConstant.DATASOURCE_TIMEOUT_RETRYS);
         druidDataSource.setFailFast(true);
         
         //允许读取注释信息
         Properties properties = new Properties();
         properties.setProperty("remarks", "true");
         properties.setProperty("useInformationSchema", "true");
         druidDataSource.setConnectProperties(properties);
         
         return druidDataSource;
     }
     
     public static String getJDBCUrl(String dataSourceMateKey, String ip, String port, String instanceName) {
        DataSourceElement dataSourceElement = SupportDataSourceHandler.getSupportDataSourceByMateKey(dataSourceMateKey);
        String jdbcUrl = MessageFormat.format(dataSourceElement.getUrlTemplate(), ip, port, instanceName);
        return jdbcUrl;
    }

    @PostConstruct
     public void init(){
         dataSourceInfoMapper = mapper;
     }
}
