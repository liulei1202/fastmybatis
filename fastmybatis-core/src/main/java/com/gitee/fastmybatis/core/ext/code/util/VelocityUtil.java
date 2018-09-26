package com.gitee.fastmybatis.core.ext.code.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.log.NullLogChute;

/**
 * Velocity工具类,根据模板内容生成文件
 * @author tanghc
 */
public class VelocityUtil {

	private VelocityUtil() {
		super();
	}

	static {
		// 禁止输出日志
		Velocity.setProperty(Velocity.RUNTIME_LOG_LOGSYSTEM, new NullLogChute());
		Velocity.init();
	}

	private static String LOG_TAG = "fastmybatis";
	private static String UTF8 = "UTF-8";

	public static String generate(VelocityContext context, InputStream inputStream) {
		try {
			return generate(context, inputStream, UTF8);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	public static String generate(VelocityContext context, InputStream inputStream, String charset) throws UnsupportedEncodingException {
		return generate(context, new InputStreamReader(inputStream, charset));
	}

	public static String generate(VelocityContext context, Reader reader) {
		StringWriter writer = new StringWriter();
		// 不用vm文件
		Velocity.evaluate(context, writer, LOG_TAG, reader);
		
		IOUtils.closeQuietly(writer);
		IOUtils.closeQuietly(reader);

		return writer.toString();

	}

	public static String generate(VelocityContext context, String template) {
		return generate(context, new StringReader(template));
	}
}
