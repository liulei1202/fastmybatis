package com.gitee.fastmybatis.generator.generator;

import java.util.List;
import java.util.Map;

/**
 * SQL上下文,这里可以取到表,字段信息<br>
 * 最终会把SQL上下文信息放到velocity中
 */
public class SQLContext {
    private TableDefinition tableDefinition; // 表结构定义
    private String packageName; // 包名
    private Map<Object, Object> param;

    public SQLContext(TableDefinition tableDefinition) {
        this.tableDefinition = tableDefinition;
        // 默认为全字母小写的类名
        this.packageName = getJavaBeanName().toLowerCase();
    }

    public String getEntitySuffix() {
        return tableDefinition.getEntitySuffix();
    }

    public String getDbName() {
        return tableDefinition.getDbName();
    }

    public String getDbNameLowerCase() {
        return this.getDbName().toLowerCase();
    }

    /**
     * 返回Java类名
     * 
     * @return
     */
    public String getJavaBeanName() {
        return this.tableDefinition.getJavaBeanName();
    }

    /**
     * 返回Java类名且首字母小写
     * 
     * @return
     */
    public String getJavaBeanNameLF() {
        return this.tableDefinition.getJavaBeanNameLF();
    }

    public TableDefinition getTableDefinition() {
        return tableDefinition;
    }

    public void setTableDefinition(TableDefinition tableDefinition) {
        this.tableDefinition = tableDefinition;
    }

    public List<ColumnDefinition> getColumnDefinitionList() {
        return tableDefinition.getColumnDefinitions();
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Map<Object, Object> getParam() {
        return param;
    }

    public void setParam(Map<Object, Object> param) {
        this.param = param;
    }

    public static void main(String[] args) {

        SQLContext[] tt = { new SQLContext(new TableDefinition("r_table_b")),
                new SQLContext(new TableDefinition("MyTable")), new SQLContext(new TableDefinition("user.frontUser")),
                new SQLContext(new TableDefinition("user.back_user")) };

        for (SQLContext ctx : tt) {
            System.out.println(ctx.getJavaBeanName());
        }
        /*
         * 输出: RTableB MyTable UserFrontUser UserBackUser
         */
    }

}
