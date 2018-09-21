package com.gitee.fastmybatis.core.ext;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.QName;
import org.dom4j.dom.DOMAttribute;
import org.dom4j.io.SAXReader;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import com.gitee.fastmybatis.core.FastmybatisConfig;
import com.gitee.fastmybatis.core.ext.code.client.ClassClient;
import com.gitee.fastmybatis.core.mapper.Mapper;

/**
 * mapper构建
 * 
 * @author tanghc
 *
 */
public class MapperLocationsBuilder {

	private static Log LOGGER = LogFactory.getLog(MapperLocationsBuilder.class);
	private static final String ENCODE = "UTF-8";
	
	private static final String EMPTY = "";
	private static final String XML_SUFFIX = ".xml";
	private static final String NODE_MAPPER = "mapper";
	private static final String MAPPER_START = "<mapper>";
	private static final String MAPPER_END = "</mapper>";
	private static final String MAPPER_EMPTY = "<mapper/>";
	private static final String ATTR_NAMESPACE = "namespace";
	

	private static final String EXT_MAPPER_PLACEHOLDER = "<!--_ext_mapper_-->";
	private static final String TEMPLATE_SUFFIX = ".vm";
	private static final String DEFAULT_CLASS_PATH = "/fastmybatis/tpl/";

	private Map<String, MapperResourceDefinition> mapperResourceStore = new HashMap<>();

	private FastmybatisConfig config = new FastmybatisConfig();

	private Attribute namespace = new DOMAttribute(new QName(ATTR_NAMESPACE));

	private String dbName;

	/**
	 * 初始化mybatis配置文件
	 * 
	 * @param basePackage
	 *            实体类的包目录.指定哪些包需要被扫描,支持多个包"package.a,package.b"并对每个包都会递归搜索
	 */
	public Resource[] build(String basePackage) {
		try {
			String[] basePackages = StringUtils.tokenizeToStringArray(basePackage,
			        ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
			ClassScanner classScanner = new ClassScanner(basePackages, Mapper.class);
			Set<Class<?>> clazzsSet = classScanner.getClassSet();

			Resource[] mapperLocations = this.buildMapperLocations(clazzsSet);

			return mapperLocations;
		} catch (Exception e) {
		    LOGGER.error("构建mapper失败", e);
			throw new RuntimeException(e);
		} finally {
			distroy();
		}
	}

	private void distroy() {
		mapperResourceStore.clear();
	}

	public void storeMapperFile(Resource[] mapperLocations) {
		for (Resource mapperLocation : mapperLocations) {
		    // XxDao.xml
			String filename = mapperLocation.getFilename();

			mapperResourceStore.put(filename, new MapperResourceDefinition(mapperLocation));
		}
	}

	private MapperResourceDefinition getMapperFile(String mapperFileName) {
		return mapperResourceStore.get(mapperFileName);
	}

	private Resource[] buildMapperLocations(Set<Class<?>> clazzsSet) {

		List<Resource> mapperLocations = this.buildMapperResource(clazzsSet);

		this.addUnmergedResource(mapperLocations);

		this.addCommonSqlClasspathMapper(mapperLocations);

		return mapperLocations.toArray(new Resource[mapperLocations.size()]);
	}

	private List<Resource> buildMapperResource(Set<Class<?>> clazzsSet) {
		int classCount = clazzsSet.size();
		if(classCount == 0) {
		    return new ArrayList<Resource>();
		}
		final String templateLocation = this.buildTemplateLocation(this.getDbName());
		final String globalVmLocation = this.config.getGlobalVmLocation();
		LOGGER.info("使用模板:" + templateLocation);
		final ClassClient codeClient = new ClassClient(config);
		final List<Resource> mapperLocations = Collections.synchronizedList(new ArrayList<Resource>(classCount));
		ExecutorService executorPool = this.buildExecutorService(classCount);

		long startTime = System.currentTimeMillis();
		try {
			for (Class<?> mapperClass : clazzsSet) {
				final Class<?> daoClass = mapperClass;
				executorPool.execute(new Runnable() {
					@Override
					public void run() {
						try {
							String xml = codeClient.genMybatisXml(daoClass, templateLocation, globalVmLocation);
							xml = mergeExtMapperFile(daoClass, xml);
							mapperLocations.add(new MapperResource(xml, daoClass));
						} catch (Exception e) {
						    LOGGER.error(e.getMessage(), e);
							throw new RuntimeException(e);
						}
					}
				});
			}

			// 等待线程池中所有的线程都执行完毕
			executorPool.shutdown();
			executorPool.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);

			long endTime = System.currentTimeMillis();
			LOGGER.info("生成Mapper内容总耗时：" + (endTime - startTime) / 1000.0 + "秒");

			this.saveMapper(config.getMapperSaveDir(), mapperLocations);

			return mapperLocations;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}

	}

