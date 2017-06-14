package com.cccis.base.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by CCC on 2016/5/16.
 */
public class RedisBaseTest {

    private RedisBaseDao<String> redisBaseDao;

    @Before
    public void init() {
        final String[] paths = {"spring-redis.xml"};
        final ApplicationContext ctx = new ClassPathXmlApplicationContext(paths);
        redisBaseDao = (RedisBaseDao)ctx.getBean("redisBaseDao");
    }

    @Test
    public void testString() {
        redisBaseDao.set("key1", "value1");
        String value = redisBaseDao.get("key1");
        System.out.println(value);

        Map<String, String> map = new HashMap<String, String>();
        map.put("key2", "value2");
        map.put("key3", "value3");
        redisBaseDao.mSet(map);

        List<String> list = new ArrayList<String>();
        list.add("key2");
        list.add("key3");
        List<String> result = redisBaseDao.mGet(list);
        System.out.println(result);

        redisBaseDao.hSet("hkey1", "key1", "value1");
        String hResult = redisBaseDao.hGet("hkey1", "key1");
        System.out.println(hResult);

        Map<String, String> hmap = new HashMap<String, String>();
        hmap.put("key1", "value1");
        hmap.put("key2", "value2");
        redisBaseDao.hSetAll("hkey2", hmap);
        Map<String, String> hResultMap = redisBaseDao.hGetAll("hkey2");
        System.out.println(hResultMap);

        redisBaseDao.lPush("lkey1", "value1", "value2", "value3", "value4");
        redisBaseDao.rPush("lkey1", "value5", "value6");
        List<String> llist1 = redisBaseDao.lRange("lkey1", 1, 2);
        System.out.println(llist1);
        String lvalue1 = redisBaseDao.lPop("lkey1");
        System.out.println(lvalue1);
        String lvalue2 = redisBaseDao.rPop("lkey1");
        System.out.println(lvalue2);
        redisBaseDao.lRemove("lkey1", -1, "value6");

        redisBaseDao.sAdd("skey1", "value1", "value2", "value3", "value1");
        Set<String> slist1 = redisBaseDao.sMembers("skey1");
        System.out.println(slist1);
        long scount = redisBaseDao.sSize("skey1");
        System.out.println(scount);
        redisBaseDao.sRemove("skey1", "value1");

        redisBaseDao.zAdd("zkey1", "value1", 100);
        redisBaseDao.zAdd("zkey1", "value2", 200);
        redisBaseDao.zAdd("zkey1", "value3", 300);
        redisBaseDao.zAdd("zkey1", "value4", 150);
        redisBaseDao.zAdd("zkey1", "value5", 180);
        Set<String> zset1 = redisBaseDao.zRange("zkey1", 1, 2);
        System.out.println(zset1);
        Set<String> zset2 = redisBaseDao.zRangeByScore("zkey1", 100, 200);
        System.out.println(zset2);
        Set<String> zset3 = redisBaseDao.zRevRange("zkey1", 1, 2);
        System.out.println(zset3);
        Long l = redisBaseDao.zRemove("zkey1", "value2");
        System.out.println(l);
        Long l2 = redisBaseDao.zSize("zkey1");
        System.out.println(l2);

        Assert.assertNotNull("1");
    }
}
