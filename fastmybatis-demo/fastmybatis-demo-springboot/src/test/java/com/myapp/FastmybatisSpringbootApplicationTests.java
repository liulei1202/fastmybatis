package com.myapp;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FastmybatisSpringbootApplicationTests {

	public void print(Object o) {
		System.out.println("=================");
		System.out.println(o);
		System.out.println("=================");
	}
	
	public void printJson(Object o) {
		System.out.println("=================");
		System.out.println(JSON.toJSONString(o));
		System.out.println("=================");
	}
}
