package com.cccis.base.redis.test;

import org.springframework.stereotype.Repository;

import com.cccis.base.redis.RedisBaseDao;

/**
 * Created by CCC on 2016/4/27.
 */
@Repository(SampleRedisDao.SPRING_BEAN_NAME)
public class SampleRedisDao extends RedisBaseDao<SampleModel> {

    public final static String SPRING_BEAN_NAME = "redis.dao.SampleRedisDao";

}
