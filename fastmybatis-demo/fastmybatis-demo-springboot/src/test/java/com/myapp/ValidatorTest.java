package com.myapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.JSON;

import junit.framework.TestCase;

public class ValidatorTest extends TestCase {

    static class A {
        @NotNull(message = "A.a not null")
        String a;
        @Min(value = 1, message = "A.i must >= 1")
        int i;
        @Override
        public String toString() {
            return "class A";
        }
    }

    static class B {
        @NotNull(message = "B.a not null")
        String a;
        @Min(value = 1, message = "B.i must >= 1")
        int i;
        @Override
        public String toString() {
            return "class B";
        }
    }

    public void testV() {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        
        int threadCount = 10;
        final CountDownLatch latch = new CountDownLatch(1);
        
        final A a = new A();
        final B b = new B();

        for (int i = 0; i < threadCount; i++) {
            final int j = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Object obj;
                    if(j %2 == 0) {
                        obj = a;
                    } else {
                        obj = b;
                    }
                    System.out.println(Thread.currentThread().getName() + "准备就绪,验证" + obj);
                    try {
                        latch.await();
                        
                        Set<ConstraintViolation<Object>> set = validator.validate(obj);
                        
                        List<String> errors = new ArrayList<String>();
                        
                        for (ConstraintViolation<Object> c : set) {
                            errors.add(c.getMessage());
                        }
                        
                        System.out.println(Thread.currentThread().getName() + ">>" + JSON.toJSONString(errors));
                        
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    
                }
            }, "线程" + i).start();
        }
        
        latch.countDown();
        System.out.println("end.");
    
    }
    
}
