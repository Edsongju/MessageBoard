package com.jzs.messageboard.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jzs.messageboard.entity.SysUser;
import com.jzs.messageboard.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserService {

    private final SysUserMapper sysUserMapper;

    public SysUserService(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    // 登录校验：用户名+密码
    public SysUser login(String username, String password) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username)
                .eq("password", password);
        return sysUserMapper.selectOne(wrapper);
    }

    // 注册：新增用户
    public void insert(SysUser user) {
        sysUserMapper.insert(user);
    }

    // 根据用户名查询
    public SysUser selectByUsername(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return sysUserMapper.selectOne(wrapper);
    }

    // 后台：查询所有用户
    public List<SysUser> listAll() {
        return sysUserMapper.selectList(new QueryWrapper<SysUser>().orderByDesc("id"));
    }

    // 后台：根据ID查询用户
    public SysUser getById(Long id) {
        return sysUserMapper.selectById(id);
    }

    // 后台：更新用户信息（设为管理员）
    public void update(SysUser user) {
        sysUserMapper.updateById(user);
    }

    // 后台：删除用户
    public void deleteById(Long id) {
        sysUserMapper.deleteById(id);
    }
}
