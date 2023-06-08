package com.gjie.wxmanager.dao.mapper;

import com.gjie.wxmanager.dao.domain.OperateLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author gongjie
 * @description 针对表【operate_log】的数据库操作Mapper
 * @createDate 2023-06-08 16:39:19
 * @Entity com.gjie.wxmanager.dao.domain.OperateLog
 */
@Mapper
public interface OperateLogMapper extends BaseMapper<OperateLog> {
    @Select("select max(updated_time)as updated_time,params,user_id,response from operate_log where path='CHAT' group by params order by updated_time desc limit ${size}")
    public List<OperateLog> listLatestChatAiResponses(@Param("size") Integer size);
}




