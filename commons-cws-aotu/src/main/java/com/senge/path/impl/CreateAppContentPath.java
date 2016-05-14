package com.clear.path.impl;

import com.clear.path.CreateInfo;

import java.io.File;

/**
 * Created by chengweisen on 2014/11/16.
 * 创建webapp根目录
 * root+path = webapp
 * 存放解压的War包文件的路径
 */
public final class CreateAppContentPath implements CreateInfo {

    private String root = null;

    private String path = null;

    public CreateAppContentPath(String root, String path) {
        this.root = root;
        this.path = path;
        superContextPath();
    }

    public void superContextPath() {
        if (String.valueOf(path) == "") {
            new Exception("path is null");
            return;
        }
        String appPath = root + File.separatorChar + path;
        File p = new File(appPath);
        if (!p.exists()) {
            p.mkdirs();
        }
    }
}
