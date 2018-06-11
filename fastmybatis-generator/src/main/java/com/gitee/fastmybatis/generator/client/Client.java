package com.gitee.fastmybatis.generator.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

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

		param.setParam(properties);
		return param;
	}
}
