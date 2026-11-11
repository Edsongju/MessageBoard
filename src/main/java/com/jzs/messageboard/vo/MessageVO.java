package com.jzs.messageboard.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MessageVO {
    private Long id;
    private Long userId;
    private String username;
    private String content;
    private Integer isTop; // 新增：是否置顶
    private LocalDateTime createTime;
}
