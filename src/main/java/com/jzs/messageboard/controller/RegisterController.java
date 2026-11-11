package com.jzs.messageboard.controller;

import com.jzs.messageboard.entity.SysUser;
import com.jzs.messageboard.service.SysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    private final SysUserService sysUserService;

    public RegisterController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    // 跳转注册页
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    // 处理注册提交（POST）
    @PostMapping("/register")
    public String doRegister(String username, String password, String confirmPassword, Model model) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            model.addAttribute("error", "用户名和密码不能为空");
            return "register";
        }
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "两次输入的密码不一致");
            return "register";
        }
        SysUser exist = sysUserService.selectByUsername(username);
        if (exist != null) {
            model.addAttribute("error", "用户名已存在");
            return "register";
        }
        SysUser user = new SysUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(0);
        sysUserService.insert(user);
        return "redirect:/login";
    }
}
