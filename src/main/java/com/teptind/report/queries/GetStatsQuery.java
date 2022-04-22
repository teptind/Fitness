package com.teptind.report.queries;

import java.time.LocalDate;

import com.teptind.common.query.Query;
import com.teptind.common.query.QueryDao;

public class GetStatsQuery implements Query {
    LocalDate from;
    LocalDate to;

    public GetStatsQuery(LocalDate from, LocalDate to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public String process(QueryDao queryDao) throws Exception {
        return ((QueryDaoImpl)queryDao).getReport(from, to);
    }
}
