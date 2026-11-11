package com.jzs.messageboard.controller;

import com.jzs.messageboard.entity.Reply;
import com.jzs.messageboard.entity.SysUser;
import com.jzs.messageboard.service.ReplyService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reply")
public class ReplyController {

    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    // 发布回复
    @PostMapping("/add")
    public String addReply(Long messageId, String content, HttpSession session) {
        SysUser currentUser = (SysUser) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        Reply reply = new Reply();
        reply.setMessageId(messageId);
        reply.setUserId(currentUser.getId());
        reply.setContent(content);
        replyService.add(reply);
        return "redirect:/";
    }

    // 删除回复
    @GetMapping("/delete/{id}")
    public String deleteReply(@PathVariable Long id, HttpSession session) {
        SysUser currentUser = (SysUser) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        replyService.deleteById(id, currentUser);
        return "redirect:/";
    }
}
