package com.gitee.fastmybatis.core.support;

import java.util.Date;

import com.gitee.fastmybatis.core.handler.BaseFill;
import com.gitee.fastmybatis.core.handler.FillType;

/**
 * update时的字段填充<br>
 * 在做insert或update操作时,如果表里面有gmt_update字段,则自动填充时间
 * @author tanghc
 *
 */
public class DateFillUpdate extends BaseFill<Date> {

	private String columnName = "gmt_update";

	public DateFillUpdate() {
		super();
	}

	public DateFillUpdate(String columnName) {
		super();
		this.columnName = columnName;
	}

	@Override
	public FillType getFillType() {
		return FillType.UPDATE;
	}

	@Override
	public Date getFillValue(Date defaultValue) {
		return new Date();
	}

	@Override
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	
}
