package com.jzs.messageboard.service;

import com.jzs.messageboard.entity.Message;
import com.jzs.messageboard.mapper.MessageMapper;
import com.jzs.messageboard.vo.MessageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageMapper messageMapper;
    private final ReplyService replyService;

    public void insert(Message message) {
        messageMapper.insert(message);
    }

    public List<MessageVO> listByUserId(Long userId) {
        return messageMapper.listByUserId(userId);
    }

    public List<MessageVO> listAllWithUsername(String keyword, Long currentUserId, String currentUsername, boolean isAdmin) {
        List<MessageVO> list = messageMapper.listAllWithUsername(keyword, currentUserId, currentUsername, isAdmin);
        for (MessageVO vo : list) {
            vo.setReplyList(replyService.listByMessageId(vo.getId(), currentUserId, currentUsername, isAdmin));
        }
        return list;
    }

    public Message selectById(Long id) {
        return messageMapper.selectById(id);
    }

    public void update(Message message) {
        messageMapper.updateById(message);
    }

    public void deleteById(Long id) {
        messageMapper.deleteById(id);
    }
}
