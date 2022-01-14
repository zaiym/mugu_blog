package com.mugu.blog.picture.dao;

import com.mugu.blog.picture.common.model.po.Picture;
import com.mugu.blog.picture.common.model.req.PictureListReq;
import com.mugu.blog.picture.common.model.vo.PictureVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description picture
 * @author zhengkai.blog.csdn.net
 * @date 2022-01-29
 */
@Mapper
public interface PictureMapper {

    /**
    * 新增
    * @date 2022/01/29
    **/
    int insert(Picture picture);

    /**
    * 刪除
    * @date 2022/01/29
    **/
    int delete(int id);

    /**
    * 更新
    * @date 2022/01/29
    **/
    int update(Picture picture);

    /**
    * 查询 根据主键 id 查询
    * @date 2022/01/29
    **/
    Picture load(int id);

    /**
    * 查询 分页查询
    * @date 2022/01/29
    **/
    List<PictureVo> pageList(PictureListReq param);

    /**
    * 查询 分页查询 count
    * @date 2022/01/29
    **/
    int pageListCount(int offset,int pagesize);

    String selectByMd5(String md5);

}