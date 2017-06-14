package com.cccis.base.mongo.test;

import org.springframework.stereotype.Repository;

import com.cccis.base.mongo.MBaseDao;

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
