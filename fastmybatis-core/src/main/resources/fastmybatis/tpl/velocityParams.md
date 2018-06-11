# velocity变量说明

## ${context}

- ${context.dbName} : 数据库名称
- ${context.packageName} : 包名
- ${context.javaBeanName} : Java类名
- ${context.javaBeanNameLF} : Java类名且首字母小写

## pk

- ${pk.isIdentity} : 是否自增
- ${pk.javaFieldName} ： 主键java字段名
- ${pk.columnName} ： 主键数据库字段名
- ${pk.isUuid} ： 主键是否使用UUID策略

## ${table}

- ${table.tableName} : 数据库表名
- ${table.comment} : 表注释
- ${table.javaBeanNameLF} : java类名，并且首字母小写

## #foreach($column in $columns)...#end

- ${column.columnName} : 表中字段名
- ${column.type} : java字段类型，String，Integer
- ${column.fullType} : java字段完整类型，java.lang.String
- ${column.javaFieldName} : java字段名
- ${column.javaFieldNameUF} : java字段名首字母大写
- ${column.javaType} : 字段的java类型
- ${column.javaTypeBox} : 字段的java装箱类型,如Integer,Long
- ${column.isIdentity} : 是否自增,返回boolean
- ${column.isPk} : 是否自增主键,返回boolean
- ${column.isIdentityPk} : 是否自增主键,返回boolean
- ${column.isEnum} : 是否枚举类型,返回boolean
- ${column.mybatisJdbcType} : 返回mybatis定义的jdbcType
- ${column.comment} : 表字段注释
