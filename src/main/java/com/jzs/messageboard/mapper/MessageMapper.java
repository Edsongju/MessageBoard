package com.jzs.messageboard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jzs.messageboard.entity.Message;
import com.jzs.messageboard.vo.MessageVO;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface MessageMapper extends BaseMapper<Message> {
    List<MessageVO> listAllWithUsername(@Param("keyword") String keyword,
                                        @Param("currentUserId") Long currentUserId,
                                        @Param("currentUsername") String currentUsername,
                                        @Param("isAdmin") boolean isAdmin);
    // 广场/后台通用：联表查询所有留言，支持关键词搜索
    List<MessageVO> listAllWithUsername(@Param("keyword") String keyword);

    // 保留你原有的按用户ID查询方法
    List<MessageVO> listByUserId(Long userId);
}
