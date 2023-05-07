//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pandownload.service;

import com.pandownload.entity.MyFile;
import java.util.List;

public interface FileService {
    List<MyFile> findAllFiles(Integer var1);

    void uploadFiles(MyFile var1);

    MyFile findFileById(Integer var1);

    List<MyFile> searchFiles(String var1);

    void setDownLoadCounts(Integer var1);

    void collectById(String var1);

    List<MyFile> findAllImages(Integer var1);

    void deleteById(Integer var1);

    List<MyFile> findAllCollects(Integer var1);
}
