package com.gjie.wxmanagercommon.service;

import com.gjie.wxmanager.api.openai.OpenApiHttp;
import com.gjie.wxmanager.api.openai.bo.OpenAIReplayMessage;
import com.gjie.wxmanager.api.openai.bo.OpenAIReplayParam;
import com.gjie.wxmanager.api.openai.request.OpenAIAuthRequest;
import com.gjie.wxmanager.api.openai.request.OpenAIReplayRequest;
import com.gjie.wxmanager.api.openai.response.OpenAiAuthResponse;
import com.gjie.wxmanager.api.openai.response.OpenAiReplayResponse;
import com.gjie.wxmanagercommon.request.BaseRequest;
import com.gjie.wxmanagercommon.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OpenAiService {

    @Value("${open-ai-model}")
    private String openAiModel;

    @Value("${open-ai-temperature}")
    private Double openAiTemperature;



    @Value("${open-ai-replay-message-role}")
    private String openAiReplayMessageRole;


    @Autowired
    private OpenApiHttp openApiHttp;

    public BaseResponse<Boolean> auth() {
        OpenAIAuthRequest aiAuthRequest = new OpenAIAuthRequest();
        OpenAiAuthResponse openAiAuthResult = openApiHttp.getOpenAiAuthResult(aiAuthRequest);
        BaseResponse<Boolean> response = new BaseResponse<>();
        response.setCode(openAiAuthResult.getCode());
        response.setSuccess(openAiAuthResult.getSuccess());
        response.setMsg(openAiAuthResult.getMessage());
        response.setData(openAiAuthResult.getData());
        return response;
    }

    public BaseResponse<String> getReplay(BaseRequest<String> request) {
        OpenAIReplayParam paramData = new OpenAIReplayParam();
        paramData.setModel(openAiModel);
        paramData.setTemperature(openAiTemperature);
        List<OpenAIReplayMessage> openAIReplayMessages = new ArrayList<>();
        paramData.setMessages(openAIReplayMessages);
        OpenAIReplayMessage openAIReplayMessage = new OpenAIReplayMessage();
        openAIReplayMessage.setRole(openAiReplayMessageRole);
        openAIReplayMessage.setContent(request.getRequestData());
        openAIReplayMessages.add(openAIReplayMessage);
        OpenAIReplayRequest aiReplayRequest = new OpenAIReplayRequest();
        aiReplayRequest.setParamData(paramData);
        OpenAiReplayResponse openAiReplayResponse = openApiHttp.getOpenAiReplayResult(aiReplayRequest);
        BaseResponse<String> response = new BaseResponse<>();
        response.setCode(openAiReplayResponse.getCode());
        response.setSuccess(openAiReplayResponse.getSuccess());
        response.setMsg(openAiReplayResponse.getMessage());
        response.setData(openAiReplayResponse.getData());
        return response;
    }
}
