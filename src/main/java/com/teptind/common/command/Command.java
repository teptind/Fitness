package com.teptind.common.command;

public interface Command {
    String process(CommandDao commandDao) throws Exception;
}
