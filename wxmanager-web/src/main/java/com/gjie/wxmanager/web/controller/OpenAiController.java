package com.gjie.wxmanager.web.controller;

import com.gjie.wxmanager.common.bo.ChatAiInfo;
import com.gjie.wxmanager.common.request.BaseRequest;
import com.gjie.wxmanager.common.response.BaseResponse;
import com.gjie.wxmanager.common.service.OpenAiService;
import com.gjie.wxmanager.dao.domain.OperateLog;
import com.gjie.wxmanager.dao.mapper.OperateLogMapper;
import com.gjie.wxmanager.dao.service.OperateLogService;
import com.gjie.wxmanager.dao.service.impl.OperateLogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/openai")
public class OpenAiController {

    @Autowired
    private OpenAiService openAiService;

    @Autowired
    private OperateLogServiceImpl operateLogService;

    @Autowired
    private OperateLogMapper operateLogMapper;


    @RequestMapping("/auth")
    public BaseResponse<Boolean> auth() {
        OperateLog byId = operateLogService.getById(1);

        return openAiService.auth();
    }

    @PostMapping("/getReplay")
    public BaseResponse<String> getReplay(@RequestBody BaseRequest<String> request) {
        return openAiService.getReplay(request);
    }


    @PostMapping("/generateImg")
    public BaseResponse<List<String>> generateImg(@RequestBody BaseRequest<String> request) {
        return openAiService.generateImg(request);
    }


    @GetMapping("/listLatestChatAiResponse")
    public BaseResponse<List<ChatAiInfo>> listLatestChatAiResponses(@RequestParam Integer size) {
        return openAiService.listLatestChatAiResponses(size);
    }


}
