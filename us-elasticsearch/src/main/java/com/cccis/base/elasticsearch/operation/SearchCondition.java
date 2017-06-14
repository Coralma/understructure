package com.cccis.base.elasticsearch.operation;

import org.elasticsearch.index.query.QueryBuilder;

/**
 * 查询部分分为query和filter
 * filter是为了过滤掉无关的数据，比如数字的比较，日期的比较都可以作为filter
 * query是查询记录的相关度
 */
public class SearchCondition extends BaseCondition {

    private SimpleQueryBuilder queryBuilder;
    private SimpleQueryBuilder filterBuilder;
    private SimpleSortBuilder simpleSortBuilder;

    private SearchCondition() {

    }

    public static SearchCondition searchCondition() {
        return new SearchCondition();
    }

    public SimpleQueryBuilder getQueryBuilder() {
        return queryBuilder;
    }

    public QueryBuilder buildQuery() {
        if (queryBuilder != null)
            return queryBuilder.toQuery();
        return null;
    }

    public SearchCondition setQueryBuilder(SimpleQueryBuilder queryBuilder) {
        this.queryBuilder = queryBuilder;
        return this;
    }

    public SimpleQueryBuilder getFilterBuilder() {
        return filterBuilder;
    }

    public QueryBuilder buildFilter() {
        if (filterBuilder != null)
            return filterBuilder.toQuery();
        return null;
    }

    public SearchCondition setFilterBuilder(SimpleQueryBuilder filterBuilder) {
        this.filterBuilder = filterBuilder;
        return this;
    }

    public SimpleSortBuilder getSimpleSortBuilder() {
        return simpleSortBuilder;
    }

    public SearchCondition setSimpleSortBuilder(SimpleSortBuilder simpleSortBuilder) {
        this.simpleSortBuilder = simpleSortBuilder;
        return this;
    }

    @Override
    public SearchCondition setPageNo(int pageNo) {
        super.setPageNo(pageNo);
        return this;
    }

    @Override
    public SearchCondition setPageSize(int pageSize) {
        super.setPageSize(pageSize);
        return this;
    }
}
