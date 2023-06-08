package com.gjie.wxmanager.common.constants.enums;

public enum OperateEnum {
    LOGIN("LOGIN","登录"),
    SIGN_IN("SIGN_IN","注册"),
    CHAT("CHAT","对话"),
    DRAW("DRAW","绘画"),
    ;
    private String name;
    private String desc;

    OperateEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
