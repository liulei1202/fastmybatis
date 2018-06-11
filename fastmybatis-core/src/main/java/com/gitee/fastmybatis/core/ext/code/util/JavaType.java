package com.gitee.fastmybatis.core.ext.code.util;

/**
 * @author tanghc
 */
public class JavaType {
    /** 基本类型 */
    private String baseType;
    /** 装箱类型 */
    private String boxType;
    /** 对应的mybatis类型 */
    private String mybatisType;

    /**
     * @param baseType
     *            基本类型
     * @param boxType
     *            装箱类型
     * @param mybatisType
     *            对应的mybatis类型
     */
    public JavaType(String baseType, String boxType, String mybatisType) {
        super();
        this.baseType = baseType;
        this.boxType = boxType;
        this.mybatisType = mybatisType;
    }

    public String getBaseType() {
        return baseType;
    }

    public String getBoxType() {
        return boxType;
    }

    public String getMybatisType() {
        return mybatisType;
    }
}