package com.gitee.fastmybatis.generator.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.gitee.fastmybatis.generator.entity.ClientParam;

public class Client{

	String out = System.getProperty("user.dir") + "/out";

	private Generator generator = new Generator();
	
    public void gen(String[] propFiles) {
		System.out.println("生成中...");

		for (String propFile : propFiles) {
			String dest = out + "/" + propFile;
			generator.generateCode(this.buildParam(propFile), dest);
		}
		System.out.println("生成完毕!");
	}
	
	public void genAll(String propFile) {
		System.out.println("生成中...");
		String dest = out + "/" + propFile;
		generator.generateCodeAll(this.buildParam(propFile), dest);
			
		System.out.println("生成完毕!");
	}

	private ClientParam buildParam(String propFile) {
		// 加载全局配置
		Properties properties = new Properties();
		try {
			InputStream inputStream = this.getClass().getClassLoader()
					.getResourceAsStream("cfg/globleConfig.properties");
			properties.load(inputStream);
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}

		Properties self = new Properties();

		try {
			self.load(this.getClass().getClassLoader().getResourceAsStream(propFile));
			properties.putAll(self);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("读取文件错误");
		}

		ClientParam param = new ClientParam();
		param.setCharset(properties.getProperty("charset"));
		// tpl
		String tplListStr = properties.getProperty("tpl.list");
		String[] tplListArr = tplListStr.split(",");
		
		param.setTplList(Arrays.asList(tplListArr));
		
		param.setTableName(properties.getProperty("tableName"));
		param.setPackageName(properties.getProperty("packageName"));
		// jdbc
		param.setDbName(properties.getProperty("dbName"));
		param.setDriverClass(properties.getProperty("driverClass"));
		param.setJdbcUrl(properties.getProperty("jdbcUrl"));
		param.setUsername(properties.getProperty("username"));
		param.setPassword(properties.getProperty("password"));
		String showSchema = properties.getProperty("showSchema");
		param.setShowSchema("true".equals(showSchema));
		param.setUuid("true".equals(properties.getProperty("uuid")));
		
		Map<String, Collection<String>> impllMap = this.buildImplMap(properties);
		properties.put("implMap", impllMap);
		
		Map<String, String> extMap = this.buildExtMap(properties);
		properties.put("extMap", extMap);

		param.setParam(properties);
		return param;
	}
	
	private Map<String, Collection<String>> buildImplMap(Properties properties) {
		String implMap = properties.getProperty("implMap", "");
		if(StringUtils.isBlank(implMap)) {
			return Collections.emptyMap();
		}
		Map<String, Collection<String>> map = new HashMap<>();
		// implMap=Student:Clonable,com.xx.PK;User:com.xx.BaseParam
		// ["Student:Clonable,com.xx.PK", "User:com.xx.BaseParam"]
		String[] arr = implMap.split(";");
		for (String block : arr) {
			this.addImpls(block, map, properties);
		}
		return map;
	}
	
	private Map<String, String> buildExtMap(Properties properties) {
		String implMap = properties.getProperty("extMap", "");
		if(StringUtils.isBlank(implMap)) {
			return Collections.emptyMap();
		}
		Map<String, String> map = new HashMap<>();
		// extMap=Student:Clonable;User:com.xx.BaseParam
		// ["Student:Clonable,com.xx.PK", "User:com.xx.BaseParam"]
		String[] arr = implMap.split(";");
		for (String block : arr) {
			this.addExt(block, map, properties);
		}
		return map;
	}
	
	private void addImpls(String block, Map<String, Collection<String>> map, Properties properties) {
		// Student:Clonable,com.xx.PK
		// ["Student", "Clonable,com.xx.PK"]
		String[] arr = block.split("\\:");
		String key = arr[0]; // Student
		String classNames = arr[1]; // Clonable,com.xx.PK
		
		String[] classArr = classNames.split(",");
		Set<String> value = new HashSet<>();
		for (String className : classArr) {
			value.add(className);
		}
		boolean isSerialable = "true".equals(properties.getProperty("serializable"));
		if(isSerialable) {
			value.add("java.io.Serializable");
		}
		
		map.put(key, value);
	}
	
	private void addExt(String block, Map<String, String> map, Properties properties) {
		// Student:Clonable
		// ["Student", "Clonable"]
		String[] arr = block.split("\\:");
		String key = arr[0]; // Student
		String className = arr[1]; // Clonable
		map.put(key, className);
	}
}