	private ExecutorService buildExecutorService(int classSize) {
		int poolSize = config.getMapperExecutorPoolSize();
		int nThreads = poolSize > classSize ? classSize : poolSize;
		
		return Executors.newFixedThreadPool(nThreads, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				t.setUncaughtExceptionHandler(new ThreadPoolUncaughtExceptionHandler());
				return t;
			}
		});
	}

	/** 保存mapper到本地文件夹 */
	private void saveMapper(final String saveDir, final List<Resource> mapperLocations) {
		if (StringUtils.hasText(saveDir)) {
			LOGGER.info("保存mapper文件到" + saveDir);
			for (Resource resource : mapperLocations) {
				OutputStream out = null;
				try {
					out = new FileOutputStream(saveDir + "/" + resource.getFilename());
					IOUtils.copy(resource.getInputStream(), out);
				} catch (FileNotFoundException e) {
					LOGGER.error("保存mapper文件错误，文件不存在。" + saveDir + "/" + resource.getFilename(), e);
					throw new RuntimeException(e);
				} catch (IOException e) {
					LOGGER.error("保存mapper文件错误。" + saveDir + "/" + resource.getFilename(), e);
					throw new RuntimeException(e);
				} finally {
					IOUtils.closeQuietly(out);
				}
			}
		}
	}

	private String buildTemplateLocation(String dbName) {
		String templateClasspath = config.getTemplateClasspath();
		if (StringUtils.isEmpty(templateClasspath)) {
			templateClasspath = DEFAULT_CLASS_PATH;
		}
		// 返回格式：classpath路径 + 数据库名称 + 文件后缀
		// 如：/fastmybatis/tpl/mysql.vm
		return templateClasspath + this.buildTemplateFileName(dbName);
	}

	/** 构建文件名 */
	private String buildTemplateFileName(String dbName) {
		dbName = dbName.replaceAll("\\s", "").toLowerCase();
		return dbName + TEMPLATE_SUFFIX;
	}

	private void addCommonSqlClasspathMapper(List<Resource> mapperLocations) {
		String commonSqlClasspath = config.getCommonSqlClasspath();
		ClassPathResource res = new ClassPathResource(commonSqlClasspath);
		mapperLocations.add(res);
	}

	/**合并其它mapper*/
	private void addUnmergedResource(List<Resource> mapperLocations) {
		Collection<MapperResourceDefinition> mapperResourceDefinitions = this.mapperResourceStore.values();
		for (MapperResourceDefinition mapperResourceDefinition : mapperResourceDefinitions) {
			if (mapperResourceDefinition.isMerged()) {
				continue;
			}

			LOGGER.info("加载未合并Mapper：" + mapperResourceDefinition.getFilename());

			mapperLocations.add(mapperResourceDefinition.getResource());
		}
	}

	/** 合并扩展mapper文件内容 */
	private String mergeExtMapperFile(Class<?> mapperClass, String xml) {
		// 自定义文件
		String mapperFileName = mapperClass.getSimpleName() + XML_SUFFIX;
		// 先找跟自己同名的xml，如:UserMapper.java -> UserMapper.xml
		MapperResourceDefinition mapperResourceDefinition = this.getMapperFile(mapperFileName);
		StringBuilder extXml = new StringBuilder();

		if (mapperResourceDefinition != null) {
			// 追加内容
			String extFileContent = this.getExtFileContent(mapperResourceDefinition.getResource());
			extXml.append(extFileContent);

			mapperResourceDefinition.setMerged(true);
		}
		// 再找namespace一样的xml
		try {
            String otherMapperXml = this.buildOtherMapperContent(mapperClass, this.mapperResourceStore.values());
            extXml.append(otherMapperXml);
        } catch (Exception e) {
            LOGGER.error("加载额外Mapp文件出错", e);
            throw new RuntimeException(e);
        }
		
		xml = xml.replace(EXT_MAPPER_PLACEHOLDER, extXml.toString());

		return xml;
	}
	
	/** 
                    一个Mapper.java可以对应多个Mapper.xml。只要namespace相同，就会把它们的内容合并，最终形成一个完整的MapperResource<br>
                   这样做的好处是每人维护一个文件相互不干扰，至少在提交代码是不会冲突，同时也遵循了开闭原则。
	 */
	private String buildOtherMapperContent(Class<?> mapperClass, Collection<MapperResourceDefinition> mapperResourceDefinitions) throws Exception {
	    StringBuilder xml = new StringBuilder();
	    String trueNamespace = mapperClass.getName();
	    for (MapperResourceDefinition mapperResourceDefinition : mapperResourceDefinitions) {
            if(mapperResourceDefinition.isMerged()) {
                continue;
            }
            Resource resource = mapperResourceDefinition.getResource();
            InputStream in = resource.getInputStream();
            Document document = this.buildSAXReader().read(in);
            Element mapperNode = document.getRootElement();
            
            Attribute attrNamespance = mapperNode.attribute(ATTR_NAMESPACE);
            String namespaceValue = attrNamespance == null ? null : attrNamespance.getValue();
            
            if(StringUtils.isEmpty(namespaceValue)) {
                throw new Exception("Mapper文件[" + mapperResourceDefinition.getFilename() + "]的namespace不能为空。");
            }
            
            if(trueNamespace.equals(namespaceValue)) {
                String rootNodeName = mapperNode.getName();

                if (!NODE_MAPPER.equals(rootNodeName)) {
                    throw new Exception("mapper文件必须含有<mapper>节点,是否缺少<mapper></mapper>?");
                }
                
                mapperNode.remove(namespace);

                String contentXml = mapperNode.asXML();
                // 去除首尾mapper标签
                contentXml = contentXml.replace(MAPPER_START, EMPTY).replace(MAPPER_END, EMPTY).replace(MAPPER_EMPTY, EMPTY);
                xml.append(contentXml);
                mapperResourceDefinition.setMerged(true);
            }
        }
	    
	    return xml.toString();
	    
	}

	private String getExtFileContent(Resource resource) {
		try {
			InputStream in = resource.getInputStream();
			Document document = this.buildSAXReader().read(in);
			Element mapperNode = document.getRootElement();

			String rootNodeName = mapperNode.getName();

			if (!NODE_MAPPER.equals(rootNodeName)) {
				throw new Exception("mapper文件必须含有<mapper>节点,是否缺少<mapper></mapper>?");
			}

			mapperNode.remove(namespace);

			String rootXml = mapperNode.asXML();
			// 去除首尾mapper标签
			rootXml = rootXml.replace(MAPPER_START, EMPTY).replace(MAPPER_END, EMPTY).replace(MAPPER_EMPTY, EMPTY);

			return rootXml;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new RuntimeException("加载资源文件出错,[" + resource.getFilename() + "]," + e.getMessage());
		}
	}

	private SAXReader buildSAXReader() {
		SAXReader reader = new SAXReader();
		reader.setEncoding(ENCODE);
		return reader;
	}

	public void setConfig(FastmybatisConfig config) {
		this.config = config;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public void setMapperExecutorPoolSize(int poolSize) {
		config.setMapperExecutorPoolSize(poolSize);
	}

	/** MapperResource包装类 */
	private static class MapperResourceDefinition {
		/** 是否合并 */
		private boolean merged;
		/** mapper对应的resource */
		private Resource resource;

		public MapperResourceDefinition(Resource resource) {
			super();
			this.resource = resource;
		}

		public String getFilename() {
			return this.resource.getFilename();
		}

		public boolean isMerged() {
			return merged;
		}

		public void setMerged(boolean merged) {
			this.merged = merged;
		}

		public Resource getResource() {
			return resource;
		}
	}

	/** 不能用InputStreamResource，因为InputStreamResource只能read一次 */
	private static class MapperResource extends ByteArrayResource {
		private Class<?> mapperClass;

		public MapperResource(String xml, Class<?> mapperClass) throws UnsupportedEncodingException {
			/*
			 * 必须指明InputStreamResource的description 主要解决多个dao报Invalid bound
			 * statement (not found)BUG。
			 * 经排查是由XMLMapperBuilder.parse()中，configuration.isResourceLoaded(
			 * resource)返回true导致。 因为InputStreamResource.toString()始终返回同一个值，
			 * 需要指定description保证InputStreamResource.toString()唯一。
			 */
			super(xml.getBytes(ENCODE), mapperClass.getName());
			this.mapperClass = mapperClass;
		}

		@Override
		public String getFilename() {
			return this.mapperClass.getSimpleName() + XML_SUFFIX;
		}

	}

	/** 捕获线程池中的异常 */
	private static class ThreadPoolUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
		@Override
		public void uncaughtException(Thread t, Throwable e) {
			LOGGER.error(e.getMessage(), e);
			System.exit(0);
		}
	}

}
