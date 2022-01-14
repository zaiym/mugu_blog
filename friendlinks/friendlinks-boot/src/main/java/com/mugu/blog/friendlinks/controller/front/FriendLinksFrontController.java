package com.mugu.blog.friendlinks.controller.front;

import com.mugu.blog.core.model.BaseParam;
import com.mugu.blog.core.model.PageData;
import com.mugu.blog.core.model.ResultMsg;
import com.mugu.blog.friend.links.common.model.req.FriendLinksAddReq;
import com.mugu.blog.friend.links.common.model.vo.FriendLinksVo;
import com.mugu.blog.friendlinks.service.FriendLinksService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(value = "友链接口-前台")
@RestController
@RequestMapping("/front/friend/links")
public class FriendLinksFrontController {

    @Autowired
    private FriendLinksService friendLinksService;

    @ApiOperation(value = "分页获取友链")
    @PostMapping(value = "/list")
    public ResultMsg<PageData<List<FriendLinksVo>>> list(@RequestBody BaseParam param){
        return ResultMsg.resultSuccess(friendLinksService.list(param));
    }
}
