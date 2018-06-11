# fastmybatis-generator
fastmybatis对应的代码生成器,采用velocity模板生成.

## 使用方法
- 下载项目
- 作为maven项目导入
- 了解下resources/cfg/t_user.properties,按照这种方式如法炮制.

配置完毕后,运行Run_t_user.java即可

## 配置详解

以`t_user.properties`为例

```
// 驱动
driverClass=com.mysql.jdbc.Driver
// 数据库连接
jdbcUrl=jdbc:mysql://localhost:3306/stu?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull
// 数据库用户名密码
username=root
password=root

// 数据库名
dbName=stu
// 表名
tableName=address
// package
packageName=com.myapp

```

- 全局配置

以下是全局配置,对所有配置文件有效.全局配置中的参数可以单独放在配置文件中.比如放在`t_user.properties`中,那么这个配置会覆盖全局的配置

```
# if true,use database column name
useDbColumn=false

# folder name of mapper class
mapperPackageName=mapper

# suffix of mapper class
mapperClassSuffix=Mapper

# folder name of entity class
entityPackageName=entity

# suffix of entity class.such as "Entity"
entitySuffix=

# if true,the entity class will implements Serializable
serializable=false

# name of delete column
deleteColumn=is_deleted

# if table pk use uuid,set true
uuid=false
```

## 模板

velocity变量参考`velocity变量说明.md`

- entity.tpl_cont 实体类模板
- mapper.tpl_cont mapper类模板

