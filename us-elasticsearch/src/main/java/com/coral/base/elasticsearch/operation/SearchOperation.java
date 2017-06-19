package com.coral.base.elasticsearch.operation;

import com.coral.base.elasticsearch.BaseResult;
import com.coral.base.elasticsearch.ClientInstance;
import com.coral.base.elasticsearch.ElasticBeanUtils;
import com.coral.base.elasticsearch.QueryResult;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScriptSortBuilder;

/**
 * search elastic search
 */
public class SearchOperation {

    private String[] indexes;
    private String[] types;

    public SearchOperation(String[] indexes, String[] types) {
        this.indexes = indexes;
        this.types = types;
    }

    public SearchOperation(String index, String type) {
        this.indexes = new String[]{index};
        this.types = new String[]{type};
    }

    /**
     * return with object
     * @param condition
     * @param clazz
     * @param <T>
     * @return
     */
    public <T extends BaseResult> QueryResult search(SearchCondition condition, Class<T> clazz) {
        SearchResponse response = searchByQuery(condition);
        return getResult(response, clazz);
    }

    /**
     *
     * @param condition
     * @param clazz
     * @param <T>
     * @return
     */
    public <T extends BaseResult> QueryResult searchBatch(SearchCondition condition, Class<T> clazz) {
        TimeValue tv = new TimeValue(1000 * 60);
        SearchRequestBuilder searchRequestBuilder = getRequestBuilder(condition);
        searchRequestBuilder.setScroll(tv).setFrom(0).setSize(1000);
        SearchResponse response = searchByQuery(searchRequestBuilder);
        QueryResult<T> queryResult = new QueryResult<T>();
        while (true) {
            for (SearchHit hit : response.getHits().getHits()) {
                queryResult.addObj(ElasticBeanUtils.copyByJson(hit, clazz));
            }
            response = ClientInstance.getClient().prepareSearchScroll(response.getScrollId()).setScroll(tv).execute().actionGet();
            if (response.getHits().getHits().length == 0) {
                break;
            }
        }
        queryResult.setCount(queryResult.size());
        return queryResult;
    }

    /**
     * return with json string
     * @param condition
     * @return
     */
    public QueryResult search(SearchCondition condition) {
        SearchResponse response = searchByQuery(condition);
        return getJsonResult(response);
    }

    /**
     * build the request
     * @param condition
     * @return
     */
    private SearchRequestBuilder getRequestBuilder(SearchCondition condition) {
        SearchRequestBuilder searchRequestBuilder = ClientInstance.getClient()
                .prepareSearch(indexes)
                .setTypes(types)
                .setQuery(condition.buildQuery())
                .setPostFilter(condition.buildFilter())
                .setFrom(condition.getFrom())
                .setSize(condition.getSize());
        SimpleSortBuilder sortBuilder = condition.getSimpleSortBuilder();
        if (sortBuilder != null) {
            for (ScriptSortBuilder scriptSortBuilder : sortBuilder.scriptSortBuilders())
                searchRequestBuilder.addSort(scriptSortBuilder);
            for (FieldSortBuilder sortBuilder1 : sortBuilder.sortBuilders())
                searchRequestBuilder.addSort(sortBuilder1);
        }


        return searchRequestBuilder;
    }

    /**
     * search the request
     * @param condition
     * @return
     */
    private SearchResponse searchByQuery(SearchCondition condition) {
        SearchRequestBuilder searchRequestBuilder = getRequestBuilder(condition);
        SearchResponse response = searchRequestBuilder
                .execute()
                .actionGet();
        return response;
    }

    /**
     *
     * @param searchRequestBuilder
     * @return
     */
    private SearchResponse searchByQuery(SearchRequestBuilder searchRequestBuilder) {
        SearchResponse response = searchRequestBuilder
                .execute()
                .actionGet();
        return response;
    }

    /**
     * search by json string
     * not prepared
     * @param condition
     * @param clazz
     * @param <T>
     * @return
     */
    @Deprecated
    public <T extends BaseResult> QueryResult searchByJson(SearchJsonCondition condition, Class<T> clazz) {
        SearchResponse response = ClientInstance.getClient()
                .prepareSearch(indexes)
                .setTypes(types)
                .setQuery(condition.getJsonString())
                .setFrom(condition.getFrom())
                .setSize(condition.getSize())
                .execute()
                .actionGet();
        return getResult(response, clazz);
    }

    /**
     * wrap object
     * @param response
     * @param clazz
     * @param <T>
     * @return
     */
    private <T extends BaseResult> QueryResult<T> getResult(SearchResponse response, Class<T> clazz) {
        QueryResult<T> queryResult = new QueryResult<T>();
        queryResult.setCount(response.getHits().getTotalHits());
        queryResult.addList(ElasticBeanUtils.copyByJson(response.getHits(), clazz));
        return queryResult;
    }

    /**
     * wrap json
     * @param response
     * @return
     */
    private QueryResult getJsonResult(SearchResponse response) {
        QueryResult<String> queryResult = new QueryResult<String>();
        queryResult.setCount(response.getHits().getTotalHits());
        queryResult.addList(ElasticBeanUtils.copyJson(response.getHits()));
        return queryResult;
    }
}
