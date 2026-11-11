package com.jzs.messageboard.controller;

import com.jzs.messageboard.entity.SysUser;
import com.jzs.messageboard.service.SysUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private final SysUserService sysUserService;

    public LoginController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    // 跳转登录页
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // 处理登录提交（POST）
    @PostMapping("/login")
    public String doLogin(String username, String password, HttpSession session, Model model) {
        SysUser user = sysUserService.login(username, password);
        if (user == null) {
            model.addAttribute("error", "用户名或密码错误");
            return "login";
        }
        session.setAttribute("currentUser", user);
        if (user.getRole() != null && user.getRole() == 1) {
            return "redirect:/admin/user";
        }
        return "redirect:/index";
    }

    // 退出登录
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
