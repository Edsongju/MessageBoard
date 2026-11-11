package com.jzs.messageboard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jzs.messageboard.entity.Message;
import com.jzs.messageboard.vo.MessageVO;
import java.util.List;

public interface MessageMapper extends BaseMapper<Message> {
    /**
     * 查询所有留言，联表获取用户名
     */
    List<MessageVO> selectAllMessageWithUsername();

    /**
     * 根据用户ID查询留言，联表获取用户名
     */
    List<MessageVO> selectMessageByUserId(Long userId);
}
