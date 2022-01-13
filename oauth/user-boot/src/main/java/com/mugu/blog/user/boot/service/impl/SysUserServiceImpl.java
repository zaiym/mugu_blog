package com.mugu.blog.user.boot.service.impl;

import com.mugu.blog.user.boot.dao.SysUserMapper;
import com.mugu.blog.user.boot.service.SysUserService;
import com.mugu.blog.user.common.po.SysRole;
import com.mugu.blog.user.common.po.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser getUserByUsername(String username) {
        return sysUserMapper.selectByUserName(username);
    }

    @Override
    public List<SysRole> getRolesByUserId(String userId) {
        return sysUserMapper.selectRolesByUserId(userId);
    }
}

