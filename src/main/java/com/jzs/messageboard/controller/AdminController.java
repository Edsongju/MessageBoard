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
        List<SysUser> list = sysUserService.listAll();
        model.addAttribute("list", list);
        model.addAttribute("currentAdminPage", "user");
        return "admin/user";
    }

    // 设为管理员
    @GetMapping("/user/setAdmin/{id}")
    public String setAdmin(@PathVariable Long id) {
        SysUser user = sysUserService.getById(id);
        if (user != null) {
            user.setRole(1);
            sysUserService.update(user);
        }
        return "redirect:/admin/user";
    }

    // 留言管理列表（支持搜索）
    @GetMapping("/message")
    public String messageList(@RequestParam(required = false) String keyword, Model model) {
        List<MessageVO> list = messageService.listAllWithUsername(keyword);
        model.addAttribute("list", list);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentAdminPage", "message");
        return "admin/message";
    }

    // 切换留言置顶状态
    @GetMapping("/message/top/{id}")
    public String toggleTop(@PathVariable Long id) {
        Message message = messageService.selectById(id);
        if (message != null) {
            if (message.getIsTop() == null || message.getIsTop() == 0) {
                message.setIsTop(1);
            } else {
                message.setIsTop(0);
            }
            messageService.update(message);
        }
        return "redirect:/admin/message";
    }

    // 删除留言
    @GetMapping("/message/delete/{id}")
    public String deleteMessage(@PathVariable Long id) {
        messageService.deleteById(id);
        return "redirect:/admin/message";
    }
}
