package com.jzs.messageboard.controller;

import com.jzs.messageboard.entity.Message;
import com.jzs.messageboard.entity.SysUser;
import com.jzs.messageboard.service.MessageService;
import com.jzs.messageboard.service.SysUserService;
import com.jzs.messageboard.vo.MessageVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final SysUserService sysUserService;
    private final MessageService messageService;

    public AdminController(SysUserService sysUserService, MessageService messageService) {
        this.sysUserService = sysUserService;
        this.messageService = messageService;
    }

    // 用户管理列表
    @GetMapping("/user")
    public String userList(Model model) {
        List<SysUser> list = sysUserService.list();
        model.addAttribute("list", list);
        return "admin/user";
    }

    // 删除用户
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        sysUserService.deleteById(id);
        return "redirect:/admin/user";
    }

    // 留言管理列表
    @GetMapping("/message")
    public String messageList(@RequestParam(required = false) String keyword, Model model) {
        List<MessageVO> list = messageService.listAllWithUsername(keyword);
        model.addAttribute("list", list);
        model.addAttribute("keyword", keyword);
        return "admin/message";
    }

    // 删除留言
    @GetMapping("/message/delete/{id}")
    public String deleteMessage(@PathVariable("id") Long id) {
        messageService.deleteById(id);
        return "redirect:/admin/message";
    }
    @GetMapping("/message/top/{id}")
    public String toggleTop(@PathVariable("id") Long id) {
        Message message = messageService.selectById(id);
        if (message != null) {
            // 状态切换：0 → 1，1 → 0
            if (message.getIsTop() == null || message.getIsTop() == 0) {
                message.setIsTop(1);
            } else {
                message.setIsTop(0);
            }
            messageService.update(message);
        }
        return "redirect:/admin/message";
    }
}
