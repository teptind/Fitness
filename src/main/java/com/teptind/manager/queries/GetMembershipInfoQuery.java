package com.teptind.manager.queries;

import com.teptind.common.query.Query;
import com.teptind.common.query.QueryDao;

public class GetMembershipInfoQuery implements Query {
    private final Long uid;

    public GetMembershipInfoQuery(Long uid) {
        this.uid = uid;
    }

    @Override
    public String process(QueryDao queryDao) throws Exception {
        return ((QueryDaoImpl)queryDao).getSubscriptionInfo(uid);
    }
}
