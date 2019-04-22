package com.gitee.fastmybatis.core.ext;

import com.gitee.fastmybatis.core.FastmybatisConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;

/**
 * mapper热部署
 *
 * @author tanghc
 */
public class HotDeploy {
    private static final Log LOG = LogFactory.getLog(HotDeploy.class);

    private SqlSessionFactoryBeanExt sqlSessionFactoryBeanExt;
    private FastmybatisConfig fastmybatisConfig;
    private Configuration configuration;

    public HotDeploy(SqlSessionFactoryBeanExt sqlSessionFactoryBeanExt) {
        this.sqlSessionFactoryBeanExt = sqlSessionFactoryBeanExt;
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBeanExt.getSqlSessionFactory();
        this.configuration = sqlSessionFactory.getConfiguration();
        this.fastmybatisConfig = sqlSessionFactoryBeanExt.getConfig();
    }

    public void start() {
        if (!fastmybatisConfig.isHotDeploy()) {
            return;
        }
        LOG.info("启动mapper文件热部署，修改mapper不用重启。");
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Resource[] mapperLocations = sqlSessionFactoryBeanExt.getMapperLocations();
                    WatchService watcher = FileSystems.getDefault().newWatchService();
                    Set<String> watchPaths = getWatchPaths(mapperLocations);
                    for (String watchPath : watchPaths) {
                        Paths.get(watchPath).register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
                    }
                    while (true) {
                        WatchKey watchKey = watcher.take();
                        Set<String> set = new HashSet<>();
                        for (WatchEvent<?> event : watchKey.pollEvents()) {
                            set.add(event.context().toString());
                        }
                        LOG.debug("修改了xml文件，重新加载mapper。files:" + set);
                        // 重新加载xml
                        reloadXml(mapperLocations);
                        boolean valid = watchKey.reset();
                        if (!valid) {
                            break;
                        }
                    }
                } catch (Exception e) {
                    LOG.error("mapper热部署失败", e);
                }
            }
        });
    }

    protected void reloadXml(Resource[] mapperLocations) throws IOException {
        Resource[] mapperLocationsNew = new Resource[mapperLocations.length];
        for (int i = 0; i < mapperLocations.length; i++) {
            Resource mapperLocation = mapperLocations[i];
            String path = mapperLocation.getURL().getPath();
            Resource resource = getResource(path);
            mapperLocationsNew[i] = resource;
        }
        // 清除现有的
        Resource[] finalMapperLocations = sqlSessionFactoryBeanExt.getFinalMapperLocations();
        for (Resource resource : finalMapperLocations) {
            clearMap(getNamespace(resource));
            clearSet(resource.toString());
        }

        sqlSessionFactoryBeanExt.setMapperLocations(mapperLocationsNew);
        sqlSessionFactoryBeanExt.buildSqlSessionFactory();
    }

    protected Set<String> getWatchPaths(Resource[] mapperLocations) throws IOException {
        Set<String> set = new HashSet<>();
        for (Resource resource : mapperLocations) {
            set.add(resource.getFile().getParentFile().getAbsolutePath());
        }
        return set;
    }


    protected Resource getResource(String path) {
        return new FileSystemResource(path);
    }

    /**
     * 删除xml元素的节点缓存
     *
     * @param nameSpace xml中命名空间
     * @date ：2018/12/19
     * @author ：zc.ding@foxmail.com
     */
    private void clearMap(String nameSpace) {
        List<String> stringList = Arrays.asList("mappedStatements", "caches", "resultMaps", "parameterMaps", "keyGenerators", "sqlFragments");
        for (String fieldName : stringList) {
            Object value = getFieldValue(configuration, fieldName);
            if (value instanceof Map) {
                Map<?, ?> map = (Map) value;
                List<Object> list = getList(map.keySet(), nameSpace);
                for (Object k : list) {
                    map.remove((Object) k);
                }
            }
        }
    }

    private List<Object> getList(Set<?> sets, String nameSpace) {
        List<Object> ret = new ArrayList<>();
        for (Object o : sets) {
            if (o.toString().startsWith(nameSpace + ".")) {
                ret.add(o);
            }
        }
        return ret;
    }

    /**
     * 清除文件记录缓存
     *
     * @param resource xml文件路径
     * @date ：2018/12/19
     * @author ：zc.ding@foxmail.com
     */
    private void clearSet(String resource) {
        Object value = getFieldValue(configuration, "loadedResources");
        if (value instanceof Set) {
            Set<?> set = (Set) value;
            set.remove(resource);
            set.remove("namespace:" + resource);
        }
    }

    /**
     * 获取对象指定属性
     *
     * @param obj       对象信息
     * @param fieldName 属性名称
     * @return java.lang.Object
     * @date ：2018/12/19
     * @author ：zc.ding@foxmail.com
     */
    private Object getFieldValue(Object obj, String fieldName) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            boolean accessible = field.isAccessible();
            field.setAccessible(true);
            Object value = field.get(obj);
            field.setAccessible(accessible);
            return value;
        } catch (Exception e) {
            LOG.info("ERROR: 加载对象中[" + fieldName + "]", e);
            return null;
        }
    }

    /**
     * 获取xml的namespace
     *
     * @param resource xml资源
     * @return java.lang.String
     * @date ：2018/12/19
     * @author ：zc.ding@foxmail.com
     */
    private String getNamespace(Resource resource) {
        try {
            XPathParser parser = new XPathParser(resource.getInputStream(), true, null, new XMLMapperEntityResolver());
            return parser.evalNode("/mapper").getStringAttribute("namespace");
        } catch (Exception e) {
            LOG.info("ERROR: 解析xml中namespace失败", e);
            return null;
        }
    }
}
