package com.mugu.blog.file.server.service.impl;

import com.mugu.blog.common.utils.AssertUtils;
import com.mugu.blog.core.model.ResultCode;
import com.mugu.blog.file.server.dao.FileMapper;
import com.mugu.blog.file.server.model.FtpProperties;
import com.mugu.blog.file.server.model.constant.FileConstant;
import com.mugu.blog.file.server.model.po.FilePO;
import com.mugu.blog.file.server.service.ImageUploadService;
import com.mugu.blog.file.server.utils.MD5Utils;
import com.mugu.blog.file.server.utils.SftpUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
public class ImageUploadServiceImpl implements ImageUploadService {

    @Autowired
    private FtpProperties ftpProperties;

    @Autowired
    private FileMapper mapper;

    @Transactional
    @SneakyThrows
    @Override
    public String upload(MultipartFile file) {
        //对文件进行MD5
        String md5 = MD5Utils.getFileMd5(file.getInputStream());
        FilePO filePO = mapper.selectByMd5(md5);
        if (Objects.nonNull(filePO)){
            String[] locations = filePO.getLocation().split(FileConstant.FILE_SEPARATOR);
            return ftpProperties.getImageUrlPrefix()+locations[locations.length-1];
        }

        //保存图片
        String fileName = file.getOriginalFilename();
        //后缀名
        String suffixName = Objects.requireNonNull(fileName).substring(fileName.lastIndexOf("."));
        //生成最终的图片名称
        String imageName = UUID.randomUUID().toString().replaceAll("-", "") + suffixName;

        //图片必须小于5M
        AssertUtils.assertTrue(file.getSize()<1024*1024*5, ResultCode.FILE_MUCH_MAX);

        SftpUtils.upload(file.getInputStream(),ftpProperties.getImagePath()+imageName);

        //存入数据库
        FilePO po = new FilePO();
        po.setStatus(1);
        po.setCreateTime(new Date());
        po.setUpdateTime(new Date());
        po.setLocation(ftpProperties.getImagePath()+imageName);
        po.setMd5(md5);
        mapper.insert(po);
        return ftpProperties.getImageUrlPrefix()+imageName;
    }

    @Override
    public String upload(MultipartFile file, double rate) {
        return null;
    }

    @Override
    public String upload(MultipartFile file, Integer height, Integer width) {
        return null;
    }

    @Override
    public String upload(MultipartFile file, double rate, Integer height, Integer width) {
        return null;
    }
}
