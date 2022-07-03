package com.se1dhe.redqueen.bot.model.entity;

public enum Command {

    READ_ONLY("/ro"),
    UN_READ_ONLY("/unro"),
    INFO("/info"),
    UP_REPUTATION("\uD83D\uDC4D"),
    DAWN_REPUTATION("\uD83D\uDC4E\uD83C\uDFFF"),
    DELETE_MSG("/del"),
    NO("nocommand"),
    ADD_GROUP_SUB("/addgroupsub"),
    DELETE_GROUP_SUB("/deletegroupsub"),
    LIST_GROUP_SUB("/listgroupsub");

    private final String command;

    Command(String command) {
        this.command = command;
    }

    public String getCommandName() {
        return command;
    }
}