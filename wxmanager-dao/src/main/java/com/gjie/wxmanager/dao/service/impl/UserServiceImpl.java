package com.gjie.wxmanager.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gjie.wxmanager.dao.domain.User;
import com.gjie.wxmanager.dao.service.UserService;
import com.gjie.wxmanager.dao.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author gongjie
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-06-08 16:39:09
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




