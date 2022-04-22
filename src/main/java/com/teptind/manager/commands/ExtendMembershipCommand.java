package com.teptind.manager.commands;

import java.sql.Date;

import com.teptind.common.command.Command;
import com.teptind.common.command.CommandDao;

public class ExtendMembershipCommand implements Command {
    private final Long uid;
    private final Date expiryDate;

    public ExtendMembershipCommand(Long uid, Date expiryDate) {
        this.uid = uid;
        this.expiryDate = expiryDate;
    }

    @Override
    public String process(CommandDao commandDao) throws Exception {
        return ((CommandDaoImpl)commandDao).extendSubscription(uid, expiryDate);
    }
}
