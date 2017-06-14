package com.cccis.base.elasticsearch;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cccis.base.elasticsearch.operation.CrudOperation;
import com.cccis.base.elasticsearch.operation.SearchCondition;
import com.cccis.base.elasticsearch.operation.SearchOperation;
import com.cccis.base.elasticsearch.operation.SimpleQueryBuilder;
import com.cccis.base.elasticsearch.operation.SimpleSortBuilder;

/**
 * Created by CCC on 2016/6/16.
 */
public class MoreSearchTest {

    CrudOperation crudOperation;
    SearchOperation searchOperation;

    @Before
    public void before() {
        crudOperation = new CrudOperation("book", "yuwen");
        searchOperation = new SearchOperation(new String[]{"book"}, new String[]{"yuwen"});
    }

    @Test
    public void addData() {
        //first
        Calendar calendar = Calendar.getInstance();
        String json = "{\"name\":\"小明\",\"firstDate\":\"" + DateFormatUtils.formatDefault(calendar.getTime()) + "\",\"secondDate\":\"" + calendar.getTimeInMillis() + "\",\"content\":\"美国留给伊拉克的是个烂摊子吗\",\"contentNot\":\"美国留给伊拉克的是个烂摊子吗\",\"message\":\"find blue sky\",\"age\":15}";
        crudOperation.add("1", json);
        //second
        calendar.add(Calendar.DAY_OF_YEAR, -10);
        json = "{\"name\":\"小明他哥\",\"firstDate\":\"" + DateFormatUtils.formatDefault(calendar.getTime()) + "\",\"secondDate\":\"" + calendar.getTimeInMillis() + "\",\"content\":\"公安部：各地校车将享最高路权\",\"contentNot\":\"公安部：各地校车将享最高路权\",\"message\":\"today is a sunny day\",\"age\":30}";
        crudOperation.add("2", json);
        //third
        calendar.add(Calendar.MONTH, -3);
        json = "{\"name\":\"小刚\",\"firstDate\":\"" + DateFormatUtils.formatDefault(calendar.getTime()) + "\",\"secondDate\":\"" + calendar.getTimeInMillis() + "\",\"content\":\"中韩渔警冲突调查：韩警平均每天扣1艘中国渔船\",\"contentNot\":\"中韩渔警冲突调查：韩警平均每天扣1艘中国渔船\",\"message\":\"today we will travel to china\",\"age\":18}";
        crudOperation.add("3", json);
        //fourth
        calendar.add(Calendar.MONTH, -2);
        json = "{\"name\":\"小明的弟弟\",\"firstDate\":\"" + DateFormatUtils.formatDefault(calendar.getTime()) + "\",\"secondDate\":\"" + calendar.getTimeInMillis() + "\",\"content\":\"中国驻洛杉矶领事馆遭亚裔男子枪击 嫌犯已自首\",\"contentNot\":\"中国驻洛杉矶领事馆遭亚裔男子枪击 嫌犯已自首\",\"message\":\"i am very happy in holiday\",\"age\":40}";
        crudOperation.add("4", json);
        //fifth
        calendar.add(Calendar.MONTH, 8);
        json = "{\"name\":\"小刚的同事\",\"firstDate\":\"" + DateFormatUtils.formatDefault(calendar.getTime()) + "\",\"secondDate\":\"" + calendar.getTimeInMillis() + "\",\"content\":\"莱斯特城成功的拿到了英超冠军\",\"contentNot\":\"莱斯特城成功的拿到了英超冠军\",\"message\":\"travel to england is a happy idea\",\"age\":55}";
        crudOperation.add("5", json);
        System.out.println();
    }

    @Test
    public void delete() {
        crudOperation.delete("1", "2", "3", "4", "5");
        System.out.println();
    }

