package com.cccis.base.elasticsearch;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cccis.base.elasticsearch.operation.IndexOperation;
import com.cccis.base.elasticsearch.operation.MappingBuilder;

public class IndexOperationTest {

    IndexOperation operation;

    @Before
    public void before() {
        operation = new IndexOperation();
    }

    @Test
    public void testAddIndexWithMapping() {
        MappingBuilder mappingFields = MappingBuilder.getInstance();
        mappingFields.addFieldUsingLike("field2", "String");
        boolean result = operation.createIndex("test2", "type1", mappingFields);
        Assert.assertTrue(result);
    }

    @Test
    public void testAddMapping() {
        MappingBuilder mappingFields = MappingBuilder.getInstance();
        mappingFields.addFieldUsingLike("column2", "String");
        boolean result = operation.addMapping("test2", "type1", mappingFields);
        Assert.assertTrue(result);
    }

    @Test
    public void testDeleteIndex() {
        boolean result = operation.deleteIndex("test2");
        Assert.assertTrue(result);
    }

}
