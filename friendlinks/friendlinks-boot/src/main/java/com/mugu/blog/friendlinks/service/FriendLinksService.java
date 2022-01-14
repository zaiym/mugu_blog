package com.mugu.blog.friendlinks.service;

import com.mugu.blog.core.model.BaseParam;
import com.mugu.blog.core.model.PageData;
import com.mugu.blog.friend.links.common.model.req.FriendLinksAddReq;
import com.mugu.blog.friend.links.common.model.req.FriendLinksDelReq;
import com.mugu.blog.friend.links.common.model.vo.FriendLinksVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FriendLinksService {
    void add(FriendLinksAddReq param, MultipartFile file);

    PageData<List<FriendLinksVo>> list(BaseParam param);

    void del(FriendLinksDelReq param);
}
