package com.gjie.wxmanager.common.constants.enums;

public enum RequestSourceEnum {
    DEFAULT("000", "DEFAULT", "无法查询来源"),

    WX("001", "WX", "微信"),
    ;
    private String code;

    private String name;
    private String desc;

    RequestSourceEnum(String code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static RequestSourceEnum getRequestSourceWithName(String sourceName) {
        for (RequestSourceEnum value : RequestSourceEnum.values()) {
            if(value.getName().equals(sourceName)){
                return value;
            }
        }
        return null;
    }
}
