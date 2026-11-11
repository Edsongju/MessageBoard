package com.jzs.messageboard.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jzs.messageboard.entity.SysUser;
import com.jzs.messageboard.mapper.SysUserMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SysUserService {

    private final SysUserMapper sysUserMapper;

    // 构造器注入
    public SysUserService(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    // 登录校验
    public SysUser login(String username, String password) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, username)
                .eq(SysUser::getPassword, password);
        return sysUserMapper.selectOne(wrapper);
    }

    // 根据用户名查询
    public SysUser selectByUsername(String username) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, username);
        return sysUserMapper.selectOne(wrapper);
    }

    // 新增用户
    public void insert(SysUser user) {
        sysUserMapper.insert(user);
    }

    // 查询所有用户
    public List<SysUser> list() {
        return sysUserMapper.selectList(null);
    }

    // 根据ID删除用户
    public void deleteById(Long id) {
        sysUserMapper.deleteById(id);
    }
}
