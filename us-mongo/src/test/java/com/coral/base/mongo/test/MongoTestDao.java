package com.coral.base.mongo.test;

import com.coral.base.mongo.MBaseDao;
import org.springframework.stereotype.Repository;

/**
 * Created by CCC on 2015/11/26.
 */
@Repository(MongoTestDao.SPRING_BEAN_NAME)
public class MongoTestDao extends MBaseDao<MongoTestModel> {

    public final static String SPRING_BEAN_NAME = "mongo.dao.MongoTestDao";

    @Override
    public Class getEntityClass() {
        return MongoTestModel.class;
    }
}
