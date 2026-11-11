package com.jzs.messageboard.service;

import com.jzs.messageboard.entity.Message;
import com.jzs.messageboard.mapper.MessageMapper;
import com.jzs.messageboard.vo.MessageVO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MessageService {

    private final MessageMapper messageMapper;

    public MessageService(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    // 新增留言
    public void insert(Message message) {
        messageMapper.insert(message);
    }

    // 查询某个用户的所有留言（带用户名）
    public List<MessageVO> listByUserId(Long userId) {
        return messageMapper.selectMessageByUserId(userId);
    }

    // 查询所有留言（带用户名，广场用）
    public List<MessageVO> listAllWithUsername() {
        return messageMapper.selectAllMessageWithUsername();
    }

    // 根据ID查询留言
    public Message selectById(Long id) {
        return messageMapper.selectById(id);
    }

    // 更新留言
    public void update(Message message) {
        messageMapper.updateById(message);
    }

    // 根据ID删除留言（逻辑删除）
    public void deleteById(Long id) {
        messageMapper.deleteById(id);
    }
}
