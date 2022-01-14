package com.mugu.blog.picture.api.feign.fallback;

import com.mugu.blog.core.model.PageData;
import com.mugu.blog.core.model.ResultCode;
import com.mugu.blog.core.model.ResultMsg;
import com.mugu.blog.picture.api.feign.admin.PictureAdminFeign;
import com.mugu.blog.picture.api.feign.admin.UploadImageFeign;
import com.mugu.blog.picture.common.model.req.PictureAddReq;
import com.mugu.blog.picture.common.model.req.PictureDelReq;
import com.mugu.blog.picture.common.model.req.PictureListReq;
import com.mugu.blog.picture.common.model.vo.PictureVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Component
@Slf4j
public class PictureAdminFallback implements PictureAdminFeign, UploadImageFeign {
    @Override
    public ResultMsg<Void> add(PictureAddReq param, MultipartFile file) {
        log.error("Picture Server Fallback..........");
        return ResultMsg.resultFail(ResultCode.SERVER_FALLBACK.getCode(),ResultCode.SERVER_FALLBACK.getMsg());
    }

    @Override
    public ResultMsg<PageData<List<PictureVo>>> list(PictureListReq param) {
        log.error("Picture Server Fallback..........");
        return ResultMsg.resultFail(ResultCode.SERVER_FALLBACK.getCode(),ResultCode.SERVER_FALLBACK.getMsg());
    }

    @Override
    public ResultMsg<Void> del(@Valid PictureDelReq param) {
        log.error("Picture Server Fallback..........");
        return ResultMsg.resultFail(ResultCode.SERVER_FALLBACK.getCode(),ResultCode.SERVER_FALLBACK.getMsg());
    }
}
