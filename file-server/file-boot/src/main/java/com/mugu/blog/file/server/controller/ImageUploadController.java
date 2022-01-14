package com.mugu.blog.file.server.controller;

import com.mugu.blog.core.model.ResultMsg;
import com.mugu.blog.file.server.service.ImageUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "图片上传接口")
@RestController
@RequestMapping("/image")
public class ImageUploadController {

    @Autowired
    private ImageUploadService imageUploadService;

    @ApiOperation(value = "上传图片")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "file",value = "文件",required = true,dataTypeClass = MultipartFile.class)
    })
    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultMsg<String> upload(@RequestParam(value = "file") MultipartFile file){
        return ResultMsg.resultSuccess(imageUploadService.upload(file));
    }
}
