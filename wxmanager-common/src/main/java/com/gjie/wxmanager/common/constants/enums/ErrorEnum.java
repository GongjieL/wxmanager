package com.gjie.wxmanager.common.constants.enums;

public enum ErrorEnum {
    UN_KNOWN_ERROR(0,"未知错误"),
    PARAM_ERROR(1,"参数错误"),

    ;

    private Integer code;
    private String desc;


    ErrorEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
