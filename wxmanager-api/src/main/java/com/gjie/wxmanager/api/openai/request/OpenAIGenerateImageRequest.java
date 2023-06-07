package com.gjie.wxmanager.api.openai.request;

import com.gjie.wxmanager.api.openai.ApiBaseRequest;
import com.gjie.wxmanager.api.openai.bo.OpenAiGenerateImageParam;
import org.springframework.http.HttpHeaders;

public class OpenAIGenerateImageRequest extends ApiBaseRequest<OpenAiGenerateImageParam> {


    {
        headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        setRequestCode(002);
    }


}
