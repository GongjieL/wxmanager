package com.gjie.wxmanager.common.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gjie.wxmanager.common.bo.UserInfo;
import com.gjie.wxmanager.common.constants.enums.OperateEnum;
import com.gjie.wxmanager.dao.domain.OperateLog;
import com.gjie.wxmanager.dao.domain.User;
import com.gjie.wxmanager.dao.mapper.UserMapper;
import com.gjie.wxmanager.dao.service.impl.OperateLogServiceImpl;
import com.gjie.wxmanager.dao.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Date;

@Service
public class CommonUserService {
    @Autowired
    private OperateLogServiceImpl operateLogService;

    @Autowired
    private TransactionTemplate transactionTemplate;


    @Autowired
    private UserServiceImpl userService;

    /**
     * 登录
     *
     * @param requestData
     * @return
     */
    public UserInfo login(UserInfo requestData) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper
                .eq("username", requestData.getUserName())
                .or()
                .eq("mobile", requestData.getMobile())
                .orderByDesc("updated_time")
        ;
        //根据手机号查询是否已经存在
        User user = userService.getOne(userQueryWrapper);
        UserInfo userInfo = new UserInfo();
        //操作记录
        OperateLog operateLog = OperateLog.builder()
                .pathName("/user/operate/login")
                .path(OperateEnum.LOGIN.getName())
                .params(JSON.toJSONString(requestData))
                .build();
        if (user == null) {
            user = new User();
            user.setChannel(requestData.getSource());
            user.setUsername(requestData.getUserName());
            user.setMobile(requestData.getMobile());
            user.setCreatedTime(new Date());
            userService.save(user);
            operateLog.setPath(OperateEnum.SIGN_IN.getName());
        }
        userInfo.setId(user.getId());
        userInfo.setUserName(user.getUsername());
        userInfo.setMobile(user.getMobile());
        //记录操作记录
        operateLog.setUserId(userInfo.getId());
        operateLog.setResponse(JSON.toJSONString(userInfo));
        operateLogService.save(operateLog);
        return userInfo;
    }
}
