package com.gitee.fastmybatis.core.ext.code.util;

/**
 * @author tanghc
 */
public class FieldUtil {

    private static final String DOT = ".";
    
	/**
	 * 过滤"."
	 * 
	 * @param field
	 * @return
	 */
	public static String dotFilter(String field) {
		if (isNotEmpty(field)) {
			if (field.indexOf(DOT) > -1) {
				String[] words = field.split("\\.");
				String ret = "";
				for (String str : words) {
					ret += upperFirstLetter(str);
				}
				return ret;
			}
		}
		return field;
	}

	/**
	 * 将第一个字母转换成大写
	 * 
	 * @param str
	 * @return
	 */
	public static String upperFirstLetter(String str) {
		if (isNotEmpty(str)) {
			String firstUpper = String.valueOf(str.charAt(0)).toUpperCase();
			str = firstUpper + str.substring(1);
		}
		return str;
	}

	/**
	 * 将第一个字母转换成小写
	 * 
	 * @param str
	 * @return
	 */
	public static String lowerFirstLetter(String str) {
		if (isNotEmpty(str)) {
			String firstLower = String.valueOf(str.charAt(0)).toLowerCase();
			str = firstLower + str.substring(1);
		}
		return str;
	}
	
	public static final char UNDERLINE = '_';

	/**
	 * 驼峰转下划线
	 * @param param
	 * @return
	 */
	public static String camelToUnderline(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			int preIndex = i - 1;
			int nextIndex = i + 1;
			if (	(
						Character.isUpperCase(c) 
						&& preIndex > 0
						&& Character.isLowerCase(param.charAt(preIndex))
					)
					||
					(
							Character.isUpperCase(c) 
							&& nextIndex < len 
							&& Character.isLowerCase(param.charAt(nextIndex))
					)
				) {
				if(i > 0) {
					sb.append(UNDERLINE);
				}
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 下划线转驼峰
	 * @param param
	 * @return
	 */
	public static String underlineToCamel(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (c == UNDERLINE) {
				if (++i < len) {
					sb.append(Character.toUpperCase(param.charAt(i)));
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	private static boolean isEmpty(String s) {
		return s == null || s.trim().length() == 0;
	}
	
	private static boolean isNotEmpty(String s) {
		return !isEmpty(s);
	}

}
