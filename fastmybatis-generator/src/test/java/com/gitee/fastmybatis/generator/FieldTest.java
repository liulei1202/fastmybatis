package com.gitee.fastmybatis.generator;

import org.junit.Test;

import com.gitee.fastmybatis.generator.generator.ColumnDefinition;

public class FieldTest extends AppTest {

	@Test
	public void testField() {
		ColumnDefinition cd = new ColumnDefinition();

		cd.setColumnName("USER_AGE");
		System.out.println(cd.getJavaFieldName());

		cd.setColumnName("user_age");
		System.out.println(cd.getJavaFieldName());

		cd.setColumnName("username");
		System.out.println(cd.getJavaFieldName());

		cd.setColumnName("USERNAME");
		System.out.println(cd.getJavaFieldName());

		cd.setColumnName("userName");
		System.out.println(cd.getJavaFieldName());
	}
}
