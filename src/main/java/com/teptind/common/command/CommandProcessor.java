package com.teptind.common.command;

public class CommandProcessor {
    private final CommandDao commandDao;

    public CommandProcessor(CommandDao commandDao) {
        this.commandDao = commandDao;
    }

    public String process(Command command) {
        try {
            return command.process(commandDao);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
