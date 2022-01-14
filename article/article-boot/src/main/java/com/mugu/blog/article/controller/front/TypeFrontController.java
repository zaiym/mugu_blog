package com.mugu.blog.article.controller.front;

import com.mugu.blog.article.common.model.po.Type;
import com.mugu.blog.article.common.model.req.TypeAddReq;
import com.mugu.blog.article.common.model.req.TypeListReq;
import com.mugu.blog.article.common.model.vo.TypeVo;
import com.mugu.blog.article.service.TypeService;
import com.mugu.blog.core.model.PageData;
import com.mugu.blog.core.model.ResultMsg;
import com.mugu.blog.mybatis.config.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "分类接口-前台")
@RestController
@RequestMapping("/front/type")
public class TypeFrontController {

    @Autowired
    private TypeService typeService;

    @ApiOperation("/分页查询分类")
    @PostMapping("/list")
    public ResultMsg<PageData<TypeVo>> list(@RequestBody TypeListReq param){
        PageData<TypeVo> page = PageUtils.getPage(param, p -> typeService.list(p));
        return ResultMsg.resultSuccess(page);
    }

    @ApiOperation("/根据ID查询分类详情")
    @GetMapping("/getById")
    public ResultMsg<Type> getById(Long id){
        return ResultMsg.resultSuccess(typeService.getById(id));
    }
}
