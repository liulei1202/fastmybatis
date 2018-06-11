package com.gitee.fastmybatis.generator.generator;

import com.gitee.fastmybatis.generator.entity.DataBaseConfig;

public interface SQLService {

	public TableSelector getTableSelector(DataBaseConfig dataBaseConfig);

}
