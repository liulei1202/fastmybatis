package com.gitee.fastmybatis.core.ext;

import com.gitee.fastmybatis.core.FastmybatisConfig;
import com.gitee.fastmybatis.core.ext.exception.DatabaseConnectException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import static org.springframework.util.Assert.notNull;


/**
 * SqlSessionFactoryBean扩展
 *
 * @author tanghc
 */
public class SqlSessionFactoryBeanExt extends SqlSessionFactoryBean {

    private static final Log LOG = LogFactory.getLog(SqlSessionFactoryBeanExt.class);

    private MapperLocationsBuilder mapperLocationsBuilder = new MapperLocationsBuilder();

    private Resource[] mapperLocations;

    private Resource[] finalMapperLocations;

    private volatile SqlSessionFactory sqlSessionFactory;

    private String basePackage;

    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
        String dbName = buildDbName(dataSource);
        mapperLocationsBuilder.setDbName(dbName);
    }

    @Override
    public void setMapperLocations(Resource[] mapperLocations) {
        this.mapperLocations = mapperLocations;
        mapperLocationsBuilder.storeMapperFile(mapperLocations);
    }

    @Override
    protected SqlSessionFactory buildSqlSessionFactory() throws IOException {
        notNull(this.basePackage, "属性 'basePackage' 必填");

        Resource[] allMapperLocations = mapperLocationsBuilder.build(this.basePackage);
        this.finalMapperLocations = allMapperLocations;
        // 重新设置mapperLocation属性
        super.setMapperLocations(allMapperLocations);
        this.sqlSessionFactory = super.buildSqlSessionFactory();
        return this.sqlSessionFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        new HotDeploy(this).start();
    }

    /**
     * @param basePackage 指定哪些包需要被扫描,支持多个包"package.a,package.b"并对每个包都会递归搜索
     */
    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public void setConfig(FastmybatisConfig config) {
        mapperLocationsBuilder.setConfig(config);
    }

    public FastmybatisConfig getConfig() {
        return mapperLocationsBuilder.getConfig();
    }

    /**
     * 获取数据库类型
     */
    private static String buildDbName(DataSource dataSource) {
        if (dataSource == null) {
            throw new NullPointerException("dataSource 不能为null");
        }
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            DatabaseMetaData metaData = conn.getMetaData();
            String dbName = metaData.getDatabaseProductName();
            LOG.info("数据库名称：" + dbName);
            return dbName;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new DatabaseConnectException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }
    }

    public Resource[] getMapperLocations() {
        return mapperLocations;
    }

    public Resource[] getFinalMapperLocations() {
        return finalMapperLocations;
    }

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

}
