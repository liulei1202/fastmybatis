# fastmybatis-generator

fastmybatis对应的代码生成器，负责生成实体类和Mapper文件

## 使用方法

- 下载项目
- 作为maven项目导入
- 运行`Run_t_userAll.java`，代码生成在`out`目录
- 了解下resources/cfg/t_user_all.properties

## 全局配置

参见`globleConfig.properties`

全局配置中的参数可以单独放在配置文件中.比如放在`t_user_all.properties`中,那么这个配置会覆盖全局的配置

```
# 如果为true，将使用数据库字段
useDbColumn=false

# 存放mapper文件的包名
mapperPackageName=mapper

# mapper文件后缀，如：Dao
mapperClassSuffix=Mapper

# 存放实体类文件的包名
entityPackageName=entity

# 实体类文件后缀, 如：Entity
entitySuffix=

# 如果为true，实体类默认实现Serializable接口
serializable=false

# 全局父类，如果设置了，所有实体类会继承这个父类
globalExt=

# 删除字段名称
deleteColumn=is_deleted

# 如果主键是UUID，设为true
uuid=false

# 指定表名称前缀，实体类会移除前缀。tableName:a_order -> Order.java
tablePrefix=

# 指定表名称后缀，实体类会移除后缀。tableName:order_ext -> Order.java
tableSuffix=

# 设置实体类实现的接口
# 例子:implMap=Student:Clonable,com.xx.PK;User:com.xx.BaseParam
# 结果:
# 	public class Student implements Clonable, com.xx.PK {}
#   public class User implements com.xx.BaseParam {}
implMap=

# 设置实体类继承的类。
# 例子:extMap=Student:Clonable;User:com.xx.BaseParam
# 结果:
# 	public class Student extend Clonable {}
#   public class User extend com.xx.BaseParam {}
#
# 此操作会覆盖globalExt属性
extMap=
```

## 团队协作

- 创建好配置文件
- 创建好Run_xx.java类
- 上传到代码仓库，提供给大家使用

## 模板

velocity变量参考`velocity变量说明.md`

- entity.tpl_cont 实体类模板
- mapper.tpl_cont mapper类模板

---

> 此代码生成器看似简陋实则功能强大，汇聚了作者多年来对代码生成器的总结和感悟。
> 代码生成器不需要太花哨，实用就行。网上的代码生成器有界面的、高大上的，原理跟这基本一样。