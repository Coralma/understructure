package com.coral.base.mongo.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by CCC on 2015/11/26.
 */
public class MongoTestMain {

    public static void main(String[] args) throws Exception {
        MongoTestModel mt = new MongoTestModel();
        mt.setName("Coral");
        mt.setAge(30);

        String paths[] = { "spring-context.xml" };
        //这个xml文件是Spring配置beans的文件，顺带一提，路径 在整个应用的根目录
        ApplicationContext ctx = new ClassPathXmlApplicationContext(paths);
        MongoTestDao dao = (MongoTestDao) ctx.getBean(MongoTestDao.SPRING_BEAN_NAME);
        dao.save(mt);
    }
}
