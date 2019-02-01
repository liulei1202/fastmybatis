# 更新日志

## 1.5.1

- @Condition新增index属性优化

## 1.5.0 

- @Condition新增index属性，调整条件顺序
- @新增LazyFetch注解
- 修复oracle数据库插入问题

## 1.4.2

- 修复懒加载BUG

## 1.4.1

- mapper.updateByQuery()方法支持逻辑删除字段

## 1.4.0

- 新增实体类一对一懒加载功能
- 查询BUG修复

## 1.3.0(有BUG不要使用此版本)

- updateByQuery可强制更新null
- 修复distinct使用方式id顺序问题
- @Condition注解可作用在字段上

## 1.2.0

- 修复自定义xml文件合并BUG
- 新增updateByMap方法
- 新增distinct使用方式

## 1.1.1

- 修复实体类静态字段被解析问题

## 1.1.0

- 提供easyui表格分页查询，见MapperUtil.java

## 1.0.17

- 修复SqlServer模板问题（使用SqlServer数据库必须升级到此版本）


## 1.0.16

- @Condition注解新增ignoreEmptyString属性 https://durcframework.gitee.io/fastmybatis/#27040701

## 1.0.15

- 代码优化性能优化（阿里代码规范+sonar）

## 1.0.14

- 代码优化（sonar）

## 1.0.13

- 合并PR2，https://gitee.com/durcframework/fastmybatis/pulls/2

## 1.0.12

- 修复模板问题


## 1.0.11

- 优化属性拷贝

## 1.0.10

- 增强Mapper.xml，不同Mapper文件可指定同一个namespace，最终会合并

## 1.0.9

- 对象转换方法优化

## 1.0.8

- pageSize传0时报错bug

## 1.0.7

- 参数类构建条件可以获取父类属性

## 1.0.6

- EasyuiDtagridParam增加条件忽略

## 1.0.5

- easyui表格参数支持

## 1.0.4

- Mapper对应无实体类BUG

## 1.0.3

- 添加强制查询功能，见TUserMapperTest.testForceQuery()

## 1.0.2

- 完善注释
- 代码优化

## 1.0.1 初版