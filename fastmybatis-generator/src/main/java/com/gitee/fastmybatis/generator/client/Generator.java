package com.gitee.fastmybatis.generator.client;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.velocity.VelocityContext;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.gitee.fastmybatis.generator.entity.ClientParam;
import com.gitee.fastmybatis.generator.entity.DataBaseConfig;
import com.gitee.fastmybatis.generator.entity.TplInfo;
import com.gitee.fastmybatis.generator.generator.SQLContext;
import com.gitee.fastmybatis.generator.generator.SQLService;
import com.gitee.fastmybatis.generator.generator.SQLServiceFactory;
import com.gitee.fastmybatis.generator.generator.TableDefinition;
import com.gitee.fastmybatis.generator.generator.TableSelector;
import com.gitee.fastmybatis.generator.util.FileUtil;
import com.gitee.fastmybatis.generator.util.FormatUtil;
import com.gitee.fastmybatis.generator.util.VelocityUtil;

public class Generator {
	
	/**
	 * 生成全部
	 * @param clientParam
	 * @param dest
	 */
	public void generateCodeAll(ClientParam clientParam, String dest) {
		DataBaseConfig dataBaseConfig = clientParam.buildDataBaseConfig();
		SQLService service = SQLServiceFactory.build(dataBaseConfig);
		
		List<TableDefinition> allTable = service.getTableSelector(dataBaseConfig).getAllTableDefinitions();
		
		FileUtil.deleteDir(new File(dest));
		
		for (TableDefinition td : allTable) {
			clientParam.setTableName(td.getTableName());
			this.generateCode(clientParam, dest);
		}
	}
	
	public void generateCode(ClientParam clientParam, String dest) {
		DataBaseConfig dataBaseConfig = clientParam.buildDataBaseConfig();
		SQLContext sqlContext = this.buildClientSQLContextList(clientParam, dataBaseConfig);

		List<String> tpls = clientParam.getTplList();

		setPackageName(sqlContext, clientParam.getPackageName());

		FileUtil.createFolder(dest);

		for (String tplFile : tpls) {
			TplInfo template = this.buildTempObj(tplFile.trim());
			if (template == null) {
				continue;
			}
			String content = doGenerator(sqlContext, template.getContent());
			String fileName = doGenerator(sqlContext, template.getFileName());
			String savePath = doGenerator(sqlContext,template.getSavePath());

			content = this.formatCode(fileName, content);

			String saveDir = dest + File.separator + savePath;
			
			FileUtil.createFolder(saveDir);

			String filePathName = saveDir + File.separator +fileName;
			
			FileUtil.write(content, filePathName, clientParam.getCharset());
		}

	}

	private TplInfo buildTempObj(String tplFile) {
		if (StringUtils.isEmpty(tplFile)) {
			return null;
		}
		
		String json = FileUtil.readFromClassPath("/tpl/" + tplFile);
		if (StringUtils.isEmpty(json)) {
			return null;
		}
		
		TplInfo temp = JSON.parseObject(json, TplInfo.class);
		String contentFileName = temp.getContentFileName();
		
		if(StringUtils.isEmpty(contentFileName)) {
			contentFileName = tplFile + "_cont";
			temp.setContentFileName(contentFileName);
		}

		if (temp != null) {
			String contentClassPath = "/tpl/" + temp.getContentFileName();
			String content = FileUtil.readFromClassPath(contentClassPath);
			temp.setContent(content);
		}

		return temp;
	}
	
	/**
	 * 返回SQL上下文列表
	 * 
	 * @param tableNames
	 * @return
	 */
	private SQLContext buildClientSQLContextList(ClientParam generatorParam, DataBaseConfig dataBaseConfig) {

		List<String> tableNames = Arrays.asList(generatorParam.getTableName().trim());

		SQLService service = SQLServiceFactory.build(dataBaseConfig);

		TableSelector tableSelector = service.getTableSelector(dataBaseConfig);
		tableSelector.setSchTableNames(tableNames);

		List<TableDefinition> tableDefinitions = tableSelector.getTableDefinitions();

		SQLContext context = new SQLContext(tableDefinitions.get(0));
		context.setParam(generatorParam.getParam());

		return context;
	}
	
	private void setPackageName(SQLContext sqlContext, String packageName) {
		if (StringUtils.hasText(packageName)) {
			sqlContext.setPackageName(packageName);
		}
	}

	private String doGenerator(SQLContext sqlContext, String template) {
		if (template == null) {
			return "";
		}
		VelocityContext context = new VelocityContext();
		
		TableDefinition tableDefinition = sqlContext.getTableDefinition();

		context.put("context", sqlContext);
		context.put("param", sqlContext.getParam());
		context.put("table", tableDefinition);
		context.put("pk", tableDefinition.getPkColumn());
		context.put("columns", tableDefinition.getColumnDefinitions());

		return VelocityUtil.generate(context, template);
	}

	// 格式化代码
	private String formatCode(String fileName, String content) {
		if (fileName.endsWith(".xml")) {
			return FormatUtil.formatXml(content);
		}
        if(fileName.toLowerCase().endsWith(".java")) {
            return FormatUtil.formatJava(content);
        }
		return content;
	}
}
