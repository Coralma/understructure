package com.coral.base.session;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.coral.base.session.data.SessionGenerator;

/**
 * Created by CCC on 2016/2/23.
 */

public class SessionTest {

    SessionGenerator sessionGenerator;

    @Before
    public void before() {
        String paths[] = { "spring-redis.xml" };
        ApplicationContext ctx = new ClassPathXmlApplicationContext(paths);
        sessionGenerator = (SessionGenerator)ctx.getBean("sessionGenerator");
    }

    @Test
    public void createSession1() {
        sessionGenerator.setAttribute("1", "2");
        String sessionId = sessionGenerator.createSession();
        System.out.println(sessionId);
    }
}
