package com.gjie.wxmanager.common.service;

import com.gjie.wxmanager.api.openai.OpenApiHttp;
import com.gjie.wxmanager.api.openai.bo.OpenAIReplayMessage;
import com.gjie.wxmanager.api.openai.bo.OpenAIReplayParam;
import com.gjie.wxmanager.api.openai.bo.OpenAiGenerateImageParam;
import com.gjie.wxmanager.api.openai.request.OpenAIGenerateImageRequest;
import com.gjie.wxmanager.api.openai.request.OpenAIReplayRequest;
import com.gjie.wxmanager.api.openai.response.OpenAiGenerateImageResponse;
import com.gjie.wxmanager.api.openai.response.OpenAiReplayResponse;
import com.gjie.wxmanager.common.constants.enums.ResponseEnum;
import com.gjie.wxmanager.common.request.BaseRequest;
import com.gjie.wxmanager.common.response.BaseResponse;
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
        //授权
//        OpenAIAuthRequest aiAuthRequest = new OpenAIAuthRequest();
//        OpenAiAuthResponse openAiAuthResult = openApiHttp.getOpenAiAuthResult(aiAuthRequest);
        return BaseResponse.<Boolean>builder()
                .code(ResponseEnum.SUCCESS.getCode())
                .success(true)
                .data(true)
                .build();

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
        BaseResponse<String> response = new BaseResponse<String>();
        response.setCode(openAiReplayResponse.getCode());
        response.setSuccess(openAiReplayResponse.getSuccess());
        response.setMsg(openAiReplayResponse.getMessage());
        response.setData(openAiReplayResponse.getData());
        return response;
    }

    public BaseResponse<List<String>> generateImg(BaseRequest<String> request) {
        OpenAIGenerateImageRequest aiGenerateImageRequest = new OpenAIGenerateImageRequest();
        OpenAiGenerateImageParam param = new OpenAiGenerateImageParam();
        param.setPrompt(request.getRequestData());
        aiGenerateImageRequest.setParamData(param);
        OpenAiGenerateImageResponse openAiGenerateImageResponse = openApiHttp.generateImage(aiGenerateImageRequest);
        BaseResponse<List<String>> response = new BaseResponse<>();
        response.setCode(openAiGenerateImageResponse.getCode());
        response.setSuccess(openAiGenerateImageResponse.getSuccess());
        response.setMsg(openAiGenerateImageResponse.getMessage());
        response.setData(openAiGenerateImageResponse.getData());
        return response;
    }
}
