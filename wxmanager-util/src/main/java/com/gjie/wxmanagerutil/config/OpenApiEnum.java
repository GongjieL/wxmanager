package com.gjie.wxmanagerutil.config;

public enum OpenApiEnum {
    OPEN_AI_GET_REPLAY(0001, "post", "https://api.openai.com/v1/chat/completions"),
    OPEN_AI_GENERATE_IMAGES(0002, "post", "https://api.openai.com/v1/images/generations"),
    ;


    OpenApiEnum(Integer code, String method, String url) {
        this.code = code;
        this.method = method;
        this.url = url;
    }

    private Integer code;
    private String method;
    private String url;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static OpenApiEnum getOpenApiEnumByCode(Integer code){
        for (OpenApiEnum value : OpenApiEnum.values()) {
            if(value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }
}
