# 开发文档

文档放在docs/files下，写完文档记得执行下`SidebarTest.main()`方法

配合gitee pages服务使用，gitee pages服务指定docs目录。

## 本地查看开发文档

- 前提：先安装好npm，[npm安装教程](https://blog.csdn.net/zhangwenwu2/article/details/52778521)。建议使用淘宝镜像。
- 安装docsify，执行npm命令`npm i docsify-cli -g --registry=https://registry.npm.taobao.org`
- cd到当前目录，运行命令`docsify serve docs`，然后访问：`http://localhost:3000`即可查看。
