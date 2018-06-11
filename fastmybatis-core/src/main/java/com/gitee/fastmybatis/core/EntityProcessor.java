package com.gitee.fastmybatis.core;

import java.util.Map;

/**
 * 记录处理
 * 
 * @param <Entity> 实体类
 * @author tanghc
 */
public interface EntityProcessor<Entity> {
	/**
	 * 处理记录
	 * @param entity 记录对象
	 * @param entityMap 记录对象中的属性名/值所对应的map
	 */
	void process(Entity entity, Map<String,Object> entityMap);
}
