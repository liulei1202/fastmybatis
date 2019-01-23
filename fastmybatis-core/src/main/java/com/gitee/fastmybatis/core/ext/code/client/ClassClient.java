package com.gitee.fastmybatis.core.ext.code.client;

import java.io.IOException;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import com.gitee.fastmybatis.core.FastmybatisConfig;
import com.gitee.fastmybatis.core.ext.code.NotEntityException;
import com.gitee.fastmybatis.core.ext.exception.MapperFileException;

/**
 * @author tanghc
 */
public class ClassClient {

	private static Log logger = LogFactory.getLog(ClassClient.class);
	
	private static String EMPTY_XML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
	        + "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">"+
            "<mapper namespace=\"%s\"> "+
            " <!--_ext_mapper_--> "+
            " <!--_global_vm_--> "+
            "</mapper>";

	private FileCodeGenerator generator;
	
	private FastmybatisConfig config;

	public ClassClient(FastmybatisConfig config) {
		if(config == null) {
			throw new IllegalArgumentException("config不能为null");
		}
		this.config = config;
		this.generator = new FileCodeGenerator();
	}

	/**
	 * 生成mybatis文件
	 * @param mapperClass Mapper的class对象
	 * @param templateLocation 模板路径
	 * @param globalVmLocation 全局模板路径
	 * @return 返回xml内容
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
		} catch (NotEntityException e) {
            return String.format(EMPTY_XML, mapperClass.getName());
        } catch (IOException e) {
        	throw new MapperFileException(e);
		}
	}

}
