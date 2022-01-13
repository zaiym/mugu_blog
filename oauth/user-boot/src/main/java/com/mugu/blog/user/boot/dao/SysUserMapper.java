package com.mugu.blog.user.boot.dao;

import com.mugu.blog.user.common.po.SysRole;
import com.mugu.blog.user.common.po.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserMapper {
    /**
     * 根据用户名查询用户详细信息
     */
    SysUser selectByUserName(String username);

    List<SysRole> selectRolesByUserId(String userId);
}
