package com.coral.base.elasticsearch;

import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.coral.base.elasticsearch.operation.CrudOperation;

/**
 * Created by CCC on 2016/6/13.
 */
public class CrudOperationTest {

    CrudOperation operation;

    @Before
    public void before() {
        operation = new CrudOperation("sample", "fulltext");
    }

    @Test
    public void testAdd() {
        String json = "{\"hello\":\"今天周不知道\",\"odate\":\"2016-08-19\",\"whythis\":\"large large blank\"}";
        operation.add(null, json);
    }

    @Test
    public void testBatchAdd() {
        String json = "{" +
                "\"user\":\"abc_{1}\"," +
                "\"postDate\":\"2016-07-{2}\"," +
                "\"message\":\"find {3} sky\"," +
                "\"age\":{4}" +
                "}";
        Random random = new Random();

        for (int i = 0; i < 10000; i++) {
            String newJson = json.replace("{1}", String.valueOf(random.nextInt(10000)))
                    .replace("{2}", String.valueOf(random.nextInt(31)))
                    .replace("{3}", String.valueOf(random.nextInt(1000)))
                    .replace("{4}", String.valueOf(random.nextInt(60)));

            operation.add(newJson);
        }
    }

    @Test
    public void testGet() {
        operation.get("AVVJATsBdWGxvaSMGJA-");
        Assert.assertNotNull("");
    }

    @Test
    public void testDelete() {
        operation.delete("AVVJATsBdWGxvaSMGJA-");
        Assert.assertNotNull("");
    }
}
