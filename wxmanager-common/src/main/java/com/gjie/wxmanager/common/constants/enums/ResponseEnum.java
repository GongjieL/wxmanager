package com.gjie.wxmanager.common.constants.enums;

public enum ResponseEnum {
    SUCCESS("200","成功"),
    ERROR("500","未知异常"),

    ;

    private String code;
    private String desc;


    ResponseEnum(String code, String desc) {
        this.code = code;
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
}
