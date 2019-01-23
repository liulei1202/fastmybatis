package com.gitee.fastmybatis.core.ext.code.client;

import com.gitee.fastmybatis.core.ext.code.NotEntityException;
import com.gitee.fastmybatis.core.ext.code.generator.MapperContext;
import com.gitee.fastmybatis.core.ext.code.generator.TableDefinition;
import com.gitee.fastmybatis.core.ext.code.generator.TableSelector;
import com.gitee.fastmybatis.core.ext.code.util.VelocityUtil;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * 代码生成器，根据定义好的velocity模板生成代码
 * @author tanghc
 *
 */
public class FileCodeGenerator {

	private static final String UTF8 = "UTF-8";
	
	public String generateCode(ClientParam clientParam) throws NotEntityException, IOException {
		InputStream templateInputStream = this.buildTemplateInputStream(clientParam);
		MapperContext sqlContext = this.buildClientSQLContextList(clientParam);
		VelocityContext context = new VelocityContext();

		TableDefinition tableDefinition = sqlContext.getTableDefinition();
		
		// vm模板中的变量
		context.put("context", sqlContext);
		context.put("table", tableDefinition);
		context.put("pk", tableDefinition.getPkColumn());
		context.put("columns", tableDefinition.getTableColumns());
		context.put("allColumns", tableDefinition.getAllColumns());
		context.put("countExpression", clientParam.getCountExpression());
		context.put("associations", tableDefinition.getAssociationDefinitions());

		return VelocityUtil.generate(context, templateInputStream);
	}
	
	/** 返回模板文件内容 
	 * @throws IOException */
	private InputStream buildTemplateInputStream(ClientParam clientParam) throws IOException {
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
			throw e;
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
	 * @param clientParam 参数
	 * @return 返回SQL上下文
	 * @throws NotEntityException 
	 */
	private MapperContext buildClientSQLContextList(ClientParam clientParam) throws NotEntityException {
		Class<?> entityClass = clientParam.getEntityClass();
		if(entityClass == Object.class || entityClass == Void.class) {
		    throw new NotEntityException();
		}
		TableSelector tableSelector = new TableSelector(entityClass,clientParam.getConfig());

		TableDefinition tableDefinition = tableSelector.getTableDefinition();

		MapperContext context = new MapperContext(tableDefinition);
		
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
