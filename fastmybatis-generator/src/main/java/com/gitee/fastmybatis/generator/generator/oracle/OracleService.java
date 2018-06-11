package com.gitee.fastmybatis.generator.generator.oracle;

import com.gitee.fastmybatis.generator.entity.DataBaseConfig;
import com.gitee.fastmybatis.generator.generator.SQLService;
import com.gitee.fastmybatis.generator.generator.TableSelector;

public class OracleService implements SQLService {

	@Override
	public TableSelector getTableSelector(DataBaseConfig dataBaseConfig) {
		return new OracleTableSelector(new OracleColumnSelector(dataBaseConfig), dataBaseConfig);
	}


}
