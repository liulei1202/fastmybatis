package com.myapp.config;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * 启用fastjson
 * @author tanghc
 *
 */
@Configuration
public class FastjsonConfig {
	
	// 插件
	private static SerializerFeature[] features = {
		//SerializerFeature.PrettyFormat, // 格式化输出
		SerializerFeature.WriteDateUseDateFormat, // 格式化日期"2014-05-29 21:36:24"
		//SerializerFeature.WriteMapNullValue,// 输出null字段
	};
	
	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(features);
		fastConverter.setFastJsonConfig(fastJsonConfig);
		return new HttpMessageConverters(fastConverter);
	}
}
