package com.jzs.messageboard.vo;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MessageVO {
    private Long id;
    private Long userId;
    private String username;
    private String content;
    private Integer isTop;
    private LocalDateTime createTime;
    private String topic;
    private List<ReplyVO> replyList;
    private Integer visibility;
    private String visibleUsernames;
}
