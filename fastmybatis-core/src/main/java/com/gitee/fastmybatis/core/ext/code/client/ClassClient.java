package com.gitee.fastmybatis.core.ext.code.client;

import java.io.FileNotFoundException;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import com.gitee.fastmybatis.core.FastmybatisConfig;

/**
 * @author tanghc
 */
public class ClassClient {

	private static Log logger = LogFactory.getLog(ClassClient.class);

	private Generator generator = new Generator();
	
	private FastmybatisConfig config;

	public ClassClient(FastmybatisConfig config) {
		super();
		if(config == null) {
			throw new IllegalArgumentException("config不能为null");
		}
		this.config = config;
	}

	/**
	 * 生成mybatis文件
	 * 
	 * @param mapperClass
	 * @param dbType
	 * @return
	 */
	public String genMybatisXml(Class<?> mapperClass, String templateLocation,String globalVmLocation) {
		if (logger.isDebugEnabled()) {
			logger.debug("开始生成" + mapperClass.getName() + "对应的Mapper");
		}

		ClientParam param = new ClientParam();
		param.setTemplateLocation(templateLocation);
		param.setGlobalVmLocation(globalVmLocation);
		param.setMapperClass(mapperClass);
		param.setConfig(config);

		try {
			return generator.generateCode(param);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

}
