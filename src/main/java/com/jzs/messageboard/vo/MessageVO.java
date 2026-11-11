package com.jzs.messageboard.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MessageVO {
    private Long id;
    private Long userId;
    private String username;
    private String content;
    private Integer isTop;
    private LocalDateTime createTime;
    private String topic;
}
