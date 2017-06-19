package com.coral.base.elasticsearch.operation;

import com.coral.base.elasticsearch.ClientInstance;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;

public class IndexOperation {

    public boolean createIndex(String indexName) {
        boolean result = true;
        try {
            CreateIndexRequestBuilder createIndexRequestBuilder = ClientInstance.getClient().admin().indices().prepareCreate(indexName);
            CreateIndexResponse response = createIndexRequestBuilder.execute().actionGet();
            result = response.isAcknowledged();
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    public boolean createIndex(String indexName, String typeName, MappingBuilder mapping) {
        boolean result = true;
        try {
            CreateIndexRequestBuilder createIndexRequestBuilder = ClientInstance.getClient().admin().indices().prepareCreate(indexName).setSettings(mapping.buildDefaultSetting()).addMapping(typeName, mapping.buildPropertyMapping());
            CreateIndexResponse response = createIndexRequestBuilder.execute().actionGet();
            result = response.isAcknowledged();
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    public boolean addMapping(String indexName, String typeName, MappingBuilder mapping) {
        boolean result = true;
        try {
            PutMappingResponse response = ClientInstance.getClient().admin().indices().preparePutMapping(indexName).setType(typeName).setSource(mapping.buildPropertyMapping()).execute().actionGet();
            result = response.isAcknowledged();
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    public boolean deleteIndex(String... indexName) {
        boolean result = true;
        try {
            DeleteIndexRequestBuilder builder = ClientInstance.getClient().admin().indices().prepareDelete(indexName);
            DeleteIndexResponse response = builder.execute().actionGet();
            result = response.isAcknowledged();
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }
}
