package com.jzs.messageboard.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReplyVO {
    private Long id;
    private Long messageId;
    private Long userId;
    private String username;
    private String content;
    private LocalDateTime createTime;
}
