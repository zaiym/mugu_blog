package com.mugu.blog.user.api.feign;

import com.mugu.blog.core.model.ResultMsg;
import com.mugu.blog.user.common.po.SysRole;
import com.mugu.blog.user.common.po.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * user的feign接口
 */
@FeignClient(value = "blog-user-boot",contextId = "user-boot")
public interface UserFeign {

    @GetMapping("/user/getByUsername")
    ResultMsg<SysUser> getByUsername(@RequestParam(value = "username") String username);

    @GetMapping("/user/getRolesByUserId")
    ResultMsg<List<SysRole>> getRolesByUserId(@RequestParam(value = "userId") String userId);
}
