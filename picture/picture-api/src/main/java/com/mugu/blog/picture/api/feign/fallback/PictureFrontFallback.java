package com.mugu.blog.picture.api.feign.fallback;

import com.mugu.blog.core.model.PageData;
import com.mugu.blog.core.model.ResultCode;
import com.mugu.blog.core.model.ResultMsg;
import com.mugu.blog.picture.api.feign.front.PictureFrontFeign;
import com.mugu.blog.picture.common.model.req.PictureListReq;
import com.mugu.blog.picture.common.model.vo.PictureVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class PictureFrontFallback implements PictureFrontFeign {

    @Override
    public ResultMsg<PageData<List<PictureVo>>> list(PictureListReq param) {
        log.error("Picture Server Fallback..........");
        return ResultMsg.resultFail(ResultCode.SERVER_FALLBACK.getCode(),ResultCode.SERVER_FALLBACK.getMsg());
    }
}
