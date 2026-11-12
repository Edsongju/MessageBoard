package com.jzs.messageboard.service;

import com.jzs.messageboard.entity.Reply;
import com.jzs.messageboard.entity.SysUser;
import com.jzs.messageboard.mapper.ReplyMapper;
import com.jzs.messageboard.vo.ReplyVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyMapper replyMapper;

    public List<ReplyVO> listByMessageId(Long messageId, Long currentUserId, String currentUsername, boolean isAdmin) {
        return replyMapper.listByMessageId(messageId, currentUserId, currentUsername, isAdmin);
    }

    public void add(Reply reply) {
        replyMapper.insert(reply);
    }

    public Reply selectById(Long id) {
        return replyMapper.selectById(id);
    }

    public boolean deleteById(Long id, SysUser currentUser) {
        Reply reply = replyMapper.selectById(id);
        if (reply == null) {
            return false;
        }
        if (currentUser.getRole() == 1 || reply.getUserId().equals(currentUser.getId())) {
            replyMapper.deleteById(id);
            return true;
        }
        return false;
    }
}
