package com.mugu.blog.friendlinks.dao;

import com.mugu.blog.core.model.BaseParam;
import com.mugu.blog.friend.links.common.model.po.FriendLinks;
import com.mugu.blog.friend.links.common.model.vo.FriendLinksVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description 友链
 */
@Mapper
public interface FriendLinksMapper {

    /**
    * 新增
    * @date 2022/02/01
    **/
    int insert(FriendLinks friendLinks);

    /**
    * 刪除
    * @date 2022/02/01
    **/
    int delete(int id);

    /**
    * 更新
    * @date 2022/02/01
    **/
    int update(FriendLinks friendLinks);

    /**
    * 查询 根据主键 id 查询
    * @date 2022/02/01
    **/
    FriendLinks load(int id);

    /**
    * 查询 分页查询
    * @date 2022/02/01
    **/
    List<FriendLinksVo> pageList(BaseParam param);

    /**
    * 查询 分页查询 count
    * @date 2022/02/01
    **/
    int pageListCount(int offset,int pagesize);

}