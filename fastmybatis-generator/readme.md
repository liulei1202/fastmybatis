# fastmybatis-generator
fastmybatis对应的代码生成器，负责生成实体类和Mapper文件

## 使用方法

- 下载项目
- 作为maven项目导入
- 运行`Run_t_userAll.java`，代码生成在`out`目录
- 了解下resources/cfg/t_user_all.properties

## 全局配置

以下是全局配置,对所有配置文件有效.全局配置中的参数可以单独放在配置文件中.比如放在`t_user_all.properties`中,那么这个配置会覆盖全局的配置

```
# if true,use database column name
useDbColumn=false

# folder name of mapper class
mapperPackageName=mapper

# suffix of mapper class
mapperClassSuffix=Mapper

# folder name of entity class
entityPackageName=entity

# suffix of entity class, such as "Entity"
entitySuffix=

# if true,the entity class will implements Serializable
serializable=false

# name of delete column
deleteColumn=is_deleted

# if table pk use uuid,set true
uuid=false
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