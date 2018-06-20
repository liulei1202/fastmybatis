package com.gitee.fastmybatis.generator;

import com.gitee.fastmybatis.generator.client.Client;

/**
 * 生成单表数据
 */
public class Run_t_user {
	
	
	public static void main(String[] args) {
		Client client = new Client();
		
		// resources/cfg下
		String[] propFiles = { 
				"cfg/t_user.properties",
				};
		client.gen(propFiles);
	}
	
}
