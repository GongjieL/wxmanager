package com.gjie.wxmanager.api.openai.request;

import com.gjie.wxmanager.api.ApiBaseRequest;
import com.gjie.wxmanager.api.openai.bo.OpenAIReplayParam;
import org.springframework.http.HttpHeaders;

public class OpenAIReplayRequest extends ApiBaseRequest<OpenAIReplayParam> {





    {
        headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        setRequestCode(002);
    }


}
