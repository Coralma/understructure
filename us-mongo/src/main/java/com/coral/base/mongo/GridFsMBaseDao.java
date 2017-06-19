package com.coral.base.mongo;

import java.io.InputStream;
import java.util.List;

import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

public abstract class GridFsMBaseDao {

    @Autowired
    protected GridFsTemplate mongoTemplate;

    public void save(InputStream in, String fileName, DBObject metadata) {
        mongoTemplate.store(in, fileName, metadata);
    }

    public List<GridFSDBFile> findByQuery(Query query) {
        return mongoTemplate.find(query);
    }

    public GridFsTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public void setMongoTemplate(GridFsTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public abstract Class getEntityClass();

}
