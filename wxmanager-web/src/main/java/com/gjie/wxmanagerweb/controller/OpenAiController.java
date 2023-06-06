package com.gjie.wxmanagerweb.controller;

import com.gjie.wxmanagercommon.request.BaseRequest;
import com.gjie.wxmanagercommon.response.BaseResponse;
import com.gjie.wxmanagercommon.service.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/openai")
public class OpenAiController {

    @Autowired
    private OpenAiService openAiService;


    @RequestMapping("/auth")
    public BaseResponse<Boolean> auth() {
        return openAiService.auth();
    }

    @PostMapping("/getReplay")
    public BaseResponse<String> getReplay(@RequestBody BaseRequest<String> request) {
        return openAiService.getReplay(request);
    }
}
