package com.mugu.blog.picture.controller.front;

import com.mugu.blog.core.model.PageData;
import com.mugu.blog.core.model.ResultMsg;
import com.mugu.blog.picture.common.model.req.PictureAddReq;
import com.mugu.blog.picture.common.model.req.PictureDelReq;
import com.mugu.blog.picture.common.model.req.PictureListReq;
import com.mugu.blog.picture.common.model.vo.PictureVo;
import com.mugu.blog.picture.service.PictureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Api(value = "相册接口")
@RestController
@RequestMapping("/front/picture")
public class PictureFrontController {

    @Autowired
    private PictureService pictureService;

    @ApiOperation(value = "获取相册")
    @PostMapping("/list")
    public ResultMsg<PageData<List<PictureVo>>> list(@RequestBody PictureListReq param){
        return ResultMsg.resultSuccess(pictureService.list(param));
    }

}
