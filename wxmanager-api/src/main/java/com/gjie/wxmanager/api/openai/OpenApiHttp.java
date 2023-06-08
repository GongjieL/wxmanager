package com.gjie.wxmanager.api.openai;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gjie.wxmanager.api.openai.request.OpenAIGenerateImageRequest;
import com.gjie.wxmanager.api.openai.request.OpenAIReplayRequest;
import com.gjie.wxmanager.api.openai.response.OpenAiGenerateImageResponse;
import com.gjie.wxmanager.api.openai.response.OpenAiReplayResponse;
import com.gjie.wxmanager.util.config.OpenApiEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

@Service
public class OpenApiHttp {
    @Autowired
    private RestTemplate restTemplate;

//    @Value("${OpenAI-Authorization}")
//    private String openAiAuthToken;

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


    public OpenAiGenerateImageResponse generateImage(OpenAIGenerateImageRequest request) {
        OpenAiGenerateImageResponse response = new OpenAiGenerateImageResponse();
        try {
            request.getHeaders().add("Authorization", "Bearer "+getOpenAiSecretKey());
            ResponseEntity openAiResponse = getOpenAiResponse(request);
            JSONObject apiResponse = JSON.parseObject(openAiResponse.getBody().toString());
            JSONArray data = apiResponse.getJSONArray("data");
            response.setData(new ArrayList<>());
            for (Object datum : data) {
                response.getData().add(((JSONObject) datum).getString("url"));
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setSuccess(false);
            response.setCode("500");
            response.setMessage("噢！我好像坏掉了!!");
            return response;
        }
    }

    public OpenAiReplayResponse getOpenAiReplayResult(OpenAIReplayRequest request) {
        OpenAiReplayResponse response = new OpenAiReplayResponse();
        try {
            request.getHeaders().add("Authorization", "Bearer "+getOpenAiSecretKey());
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


    private String getOpenAiSecretKey() throws IOException {
        String projectPath = System.getProperty("user.dir");
        InputStream inputStream = new FileInputStream(projectPath+ File.separator + "openai.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        String openaiKey = properties.getProperty("openaiKey");
        inputStream.close();
        return openaiKey;
    }


}
