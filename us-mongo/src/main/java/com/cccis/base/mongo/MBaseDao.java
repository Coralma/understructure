package com.cccis.base.mongo;

import java.io.InputStream;
import java.util.List;

import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

public abstract class MBaseDao<T> {

    @Autowired
    protected MongoTemplate mongoTemplate;

    @Autowired
    private GridFsTemplate girdFsTemplate;

    public GridFSFile storeGridFSFile(InputStream in, String fileName, DBObject metadata) {
        return getGirdFsTemplate().store(in, fileName, metadata);
    }

    public void removeGirdFSFile(String oid) {
        Criteria criteria = Criteria.where("metadata.uuid").is(oid);
        Query query = new Query(criteria);
        getGirdFsTemplate().delete(query);
    }

    public GridFSFile findGridFSFileByOID(String oid) {
        Criteria criteria = Criteria.where("metadata.uuid").is(oid);
        Query query = new Query(criteria);
        return getGirdFsTemplate().findOne(query);
    }

    public void save(T t) {
        mongoTemplate.save(t);
    }

    public void remove(T t) {
        mongoTemplate.remove(t);
    }

    public List<T> findAll() {
        return mongoTemplate.findAll(getEntityClass());
    }

    public List<T> findByQuery(Query query) {
        return mongoTemplate.find(query, getEntityClass());
    }

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public abstract Class<T> getEntityClass();

    public GridFsTemplate getGirdFsTemplate() {
        return girdFsTemplate;
    }

    public void setGirdFsTemplate(GridFsTemplate girdFsTemplate) {
        this.girdFsTemplate = girdFsTemplate;
    }
}
