package com.jzs.messageboard.service;

import com.jzs.messageboard.entity.Message;
import com.jzs.messageboard.mapper.MessageMapper;
import com.jzs.messageboard.vo.MessageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageMapper messageMapper;
    private final ReplyService replyService;

    @Cacheable(value = "message", key = "#id")
    public Message selectById(Long id) {
        return messageMapper.selectById(id);
    }

    @Cacheable(value = "messageList", key = "#keyword + '_' + #currentUserId + '_' + #currentUsername + '_' + #isAdmin")
    public List<MessageVO> listAllWithUsername(String keyword, Long currentUserId, String currentUsername, boolean isAdmin) {
        List<MessageVO> list = messageMapper.listAllWithUsername(keyword, currentUserId, currentUsername, isAdmin);
        for (MessageVO vo : list) {
            vo.setReplyList(replyService.listByMessageId(vo.getId(), currentUserId, currentUsername, isAdmin));
        }
        return list;
    }

    @Cacheable(value = "myMessage", key = "#userId")
    public List<MessageVO> listByUserId(Long userId) {
        return messageMapper.listByUserId(userId);
    }

    @CacheEvict(value = {"messageList", "myMessage"}, allEntries = true)
    public void insert(Message message) {
        messageMapper.insert(message);
    }

    /**
     * 修正：用 @Caching 组合多个缓存清除
     * 1. 清除所有列表缓存（allEntries = true）
     * 2. 清除当前id对应的单条留言缓存
     */
    @Caching(evict = {
            @CacheEvict(value = {"messageList", "myMessage"}, allEntries = true),
            @CacheEvict(value = "message", key = "#message.id")
    })
    public void update(Message message) {
        messageMapper.updateById(message);
    }

    @CacheEvict(value = {"message", "messageList", "myMessage", "replyList"}, allEntries = true)
    public void deleteById(Long id) {
        messageMapper.deleteById(id);
    }
}
