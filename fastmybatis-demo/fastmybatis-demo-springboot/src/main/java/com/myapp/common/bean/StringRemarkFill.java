package com.myapp.common.bean;

import com.gitee.fastmybatis.core.handler.BaseFill;
import com.gitee.fastmybatis.core.handler.FillType;
import com.myapp.entity.TUser;

public class StringRemarkFill extends BaseFill<String> {

	@Override
	public String getColumnName() {
		return "remark";
	}

	@Override
	public Class<?>[] getTargetEntityClasses() {
		return new Class<?>[] { TUser.class };
	}

	@Override
	public FillType getFillType() {
		return FillType.INSERT;
	}

	@Override
	protected Object getFillValue(String defaultValue) {
		return "备注默认内容";
	}

}
