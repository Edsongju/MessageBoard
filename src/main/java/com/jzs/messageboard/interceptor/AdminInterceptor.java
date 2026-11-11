package com.jzs.messageboard.interceptor;

import com.jzs.messageboard.entity.SysUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        SysUser user = (SysUser) request.getSession().getAttribute("currentUser");
        if (user == null || user.getRole() == null || user.getRole() != 1) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
