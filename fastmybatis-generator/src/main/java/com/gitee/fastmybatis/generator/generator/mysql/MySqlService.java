package com.gitee.fastmybatis.generator.generator.mysql;

import com.gitee.fastmybatis.generator.entity.DataBaseConfig;
import com.gitee.fastmybatis.generator.generator.SQLService;
import com.gitee.fastmybatis.generator.generator.TableSelector;

public class MySqlService implements SQLService {

	@Override
	public TableSelector getTableSelector(DataBaseConfig dataBaseConfig) {
		return new MySqlTableSelector(new MySqlColumnSelector(dataBaseConfig), dataBaseConfig);
	}

}
