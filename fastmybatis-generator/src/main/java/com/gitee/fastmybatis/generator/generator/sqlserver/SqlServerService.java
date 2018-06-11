package com.gitee.fastmybatis.generator.generator.sqlserver;

import com.gitee.fastmybatis.generator.entity.DataBaseConfig;
import com.gitee.fastmybatis.generator.generator.SQLService;
import com.gitee.fastmybatis.generator.generator.TableSelector;

public class SqlServerService implements SQLService {

	@Override
	public TableSelector getTableSelector(DataBaseConfig dataBaseConfig) {
		return new SqlServerTableSelector(new SqlServerColumnSelector(dataBaseConfig), dataBaseConfig);
	}

}
