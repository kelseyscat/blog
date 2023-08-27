package com.hcl.blog.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface QiniuService {
    // 简单上传，使用默认策略，只需要设置上传的空间名就可以了
    String getUpToken();

    String saveImage(MultipartFile file) throws IOException;
}
