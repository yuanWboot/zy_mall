package com.zy.mall.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 常量类
 */
@Component
public class Constant {
    public static final String IMOOC_MALL_USER = "imooc_mall_user";
    // 盐值
    public static final String SALT  =  "ghj978fjkhjh/./!";

    public static  String FILE_UPLOAD_DIR;
    @Value("${file.upload.dir}")
    public void setFileUploadDir(String fileUploadDir){
        FILE_UPLOAD_DIR = fileUploadDir;
    }
}
