package com.jzs.messageboard.service;

import com.jzs.messageboard.entity.Reply;
import com.jzs.messageboard.entity.SysUser;
import com.jzs.messageboard.mapper.ReplyMapper;
import com.jzs.messageboard.vo.ReplyVO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReplyService {

    private final ReplyMapper replyMapper;

    public ReplyService(ReplyMapper replyMapper) {
        this.replyMapper = replyMapper;
    }

    // 根据留言ID查询回复列表
    public List<ReplyVO> listByMessageId(Long messageId) {
        return replyMapper.listByMessageId(messageId);
    }

    // 新增回复
    public void add(Reply reply) {
        replyMapper.insert(reply);
    }

    // 根据ID查询回复
    public Reply selectById(Long id) {
        return replyMapper.selectById(id);
    }

    // 删除回复（权限校验：本人或管理员可删）
    public boolean deleteById(Long id, SysUser currentUser) {
        Reply reply = replyMapper.selectById(id);
        if (reply == null) {
            return false;
        }
        // 管理员 或 回复本人 允许删除
        if (currentUser.getRole() == 1 || reply.getUserId().equals(currentUser.getId())) {
            replyMapper.deleteById(id);
            return true;
        }
        return false;
    }
}
