package com.gitee.fastmybatis.core.handler;

/**
 * @author tanghc
 */
public class CustomIdTypeHandler extends AbstractTypeHandlerAdapter<Object> {

    @Override
    protected Object convertValue(Object columnValue) {
        return null;
    }

    @Override
    protected Object getFillValue(Object defaultValue) {
        try {
            return Identitys.get();
        } finally {
            Identitys.remove();
        }
    }
}
