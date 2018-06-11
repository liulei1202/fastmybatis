package com.gitee.fastmybatis.core.ext.code.client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import com.gitee.fastmybatis.core.ext.code.generator.SQLContext;
import com.gitee.fastmybatis.core.ext.code.generator.TableDefinition;
import com.gitee.fastmybatis.core.ext.code.generator.TableSelector;
import com.gitee.fastmybatis.core.ext.code.util.VelocityUtil;

/**
 * 代码生成器，根据定义好的velocity模板生成代码
 * @author tanghc
 *
 */
public class Generator {
	
	private static final Charset UTF8 = Charsets.toCharset("UTF-8");
	
	public String generateCode(ClientParam clientParam) throws FileNotFoundException {
		InputStream templateInputStream = this.buildTemplateInputStream(clientParam);
		SQLContext sqlContext = this.buildClientSQLContextList(clientParam);
		VelocityContext context = new VelocityContext();

		TableDefinition tableDefinition = sqlContext.getTableDefinition();
		
		// vm模板中的变量
		context.put("context", sqlContext);
		context.put("table", tableDefinition);
		context.put("pk", tableDefinition.getPkColumn());
		context.put("columns", tableDefinition.getTableColumns());
		context.put("allColumns", tableDefinition.getAllColumns());
		context.put("countExpression", clientParam.getCountExpression());

		return VelocityUtil.generate(context, templateInputStream);
	}
	
	/** 返回模板文件内容 */
	private InputStream buildTemplateInputStream(ClientParam clientParam) throws FileNotFoundException {
	    // 模板文件
		DefaultResourceLoader templateLoader = new DefaultResourceLoader(); 
		Resource vmResource = templateLoader.getResource(clientParam.getTemplateLocation());
		
		try {
			if(StringUtils.isNotBlank(clientParam.getGlobalVmLocation())) {
			    // 全局模板文件
				DefaultResourceLoader globalVmResourceLoader = new DefaultResourceLoader(); 
				Resource globalVmResource = globalVmResourceLoader.getResource(clientParam.getGlobalVmLocation());
				return this.mergeGlobalVm(vmResource, globalVmResource,clientParam.getGlobalVmPlaceholder());
			}else {
				return vmResource.getInputStream();
			}
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	/** 合并全局模板,globalVmResource的内容合并到vmResource中 */
	private InputStream mergeGlobalVm(Resource vmResource,Resource globalVmResource,String placeholder) throws IOException {
		String vmContent = IOUtils.toString(vmResource.getInputStream(), UTF8);
		String globalVmContent = IOUtils.toString(globalVmResource.getInputStream(), UTF8);
		
		String finalContent = vmContent.replace(placeholder, globalVmContent);
		
		return IOUtils.toInputStream(finalContent, UTF8);
	}
	
	/**
	 * 返回SQL上下文列表
	 * 
	 * @param tableNames
	 * @return
	 */
	private SQLContext buildClientSQLContextList(ClientParam clientParam) {
		Class<?> entityClass = clientParam.getEntityClass();
		TableSelector tableSelector = new TableSelector(entityClass,clientParam.getConfig());

		TableDefinition tableDefinition = tableSelector.getTableDefinition();

		SQLContext context = new SQLContext(tableDefinition);
		
		String namespace = this.buildNamespace(clientParam.getMapperClass());
		context.setClassName(entityClass.getName());
		context.setClassSimpleName(entityClass.getSimpleName());
		context.setPackageName(entityClass.getPackage().getName());
		context.setNamespace(namespace);
		
		return context;
	}
	
	private String buildNamespace(Class<?> mapperClass) {
		return mapperClass.getName();
	}

}
