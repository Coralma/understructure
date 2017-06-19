/**
 *
 */
package com.coral.base.mongo;

import java.net.UnknownHostException;

import com.mongodb.Mongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Basic configuration for mongodb.
 */
@Configuration
@EnableMongoRepositories
@PropertySource({ "classpath:mongo.properties" })
public class MongoConfiguration {

    private final static String MONGODB_HOST = "mongodb.host";

    private final static String MONGODB_PORT = "mongodb.port";

    private final static String MONGODB_DATABASE = "mongodb.database";

    private final static String MONGODB_USERNAME = "mongodb.username";

    private final static String MONGODB_PASSWORD = "mongodb.password";

    @Autowired
    protected Environment env;

    @Bean
    public Mongo mongo() throws UnknownHostException {
        String port = env.getProperty(MONGODB_PORT);
        if(port == null) {
            Mongo mongo = new Mongo(env.getProperty(MONGODB_HOST));
            return mongo;
        } else {
            Mongo mongo = new Mongo(env.getProperty(MONGODB_HOST), Integer.parseInt(port));
            return mongo;
        }
    }

    @Bean
    public MongoTemplate mongoTemplate(Mongo mongo) {
        return new MongoTemplate(mongo, env.getProperty(MONGODB_DATABASE));
    }

    @Bean
    public GridFsTemplate gridFsTemplate(Mongo mongo) {
        String username = env.getProperty(MONGODB_USERNAME);
        String password = env.getProperty(MONGODB_PASSWORD);
        MongoDbFactory dbFactory = null;
        if(username != null && password != null) {
            UserCredentials userCredentials = new UserCredentials(username,password);
            dbFactory = new SimpleMongoDbFactory(mongo, env.getProperty(MONGODB_DATABASE), userCredentials);
        } else {
            dbFactory = new SimpleMongoDbFactory(mongo, env.getProperty(MONGODB_DATABASE));
        }
        MongoMappingContext mappingContext = new MongoMappingContext();
        MappingMongoConverter mongoConverter = new MappingMongoConverter(dbFactory, mappingContext);
        return new GridFsTemplate(dbFactory, mongoConverter);
    }
}
