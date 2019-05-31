package com.gitee.fastmybatis.core.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * TypeHandler适配器
 *
 * @author tanghc
 */
public abstract class AbstractTypeHandlerAdapter<T> extends BaseTypeHandler<T> {


    /**
     * 解析数据库字段值，转成java对象值
     *
     * @param columnValue 数据库值
     * @return 返回java对象值
     */
    protected abstract T convertValue(Object columnValue);

    /**
     * 保存到数据库的值
     *
     * @param defaultValue 默认值
     * @return 返回保存到数据库的值
     */
    protected abstract Object getFillValue(T defaultValue);


    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return this.convertValue(rs.getObject(columnName));
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return this.convertValue(rs.getObject(columnIndex));
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return this.convertValue(cs.getObject(columnIndex));
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null) {
            if (jdbcType == null) {
                throw new TypeException(
                        "JDBC requires that the JdbcType must be specified for all nullable parameters.");
            }
            try {
                this.setNullParameter(ps, i, parameter, jdbcType);
            } catch (SQLException e) {
                throw new TypeException("Error setting null for parameter #" + i + " with JdbcType " + jdbcType + " . "
                        + "Try setting a different JdbcType for this parameter or a different jdbcTypeForNull configuration property. "
                        + "Cause: " + e, e);
            }
        } else {
            try {
                setNonNullParameter(ps, i, parameter, jdbcType);
            } catch (Exception e) {
                throw new TypeException(
                        "Error setting non null for parameter #" + i + " with JdbcType " + jdbcType + " . "
                                + "Try setting a different JdbcType for this parameter or a different configuration property. "
                                + "Cause: " + e,
                        e);
            }
        }
    }

    protected void setNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        this.setParameterValue(ps, i, parameter, jdbcType);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        this.setParameterValue(ps, i, parameter, jdbcType);
    }

    protected void setParameterValue(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        Object val = this.buildFillValue(parameter);
        if (val == null) {
            ps.setNull(i, jdbcType.TYPE_CODE);
        } else {
            if (val instanceof Date) {
                Timestamp date = new Timestamp(((Date) val).getTime());
                ps.setTimestamp(i, date);
            } else {
                ps.setObject(i, val);
            }
        }
    }

    protected Object buildFillValue(T parameter) {
        return this.getFillValue(parameter);
    }

    /**
     * setParameter默认行为
     *
     * @param ps        PreparedStatement
     * @param i         字段索引
     * @param parameter 参数
     * @param jdbcType  数据类型
     * @throws SQLException 抛出SQLException
     */
    protected void setParameterDefault(PreparedStatement ps, int i, T parameter, JdbcType jdbcType)
            throws SQLException {
        super.setParameter(ps, i, parameter, jdbcType);
    }

}
