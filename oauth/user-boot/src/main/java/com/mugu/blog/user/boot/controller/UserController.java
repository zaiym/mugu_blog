package com.mugu.blog.user.boot.controller;

import com.mugu.blog.core.model.ResultMsg;
import com.mugu.blog.user.boot.service.SysUserService;
import com.mugu.blog.user.common.po.SysRole;
import com.mugu.blog.user.common.po.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    public SysUserService sysUserService;

    @GetMapping("/getByUsername")
    public ResultMsg<SysUser> getByUsername(String username){
        return ResultMsg.resultSuccess(sysUserService.getUserByUsername(username));
    }

    @GetMapping("/getRolesByUserId")
    public ResultMsg<List<SysRole>> getRolesByUserId(String userId){
        return ResultMsg.resultSuccess(sysUserService.getRolesByUserId(userId));
    }
}
