package com.gitee.fastmybatis.generator.entity;

public class TplInfo {
	private String savePath;
	private String fileName;
	private String content;
	private String contentFileName; // 内容文件名,默认为模板文件名 + "_cont",如dao.tpl_cont

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentFileName() {
		return contentFileName;
	}

	public void setContentFileName(String contentFileName) {
		this.contentFileName = contentFileName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
