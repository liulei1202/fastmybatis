package com.gitee.fastmybatis.generator;

import java.io.FileNotFoundException;

import org.junit.Test;

import de.hunsicker.jalopy.Jalopy;
import junit.framework.TestCase;

public class JalopyTest extends TestCase {
    // 格式化java代码
    @Test
    public void testFormat() throws FileNotFoundException {
        StringBuffer output = new StringBuffer();
        Jalopy j = new Jalopy();
        j.setEncoding("UTF-8");
        j.setInput("public class JalopyTest {"
                + "public void foo() {if(true) System.out.println(1);}  int getI(){return 1;}}", "A.java"); // 第二个参数随便填个java类名
        j.setOutput(output);
        j.format();
        
        System.out.println(output);
    }
}
