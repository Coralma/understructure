package com.coral.base.redis.test;

import com.coral.base.redis.RedisBaseDao;
import org.springframework.stereotype.Repository;

/**
 * Created by CCC on 2016/4/27.
 */
@Repository(SampleRedisDao.SPRING_BEAN_NAME)
public class SampleRedisDao extends RedisBaseDao<SampleModel> {

    public final static String SPRING_BEAN_NAME = "redis.dao.SampleRedisDao";

}
