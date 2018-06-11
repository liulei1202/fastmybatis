package com.myapp.common.bean;

import com.myapp.common.exception.SysException;
import com.myapp.common.msg.ErrorMeta;
import com.myapp.common.msg.Errors;
import com.myapp.common.msg.MsgInfo;

/**
 * 返回结果工厂类
 * 
 * @author tanghc
 *
 */
public class Results {

	private static final ErrorMeta SUCEESS = Errors.SUCCESS;

	public static ResultBean success() {
		ResultBean bean = new ResultBean();
		bean.setCode(SUCEESS.getCode());
		bean.setMsg(SUCEESS.getMsg());
		return bean;
	}

	public static ResultBean success(Object data) {
		ResultBean bean = success();
		bean.setData(data);
		return bean;
	}

	public static ResultBean error(Integer code, String msg) {
		ResultBean bean = new ResultBean();
		bean.setCode(code);
		bean.setMsg(msg);
		return bean;
	}
	
	public static ResultBean error(Integer code, String msg,Object data) {
		ResultBean bean = new ResultBean();
		bean.setCode(code);
		bean.setMsg(msg);
		bean.setData(data);
		return bean;
	}

	public static ResultBean error(MsgInfo<Integer> msgInfo) {
		return error(msgInfo.getCode(), msgInfo.getMsg());
	}

	public static ResultBean error(Exception e) {
		int errCode = Errors.SYS_ERROR.getCode();
		String msg = e.getMessage();
		Object data = null;
		if (e instanceof SysException) {
			SysException ex = ((SysException) e);
			errCode = ex.getMsgInfo().getCode();
			data = ex.getData();
		}
		return error(errCode, msg, data);
	}
}
