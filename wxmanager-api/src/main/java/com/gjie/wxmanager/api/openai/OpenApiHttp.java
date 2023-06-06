package com.gjie.wxmanager.api.openai;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gjie.wxmanager.api.openai.request.OpenAIAuthRequest;
import com.gjie.wxmanager.api.openai.request.OpenAIReplayRequest;
import com.gjie.wxmanager.api.openai.response.OpenAiAuthResponse;
import com.gjie.wxmanager.api.openai.response.OpenAiReplayResponse;
import com.gjie.wxmanagerutil.config.OpenApiEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenApiHttp {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${OpenAI-Authorization}")
    private String openAiAuthToken;

    @Value("${OpenAI-Organization}")
    private String openAiOrganization;


    private ResponseEntity getOpenAiResponse(ApiBaseRequest request) {
        OpenApiEnum openApiEnum = OpenApiEnum.getOpenApiEnumByCode(request.getRequestCode());
        HttpMethod httpMethod = HttpMethod.resolve(openApiEnum.getMethod().toUpperCase());
        String url = openApiEnum.getUrl();
        String param = buildOpenaiParam(request);
        String body = null;
        if (HttpMethod.GET.equals(httpMethod)) {
            if (param != null) {
                url = url + param;
            }
        } else {
            body = param;
        }
        HttpEntity<String> httpEntity = new HttpEntity(body, request.getHeaders());
        ResponseEntity<String> response =
                restTemplate.exchange(url, HttpMethod.resolve(openApiEnum.getMethod().toUpperCase()), httpEntity, String.class);
        return response;
    }


    public OpenAiAuthResponse getOpenAiAuthResult(OpenAIAuthRequest request) {
        request.getHeaders().add("OpenAI-Organization", openAiOrganization);
        request.getHeaders().add("Authorization", openAiAuthToken);
        OpenAiAuthResponse response = new OpenAiAuthResponse();
        try {
            ResponseEntity openAiResponse = getOpenAiResponse(request);
            boolean success = !openAiResponse.getBody().toString().contains("error");
            if (success) {
                response.setData(true);
            } else {
                response.setSuccess(false);
                response.setCode("500");
                response.setMessage("噢！我好像坏掉了!!");
            }
            return response;
        } catch (Exception e) {
            response.setSuccess(false);
            response.setCode("500");
            response.setMessage("噢！我好像坏掉了!!");
            return response;
        }
    }

    public OpenAiReplayResponse getOpenAiReplayResult(OpenAIReplayRequest request) {
        request.getHeaders().add("Authorization", openAiAuthToken);
        OpenAiReplayResponse response = new OpenAiReplayResponse();
        try {
            ResponseEntity openAiResponse = getOpenAiResponse(request);
            JSONObject message = (JSONObject) (JSON.parseObject(openAiResponse.getBody().toString()).getJSONArray("choices").get(0));
            response.setData(message.getJSONObject("message").getString("content"));
            return response;
        } catch (Exception e) {
            response.setSuccess(false);
            response.setCode("500");
            response.setMessage("噢！我好像坏掉了!!");
            return response;
        }
    }

    private String buildOpenaiParam(ApiBaseRequest request) {
        Object paramData = request.getParamData();
        if (paramData == null) {
            return null;
        }
        return JSON.toJSONString(paramData);
    }

}