    @Test
    public void search() {
        //年龄在25-35
        SimpleQueryBuilder innerQueryBuilder1 = SimpleQueryBuilder.simpleQuery().andRange("age", 25, 35);
        //message包含very happy
        SimpleQueryBuilder innerQueryBuilder2 = SimpleQueryBuilder.simpleQuery().andString("message", "very happy");
        //把上面的两个条件或操作
        SimpleQueryBuilder innerQueryBuilder3 = SimpleQueryBuilder.simpleQuery().andChild(innerQueryBuilder1).orChild(innerQueryBuilder2);
        //把上面的复合条件做与操作
        SimpleQueryBuilder queryBuilder = SimpleQueryBuilder.simpleQuery().andString("name", "小明他哥").orChild(innerQueryBuilder3);
        SimpleSortBuilder sortBuilder = SimpleSortBuilder.simpleSort().sortAsc("age").sortDesc("name");
        SearchCondition condition = SearchCondition.searchCondition().setQueryBuilder(queryBuilder.build()).setSimpleSortBuilder(sortBuilder).setPageNo(1).setPageSize(10);
        QueryResult<MoreResult> queryResult = searchOperation.search(condition, MoreResult.class);
        Assert.assertNotNull(queryResult);
    }

    @Test
    public void search1() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -30);
        Date start = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 60);
        Date end = calendar.getTime();
//        SimpleQueryBuilder innerQueryBuilder1 = SimpleQueryBuilder.simpleQuery().andRangeDate("firstDate", start, end);
//        SimpleQueryBuilder innerQueryBuilder1 = SimpleQueryBuilder.simpleQuery().andMoreThan("age", 30);
        SimpleQueryBuilder innerQueryBuilder1 = SimpleQueryBuilder.simpleQuery().andMoreThanEquals("age", 30);
//        SimpleQueryBuilder innerQueryBuilder1 = SimpleQueryBuilder.simpleQuery().andEquals("age", 30);
//        SimpleQueryBuilder innerQueryBuilder1 = SimpleQueryBuilder.simpleQuery().andRange("age", 15, 18);
//        SimpleQueryBuilder innerQueryBuilder2 = SimpleQueryBuilder.simpleQuery().andString("contentNot", "中国发财");
//        SimpleQueryBuilder innerQueryBuilder2 = SimpleQueryBuilder.simpleQuery().andString("message", "very happy");
        SimpleSortBuilder sortBuilder = SimpleSortBuilder.simpleSort().sortAsc("age").sortDesc("name");
        SearchCondition condition = SearchCondition.searchCondition()
//                .setQueryBuilder(innerQueryBuilder2)
                .setFilterBuilder(innerQueryBuilder1.build())
                .setSimpleSortBuilder(sortBuilder)
                .setPageNo(1).setPageSize(10);
        QueryResult<MoreResult> queryResult = searchOperation.search(condition, MoreResult.class);
        QueryResult<MoreResult> queryResult1 = searchOperation.searchBatch(condition, MoreResult.class);
        Assert.assertNotNull(queryResult);
    }

    @Test
    public void search2() {
        SimpleQueryBuilder simpleQueryBuilder1 = SimpleQueryBuilder.simpleQuery().andString("name", "小明他哥");
        SimpleQueryBuilder simpleQueryBuilder2 = SimpleQueryBuilder.simpleQuery().andString("age", "55");
        SimpleQueryBuilder simpleQueryBuilder11 = SimpleQueryBuilder.simpleQuery().orChild(simpleQueryBuilder1).orChild(simpleQueryBuilder2);
        SimpleQueryBuilder simpleQueryBuilder3 = SimpleQueryBuilder.simpleQuery().andChild(simpleQueryBuilder11);
        SimpleQueryBuilder simpleQueryBuilder4 = SimpleQueryBuilder.simpleQuery().orString("name", "小明他哥").orString("age", "55");
        QueryResult<MoreResult> queryResult3 = searchOperation.search(SearchCondition.searchCondition().setFilterBuilder(simpleQueryBuilder11.build()), MoreResult.class);
        System.out.println();
    }

    @Test
    public void search3() {
        QueryResult<MoreResult> queryResult3 = searchOperation.search(SearchCondition.searchCondition().setFilterBuilder(SimpleQueryBuilder.simpleQuery().andLike("content", "国").build()).setPageNo(0).setPageSize(1), MoreResult.class);
        System.out.println();
    }
}
