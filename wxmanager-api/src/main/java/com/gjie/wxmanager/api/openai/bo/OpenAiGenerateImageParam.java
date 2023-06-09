package com.gjie.wxmanager.api.openai.bo;

public class OpenAiGenerateImageParam {
    //图片描述
    private String prompt;
    //图片个数
    private Integer n=1;
    //图片大小
    private String size="1024x1024";

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
