package com.gjie.wxmanager.common.service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gjie.wxmanager.api.openai.OpenApiHttp;
import com.gjie.wxmanager.api.openai.bo.OpenAIReplayMessage;
import com.gjie.wxmanager.api.openai.bo.OpenAIReplayParam;
import com.gjie.wxmanager.api.openai.bo.OpenAiGenerateImageParam;
import com.gjie.wxmanager.api.openai.request.OpenAIGenerateImageRequest;
import com.gjie.wxmanager.api.openai.request.OpenAIReplayRequest;
import com.gjie.wxmanager.api.openai.response.OpenAiGenerateImageResponse;
import com.gjie.wxmanager.api.openai.response.OpenAiReplayResponse;
import com.gjie.wxmanager.common.bo.ChatAiInfo;
import com.gjie.wxmanager.common.constants.enums.OperateEnum;
import com.gjie.wxmanager.common.constants.enums.ResponseEnum;
import com.gjie.wxmanager.common.request.BaseRequest;
import com.gjie.wxmanager.common.response.BaseResponse;
import com.gjie.wxmanager.dao.domain.OperateLog;
import com.gjie.wxmanager.dao.domain.User;
import com.gjie.wxmanager.dao.service.OperateLogService;
import com.gjie.wxmanager.dao.service.impl.OperateLogServiceImpl;
import com.gjie.wxmanager.dao.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private OperateLogServiceImpl operateLogService;

    @Autowired
    private UserServiceImpl userService;

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
        //暂时不进行记录
        String requestData = request.getRequestData();
        String responseFromDb = getResponseFromDb(requestData);
        if (!StringUtils.isEmpty(responseFromDb)) {
            return BaseResponse.<String>builder()
                    .data(responseFromDb)
                    .success(true)
                    .code(ResponseEnum.SUCCESS.getCode())
                    .build();
        }
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
        //保存操作记录
        OperateLog operateLog = OperateLog.builder()
                .pathName("/openai/getReplay")
                .params(request.getRequestData())
                .path(OperateEnum.CHAT.getName())
                .response(JSON.toJSONString(openAiReplayResponse))
                .userId(Long.valueOf(request.getToken()))
                .build();
        operateLogService.save(operateLog);
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
        OperateLog operateLog = OperateLog.builder()
                .pathName("/openai/getReplay")
                .params(request.getRequestData())
                .path(OperateEnum.DRAW.getName())
                .response(JSON.toJSONString(openAiGenerateImageResponse))
                .userId(Long.valueOf(request.getToken()))
                .build();
        operateLogService.save(operateLog);
        BaseResponse<List<String>> response = new BaseResponse<>();
        response.setCode(openAiGenerateImageResponse.getCode());
        response.setSuccess(openAiGenerateImageResponse.getSuccess());
        response.setMsg(openAiGenerateImageResponse.getMessage());
        response.setData(openAiGenerateImageResponse.getData());
        return response;
    }

    /**
     * 获取数据库中的回答
     *
     * @param question
     * @return
     */
    private String getResponseFromDb(String question) {
        QueryWrapper<OperateLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("path", OperateEnum.CHAT.getName()).eq("params", question).orderByDesc("updated_time");
        List<OperateLog> operateLogs = operateLogService.list(queryWrapper);
        if (CollectionUtils.isEmpty(operateLogs)) {
            return null;
        }
        //找到不是500的
        for (OperateLog operateLog : operateLogs) {
            BaseResponse<String> response = JSON.parseObject(operateLog.getResponse(), new TypeReference<BaseResponse<String>>() {
            });
            if (ResponseEnum.SUCCESS.getCode().equals(response.getCode())) {
                return response.getData();
            }
        }
        return null;
    }

    public BaseResponse<List<ChatAiInfo>> listLatestChatAiResponses(Integer size) {
        List<OperateLog> operateLogs = operateLogService.listLatestChatAiResponses(size);
        //查询人
        if (CollectionUtils.isEmpty(operateLogs)) {
            return BaseResponse.<List<ChatAiInfo>>builder()
                    .data(null)
                    .success(true)
                    .code(ResponseEnum.SUCCESS.getCode())
                    .build();
        }
        List<Long> userIds = operateLogs.stream().map(OperateLog::getUserId).collect(Collectors.toList());
        Collection<User> users = userService.listByIds(userIds);
        Map<Long, User> userData = users.stream().collect(Collectors.toMap(User::getId, a -> a));

        List<ChatAiInfo> chatAiInfos = new ArrayList<>();
        operateLogs.forEach(o -> {
            User user = userData.get(o.getUserId());
            ChatAiInfo chatAiInfo = new ChatAiInfo();
            chatAiInfo.setQuestion(o.getParams());
//            chatAiInfo.setResponse(o.getResponse());
            chatAiInfo.setUsername(user.getUsername() == null ? user.getMobile() : user.getUsername());
            chatAiInfo.setUpdatedTime(o.getUpdatedTime());
            chatAiInfos.add(chatAiInfo);
        });
        return BaseResponse.<List<ChatAiInfo>>builder()
                .data(chatAiInfos)
                .success(true)
                .code(ResponseEnum.SUCCESS.getCode())
                .build();
    }
}
