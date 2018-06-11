package com.myapp.common.msg;

/**
 * 定义错误的地方
 * @author tanghc
 *
 */
public interface Errors {

	ErrorMeta SUCCESS = new ErrorMeta(0, "success");
	ErrorMeta UNKNOW_ERROR = new ErrorMeta(98, "未知错误");
	ErrorMeta SYS_ERROR = new ErrorMeta(99, "系统错误");
	 
	ErrorMeta NO_RECORD = new ErrorMeta(1, "无操作记录");
	ErrorMeta ERROR_VALIDATE = new ErrorMeta(2, "验证失败");
	ErrorMeta NULL_OBJECT = new ErrorMeta(3,"null对象");
	ErrorMeta ERROR_SERACH = new ErrorMeta(4, "查询错误");
	ErrorMeta ERROR_EXPORT = new ErrorMeta(5, "导出错误");
	ErrorMeta CLASS_NEW_ERROR = new ErrorMeta(7,"系统错误");
	
	ErrorMeta ERROR_SAVE = new ErrorMeta(10,"保存失败");
	ErrorMeta ERROR_UPDATE = new ErrorMeta(11,"修改失败");
	ErrorMeta DELETE_UPDATE = new ErrorMeta(12,"删除失败");
	ErrorMeta RECORD_EXSIT = new ErrorMeta(13,"记录已存在");
	ErrorMeta ERROR_OPT = new ErrorMeta(14,"非法操作");
	
	
}
