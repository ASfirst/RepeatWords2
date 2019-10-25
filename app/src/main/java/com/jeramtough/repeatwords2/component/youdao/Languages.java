package com.jeramtough.repeatwords2.component.youdao;

public enum Languages {
    AUTO("auto"), ENGLISH("EN"), CHINESE("zh-CHS");

    private String name;

    Languages(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
