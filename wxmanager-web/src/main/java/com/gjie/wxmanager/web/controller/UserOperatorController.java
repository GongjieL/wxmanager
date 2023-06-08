package com.gjie.wxmanager.web.controller;

import com.gjie.wxmanager.common.bo.UserInfo;
import com.gjie.wxmanager.common.constants.enums.ErrorEnum;
import com.gjie.wxmanager.common.constants.enums.RequestSourceEnum;
import com.gjie.wxmanager.common.constants.enums.ResponseEnum;
import com.gjie.wxmanager.common.exception.BaseException;
import com.gjie.wxmanager.common.request.BaseRequest;
import com.gjie.wxmanager.common.response.BaseResponse;
import com.gjie.wxmanager.common.service.CommonUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user/operate")
public class UserOperatorController {
    @Autowired
    private CommonUserService commonUserService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @PostMapping("/login")
    public BaseResponse<UserInfo> login(@RequestBody BaseRequest<UserInfo> request) {
        //校验
        UserInfo userInfo = request.getRequestData();
        //不能同时为空
        if (StringUtils.isNoneBlank(userInfo.getUserName(), userInfo.getMobile())) {
            throw new BaseException(ErrorEnum.PARAM_ERROR);
        }
        if (StringUtils.isBlank(userInfo.getSource())) {
            userInfo.setSource(RequestSourceEnum.DEFAULT.getName());
        }
        if (RequestSourceEnum.getRequestSourceWithName(userInfo.getSource()) == null) {
            throw new BaseException(ErrorEnum.PARAM_ERROR);
        }

        return transactionTemplate.execute((status)->{
            UserInfo queryUserInfo = commonUserService.login(request.getRequestData());
            if (queryUserInfo.getId() != null) {
                return BaseResponse.<UserInfo>builder()
                        .code(ResponseEnum.SUCCESS.getCode())
                        .success(true)
                        .data(queryUserInfo)
                        .build();
            }
            return BaseResponse.<UserInfo>builder()
                    .code(ResponseEnum.ERROR.getCode())
                    .msg(ResponseEnum.ERROR.getDesc())
                    .success(false)
                    .data(null)
                    .build();
        });

    }
}
