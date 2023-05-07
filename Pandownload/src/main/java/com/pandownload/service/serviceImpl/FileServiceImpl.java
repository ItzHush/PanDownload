//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pandownload.service.serviceImpl;

import com.pandownload.entity.MyFile;
import com.pandownload.mapper.FileMapper;
import com.pandownload.service.FileService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {
    @Resource
    private FileMapper fileMapper;

    public FileServiceImpl() {
    }

    public List<MyFile> findAllFiles(Integer id) {
        return this.fileMapper.findAllFiles(id);
    }

    public void uploadFiles(MyFile myFile) {
        this.fileMapper.uploadFiles(myFile);
    }

    public MyFile findFileById(Integer id) {
        return this.fileMapper.findFileById(id);
    }

    public List<MyFile> searchFiles(String name) {
        return this.fileMapper.searchFiles(name);
    }

    public void setDownLoadCounts(Integer fileId) {
        this.fileMapper.setDownLoadCounts(fileId);
    }

    public void collectById(String id) {
        this.fileMapper.collectById(id);
    }

    public List<MyFile> findAllImages(Integer id) {
        return this.fileMapper.findAllImages(id);
    }

    public void deleteById(Integer id) {
        this.fileMapper.deleteById(id);
    }

    public List<MyFile> findAllCollects(Integer id) {
        return this.fileMapper.findAllCollects(id);
    }
}
