package com.myapp.config;

import java.io.IOException;
import java.util.Arrays;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import com.gitee.fastmybatis.core.FastmybatisConfig;
import com.gitee.fastmybatis.core.ext.SqlSessionFactoryBeanExt;
import com.gitee.fastmybatis.core.support.DateFillInsert;
import com.gitee.fastmybatis.core.support.DateFillUpdate;

/**
 * 第二个数据源，后续有第三个数据，复制这个文件，然后改下配置即可
 * @author tanghc
 */
//@Configuration
//@MapperScan(basePackages = { DbSecondConfig.basePackage }, sqlSessionFactoryRef = DbSecondConfig.sqlSessionFactoryName)
public class DbSecondConfig {
    
    /* ********************只需要改这里的配置******************** */
    static final String dbName = "Second";
    /** 配置文件前缀 */
    public static final String prefix = "spring.datasourceSecond";
    /** 存放mapper包路径 */
    public static final String basePackage = "com.app2.dao";
    /** mybatis的config文件路径 */
    public static final String mybatisConfigLocation = "classpath:mybatis/mybatisConfig.xml";
    /** mybatis的mapper文件路径 */
    public static final String mybatisMapperLocations = "classpath:mybatis/mapper2/*.xml";
    /** 表新增时间字段名 */
    public static final String dbInsertDateColumnName = "gmt_create";
    /** 表更新时间字段名 */
    public static final String dbUpdateDateColumnName = "gmt_update";
    /* **************************************************** */
    
    /** 数据源名称 */
    public static final String dataSourceName = "dataSource" + dbName;
    /** sqlSessionTemplate名称 */
    public static final String sqlSessionTemplateName = "sqlSessionTemplate" + dbName;
    /** sqlSessionFactory名称 */
    public static final String sqlSessionFactoryName = "sqlSessionFactory" + dbName;
    /** transactionManager名称 */
    public static final String transactionManagerName = "transactionManager" + dbName;
    /** transactionTemplate名称 */
    public static final String transactionTemplateName = "transactionTemplate" + dbName;

    @Bean(name = dataSourceName)
    @ConfigurationProperties(prefix = prefix) // application.properteis中对应属性的前缀
    public DataSource dataSourceMater() {
        return DataSourceBuilder.create().build();
    }

    public FastmybatisConfig fastmybatisConfig() {
        FastmybatisConfig config = new FastmybatisConfig();
        /*
         * 驼峰转下划线形式，默认是true 开启后java字段映射成数据库字段将自动转成下划线形式 如：userAge -> user_age
         * 如果数据库设计完全遵循下划线形式，可以启用 这样可以省略Entity中的注解，@Table，@Column都可以不用，只留
         * 
         * @Id
         * 
         * @GeneratedValue 参见：UserInfo.java
         */
        config.setCamel2underline(true);
        config.setFills(Arrays.asList(new DateFillInsert(dbInsertDateColumnName),
                new DateFillUpdate(dbUpdateDateColumnName)));

        return config;
    }

    @Bean(name = sqlSessionFactoryName)
    public SqlSessionFactory sqlSessionFactory(@Autowired @Qualifier(dataSourceName) DataSource dataSource) throws Exception {
        Assert.notNull(dataSource, "dataSource can not be null.");
        SqlSessionFactoryBeanExt bean = new SqlSessionFactoryBeanExt();

        bean.setDataSource(dataSource);
        bean.setConfigLocation(this.getResource(mybatisConfigLocation));
        bean.setMapperLocations(this.getResources(mybatisMapperLocations));

        // ====以下是附加属性====

        // dao所在的包名,跟MapperScannerConfigurer的basePackage一致,多个用;隔开
        bean.setBasePackage(basePackage);
        bean.setConfig(fastmybatisConfig());

        return bean.getObject();

    }

    @Bean(name = sqlSessionTemplateName)
    public SqlSessionTemplate sqlSessionTemplate(
            @Autowired @Qualifier(sqlSessionFactoryName) SqlSessionFactory sessionFactory) throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sessionFactory); // 使用上面配置的Factory
        return template;
    }
    
    @Bean(name = transactionManagerName)
    public PlatformTransactionManager annotationDrivenTransactionManager(
            @Autowired @Qualifier(dataSourceName) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
    
    @Bean(name = transactionTemplateName)
    public TransactionTemplate transactionTemplate(@Autowired @Qualifier(transactionManagerName)PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }
    

    private Resource[] getResources(String path) throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        return resolver.getResources(path);
    }

    private Resource getResource(String path) {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        return resolver.getResource(path);
    }

}
