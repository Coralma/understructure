package com.coral.base.redis.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by CCC on 2016/4/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-spring-redis.xml")
public class RedisDaoTest {

    @Autowired
    protected Environment env;

    @Test
    public void testDao() {
        System.out.println(env.getProperty("redis.ip"));
    }
}
