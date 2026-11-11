package com.jzs.messageboard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jzs.messageboard.entity.Reply;
import com.jzs.messageboard.vo.ReplyVO;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface ReplyMapper extends BaseMapper<Reply> {
    // 根据留言ID查询所有回复（联表带用户名）
    List<ReplyVO> listByMessageId(@Param("messageId") Long messageId);
    List<ReplyVO> listByMessageId(@Param("messageId") Long messageId,
                                  @Param("currentUserId") Long currentUserId,
                                  @Param("currentUsername") String currentUsername,
                                  @Param("isAdmin") boolean isAdmin);
}
