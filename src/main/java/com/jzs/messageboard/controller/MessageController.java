package com.jzs.messageboard.controller;

import com.jzs.messageboard.entity.Message;
import com.jzs.messageboard.entity.SysUser;
import com.jzs.messageboard.service.MessageService;
import com.jzs.messageboard.vo.MessageVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // 我的留言列表
    @GetMapping("/list")
    public String list(HttpSession session, Model model) {
        SysUser user = (SysUser) session.getAttribute("currentUser");
        List<MessageVO> list = messageService.listByUserId(user.getId());
        model.addAttribute("list", list);
        return "message/list";
    }

    // 跳转发布页
    @GetMapping("/add")
    public String addPage() {
        return "message/add";
    }

    // 提交发布留言（POST）
    @PostMapping("/add")
    public String add(@RequestParam("content") String content,
                      @RequestParam(value = "topic", required = false) String topic,
                      @RequestParam(value = "visibility", defaultValue = "0") Integer visibility,
                      @RequestParam(value = "visibleUsernames", required = false) String visibleUsernames,
                      HttpSession session) {
        SysUser user = (SysUser) session.getAttribute("currentUser");
        Message message = new Message();
        message.setUserId(user.getId());
        message.setContent(content);
        message.setTopic(topic);
        message.setVisibility(visibility);
        // 非指定用户模式时清空用户名字段
        if (visibility != 2) {
            message.setVisibleUsernames(null);
        } else {
            message.setVisibleUsernames(visibleUsernames);
        }
        message.setStatus(1);
        message.setIsTop(0);
        messageService.insert(message);
        return "redirect:/index";
    }

    // 跳转编辑页
    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable("id") Long id, HttpSession session, Model model) {
        SysUser user = (SysUser) session.getAttribute("currentUser");
        Message message = messageService.selectById(id);
        if (message == null || !message.getUserId().equals(user.getId())) {
            return "redirect:/message/list";
        }
        model.addAttribute("message", message);
        return "message/edit";
    }

    // 提交编辑（POST）
    @PostMapping("/edit")
    public String edit(@RequestParam("id") Long id,
                       @RequestParam("content") String content,
                       @RequestParam(value = "topic", required = false) String topic,
                       @RequestParam(value = "visibility", defaultValue = "0") Integer visibility,
                       @RequestParam(value = "visibleUsernames", required = false) String visibleUsernames,
                       HttpSession session) {
        SysUser user = (SysUser) session.getAttribute("currentUser");
        Message message = messageService.selectById(id);
        if (message != null && message.getUserId().equals(user.getId())) {
            message.setContent(content);
            message.setTopic(topic);
            message.setVisibility(visibility);
            if (visibility != 2) {
                message.setVisibleUsernames(null);
            } else {
                message.setVisibleUsernames(visibleUsernames);
            }
            messageService.update(message);
        }
        return "redirect:/message/list";
    }

    // 删除留言
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, HttpSession session) {
        SysUser user = (SysUser) session.getAttribute("currentUser");
        Message message = messageService.selectById(id);
        if (message != null && message.getUserId().equals(user.getId())) {
            messageService.deleteById(id);
        }
        return "redirect:/message/list";
    }
}
