package com.mugu.blog.user.boot.service;

import com.mugu.blog.user.common.po.SysRole;
import com.mugu.blog.user.common.po.SysUser;

import java.util.List;

public interface SysUserService {
    SysUser getUserByUsername(String username);

    List<SysRole> getRolesByUserId(Long userId);

    SysUser getByUserId(String userId);

}
