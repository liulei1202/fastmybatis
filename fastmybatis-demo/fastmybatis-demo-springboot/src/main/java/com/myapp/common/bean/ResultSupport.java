package com.myapp.common.bean;

/**
 * 结果返回抽象
 * @author tanghc
 *
 * @param <CodeType> 状态码类型
 * @param <DataType> 数据体类型，通常是Object
 */
public abstract class ResultSupport<CodeType, DataType> implements Result<CodeType, DataType> {

	private CodeType code;
	private String msg;
	private DataType data;

	@Override
	public void setCode(CodeType code) {
		this.code = code;
	}

	@Override
	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public void setData(DataType data) {
		this.data = data;
	}

	protected CodeType fatchCode() {
		return code;
	}

	protected String fatchMsg() {
		return msg;
	}

	protected DataType fatchData() {
		return data;
	}

}
