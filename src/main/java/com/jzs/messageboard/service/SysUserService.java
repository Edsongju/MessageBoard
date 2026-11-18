package com.jzs.messageboard.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jzs.messageboard.entity.SysUser;
import com.jzs.messageboard.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SysUserService {
    private final SysUserMapper sysUserMapper;

    public SysUser login(String username, String password) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username).eq("password", password);
        return sysUserMapper.selectOne(wrapper);
    }

    public void insert(SysUser user) {
        sysUserMapper.insert(user);
    }

    public SysUser selectByUsername(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return sysUserMapper.selectOne(wrapper);
    }

    @Cacheable(value = "userList")
    public List<SysUser> listAll() {
        return sysUserMapper.selectList(new QueryWrapper<SysUser>().orderByDesc("id"));
    }

    @Cacheable(value = "user", key = "#id")
    public SysUser getById(Long id) {
        return sysUserMapper.selectById(id);
    }

    @CacheEvict(value = {"user", "userList"}, allEntries = true)
    public void update(SysUser user) {
        sysUserMapper.updateById(user);
    }

    @CacheEvict(value = {"user", "userList"}, allEntries = true)
    public void deleteById(Long id) {
        sysUserMapper.deleteById(id);
    }
}
