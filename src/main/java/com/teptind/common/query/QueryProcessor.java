package com.teptind.common.query;

public class QueryProcessor {

    private final QueryDao queryDao;

    public QueryProcessor(QueryDao queryDao) {
        this.queryDao = queryDao;
    }

    public String process(Query query) {
        try {
            return query.process(queryDao);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
