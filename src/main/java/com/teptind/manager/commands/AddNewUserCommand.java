package com.teptind.manager.commands;

import com.teptind.common.command.Command;
import com.teptind.common.command.CommandDao;

public class AddNewUserCommand implements Command {
    public String process(CommandDao commandDao) throws Exception {
        return ((CommandDaoImpl) commandDao).addNewUser();
    }
}
