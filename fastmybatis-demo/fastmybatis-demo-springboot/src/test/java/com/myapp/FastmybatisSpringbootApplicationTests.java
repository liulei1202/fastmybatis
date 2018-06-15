package com.myapp;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FastmybatisSpringbootApplicationTests {

    public void print(Object o) {
        System.out.println("=================");
        System.out.println(o);
        System.out.println("=================");
    }

}
