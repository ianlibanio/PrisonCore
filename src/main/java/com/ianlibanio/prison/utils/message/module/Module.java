package com.ianlibanio.prison.utils.message.module;

public enum Module {

    RANKUP("rankup");

    private final String name;

    Module(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
