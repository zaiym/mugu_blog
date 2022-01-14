package com.mugu.blog.file.server.dao;

import com.mugu.blog.file.server.model.po.FilePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description file
 * @author zhengkai.blog.csdn.net
 * @date 2022-02-02
 */
@Mapper
public interface FileMapper {

    /**
    * 新增
    * @author zhengkai.blog.csdn.net
    * @date 2022/02/02
    **/
    int insert(FilePO file);

    /**
    * 刪除
    * @author zhengkai.blog.csdn.net
    * @date 2022/02/02
    **/
    int delete(int id);

    /**
    * 更新
    * @author zhengkai.blog.csdn.net
    * @date 2022/02/02
    **/
    int update(FilePO file);

    /**
    * 查询 根据主键 id 查询
    * @author zhengkai.blog.csdn.net
    * @date 2022/02/02
    **/
    FilePO load(int id);

    /**
    * 查询 分页查询
    * @author zhengkai.blog.csdn.net
    * @date 2022/02/02
    **/
    List<FilePO> pageList(int offset, int pagesize);

    /**
    * 查询 分页查询 count
    * @author zhengkai.blog.csdn.net
    * @date 2022/02/02
    **/
    int pageListCount(int offset,int pagesize);

    FilePO selectByMd5(String md5);

}