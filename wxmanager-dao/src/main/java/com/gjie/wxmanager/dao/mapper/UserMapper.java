package com.gjie.wxmanager.dao.mapper;

import com.gjie.wxmanager.dao.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author gongjie
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-06-08 16:39:09
* @Entity com.gjie.wxmanager.dao.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




