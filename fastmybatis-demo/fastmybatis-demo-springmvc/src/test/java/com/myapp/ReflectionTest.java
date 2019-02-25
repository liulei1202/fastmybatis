package com.myapp;

import junit.framework.TestCase;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * @author tanghc
 */
public class ReflectionTest extends TestCase {

    public void testA() {
        ReflectionUtils.doWithFields(B.class, new ReflectionUtils.FieldCallback(){
            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                System.out.println(field.toGenericString());
            }
        });

    }

    static class A {
        private int i;

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }
    }

    static class B  extends A {
        private String s;

        public String getS() {
            return s;
        }

        public void setS(String s) {
            this.s = s;
        }
    }
}
