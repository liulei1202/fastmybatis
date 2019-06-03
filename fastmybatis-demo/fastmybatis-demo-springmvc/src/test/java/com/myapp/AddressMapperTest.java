package com.myapp;

import com.myapp.entity.Address;
import com.myapp.mapper.AddressMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;

/**
 * @author tanghc
 */
public class AddressMapperTest extends TestBase {

    @Autowired
    AddressMapper addressMapper;

    @Test
    public void testAdd() {
        Address address = new Address();
        address.setAddress("杭州" + System.currentTimeMillis());
        addressMapper.save(address);
        // 回写id
        System.out.println(address);
    }

    @Test
    public void testAdd2() throws InterruptedException {
        int count = 5;
        CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < count; i++) {
            final int k = i;
            new Thread(() -> {
                try {
                    latch.await();
                    Address address = new Address();
                    address.setAddress("杭州" + (System.currentTimeMillis() + k));
                    int j = addressMapper.save(address);
                    // 回写id
                    System.out.println(address);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        latch.countDown();
        Thread.sleep(Long.MAX_VALUE);
    }

}
