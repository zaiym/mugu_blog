package com.mugu.blog.friendlinks.controller.admin;

import com.mugu.blog.core.model.BaseParam;
import com.mugu.blog.core.model.PageData;
import com.mugu.blog.core.model.ResultMsg;
import com.mugu.blog.friend.links.common.model.req.FriendLinksAddReq;
import com.mugu.blog.friend.links.common.model.req.FriendLinksDelReq;
import com.mugu.blog.friend.links.common.model.vo.FriendLinksVo;
import com.mugu.blog.friendlinks.aspect.AvoidRepeatableCommit;
import com.mugu.blog.friendlinks.service.FriendLinksService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Api(value = "友链接口-后台")
@RestController
@RequestMapping("/admin/friend/links")
public class FriendLinksAdminController {

    @Autowired
    private FriendLinksService friendLinksService;

    @AvoidRepeatableCommit
    @ApiOperation(value = "添加友链")
    @PostMapping(value = "/add",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultMsg<Void> add(FriendLinksAddReq param, @RequestParam("file") MultipartFile file){
        friendLinksService.add(param,file);
        return ResultMsg.resultSuccess();
    }

    @ApiOperation(value = "分页获取友链")
    @PostMapping(value = "/list")
    public ResultMsg<PageData<List<FriendLinksVo>>> list(@RequestBody BaseParam param){
        return ResultMsg.resultSuccess(friendLinksService.list(param));
    }

    @ApiOperation(value = "删除友链")
    @PostMapping(value = "/del")
    public ResultMsg<Void> del(@RequestBody @Valid FriendLinksDelReq param){
        friendLinksService.del(param);
        return ResultMsg.resultSuccess();
    }
}
