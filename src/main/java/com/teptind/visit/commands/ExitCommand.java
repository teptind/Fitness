package com.teptind.visit.commands;

import com.teptind.common.command.Command;
import com.teptind.common.command.CommandDao;

public class ExitCommand implements Command {
    Long uid;
    Long timestamp;

    public ExitCommand(Long uid, Long timestamp) {
        this.uid = uid;
        this.timestamp = timestamp;
    }


    @Override
    public String process(CommandDao commandDao) throws Exception {
        return ((CommandDaoImpl)commandDao).exit(uid, timestamp);
    }
}
