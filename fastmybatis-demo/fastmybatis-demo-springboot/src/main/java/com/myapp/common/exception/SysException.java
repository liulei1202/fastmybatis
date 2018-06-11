package com.myapp.common.exception;

import com.myapp.common.msg.Errors;
import com.myapp.common.msg.MsgInfo;

public class SysException extends RuntimeException {
	private static final long serialVersionUID = 2146368944489773928L;

	private MsgInfo<Integer> msgInfo;
	private Object data;
	
	public SysException(Exception e) {
		this(e.getMessage());
	}
	
	public SysException(MsgInfo<Integer> msgType,Object data) {
		this(msgType);
		this.data = data;
	}
	
	public SysException(MsgInfo<Integer> msgType) {
		this.msgInfo = msgType;
	}
	
	public SysException(final String msg) {
		this(new SysMsgInfo(msg));
	}
	
	public MsgInfo<Integer> getMsgInfo() {
		return msgInfo;
	}

	public void setMsgInfo(MsgInfo<Integer> msgInfo) {
		this.msgInfo = msgInfo;
	}
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}


	private static class SysMsgInfo implements MsgInfo<Integer> {
		
		private String msg;
		
		public SysMsgInfo(String msg) {
			super();
			this.msg = msg;
		}

		@Override
		public String getMsg() {
			return msg;
		}

		@Override
		public Integer getCode() {
			return Errors.SYS_ERROR.getCode();
		}
	}

}
