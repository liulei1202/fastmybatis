package com.gitee.fastmybatis.generator;

import com.gitee.fastmybatis.generator.client.Client;

/**
 * @author tanghc
 *
 */
public class Run_t_userAll {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Client client = new Client();
		
		client.genAll("cfg/all.properties");
	}

}
