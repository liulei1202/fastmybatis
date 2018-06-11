package com.gitee.fastmybatis.core.ext.code.client;

import com.gitee.fastmybatis.core.FastmybatisConfig;
import com.gitee.fastmybatis.core.util.ClassUtil;

/**
 * 客户端参数
 * @author tanghc
 */
public class ClientParam {
	private Class<?> mapperClass;
	/** 模板路径 */
	private String templateLocation;
	private String globalVmLocation;
	private FastmybatisConfig config;

	public Class<?> getEntityClass() {
		if (mapperClass.isInterface()) {
			return ClassUtil.getSuperInterfaceGenricType(mapperClass, 0);
		} else {
			return ClassUtil.getSuperClassGenricType(mapperClass, 0);
		}
	}
	
	public String getGlobalVmPlaceholder() {
		return this.config.getGlobalVmPlaceholder();
	}

	public String getGlobalVmLocation() {
		return globalVmLocation;
	}

	public void setGlobalVmLocation(String globalVmLocation) {
		this.globalVmLocation = globalVmLocation;
	}

	public Class<?> getMapperClass() {
		return mapperClass;
	}

	public void setMapperClass(Class<?> mapperClass) {
		this.mapperClass = mapperClass;
	}

	public String getTemplateLocation() {
		return templateLocation;
	}

	public void setTemplateLocation(String templateLocation) {
		this.templateLocation = templateLocation;
	}

	public FastmybatisConfig getConfig() {
		return config;
	}

	public void setConfig(FastmybatisConfig config) {
		this.config = config;
	}

	public String getCountExpression() {
		return this.config.getCountExpression();
	}

}
