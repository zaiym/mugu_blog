package com.mugu.blog.article.controller.front;

import com.mugu.blog.article.common.model.vo.FooterTotalVo;
import com.mugu.blog.article.service.FooterService;
import com.mugu.blog.core.model.ResultMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "博客底部接口")
@RequestMapping("/front/footer")
@RestController
public class FooterController {

    @Autowired
    private FooterService footerService;

    @ApiOperation(value = "底部统计信息")
    @PostMapping("/total")
    public ResultMsg<FooterTotalVo> total(){
        return ResultMsg.resultSuccess(footerService.total());
    }
}
