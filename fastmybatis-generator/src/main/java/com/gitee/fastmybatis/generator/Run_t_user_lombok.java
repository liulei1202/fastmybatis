package com.gitee.fastmybatis.generator;

import com.gitee.fastmybatis.generator.client.Client;

/**
 * 代码生成执行程序
 */
public class Run_t_user_lombok {
	
	
	public static void main(String[] args) {
		Client client = new Client();
		
		// resources/cfg下
		String[] propFiles = { 
				"cfg/t_user_lombok.properties",
				};
		client.gen(propFiles);
	}
	
}
