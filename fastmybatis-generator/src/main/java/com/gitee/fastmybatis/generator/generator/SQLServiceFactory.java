package com.gitee.fastmybatis.generator.generator;

import java.util.HashMap;
import java.util.Map;

import com.gitee.fastmybatis.generator.entity.DataBaseConfig;
import com.gitee.fastmybatis.generator.generator.mysql.MySqlService;
import com.gitee.fastmybatis.generator.generator.oracle.OracleService;
import com.gitee.fastmybatis.generator.generator.sqlserver.SqlServerService;

public class SQLServiceFactory {

	private static Map<String, SQLService> SERVICE_MAP = new HashMap<String, SQLService>(
			20);

	public static SQLService build(DataBaseConfig dataBaseConfig) {
		String driverClass = dataBaseConfig.getDriverClass();
		SQLService service = SERVICE_MAP.get(driverClass);

		if (service == null) {

			service = findSqlService(driverClass);

			if (service != null) {
				SERVICE_MAP.put(driverClass, service);
			} else {
				throw new RuntimeException("本系统暂不支持该数据源("
						+ dataBaseConfig.getDriverClass() + ")");
			}

		}

		return service;
	}

	private static SQLService findSqlService(String driverClass) {
		if (driverClass.contains("mysql")) {
			return new MySqlService();
		}
		if (driverClass.contains("jtds")) {
			return new SqlServerService();
		}
		if (driverClass.contains("sqlserver")) {
			return new SqlServerService();
		}
		if (driverClass.contains("oracle")) {
			return new OracleService();
		}
		// ... 添加其他数据库

		return null;
	}

}
