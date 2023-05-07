//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pandownload.controller;

import com.pandownload.entity.MyFile;
import com.pandownload.entity.RestBean;
import com.pandownload.entity.User;
import com.pandownload.service.FileService;
import com.pandownload.service.UserService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping({"/files"})
public class FileController {
    @Resource
    private FileService fileService;
    @Resource
    private UserService userService;

    public FileController() {
    }

    @GetMapping({"/findallfiles"})
    public RestBean<List<MyFile>> findAllFiles(HttpServletRequest req) {
        Integer id = (Integer)req.getServletContext().getAttribute("USER_ID");
        List<MyFile> allFilesList = this.fileService.findAllFiles(id);
        return RestBean.success(allFilesList);
    }

    @PostMapping({"/uploadFiles"})
    public RestBean uploadFiles(MultipartFile file, HttpServletRequest req) throws IOException {
        Integer userId = (Integer)req.getServletContext().getAttribute("USER_ID");
        User indexInfo = this.userService.findIndexInfo(userId);
        float useSpace = indexInfo.getUseSpace();
        float space = indexInfo.getSpace() * 1024.0F * 1024.0F;
        boolean isImg = false;
        String oldName = file.getOriginalFilename();
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        String[] suffix = new String[]{"css", "doc", "html", "img", "js", "json", "mp3", "mp4", "oth", "pdf", "ppt", "psd", "txt", "xls", "xml", "zip", "pptx", "docx", "jpg", "png", "7z", "rar", "flac", "wav", "jpeg"};
        boolean pattern = false;
        String[] var12 = suffix;
        int var13 = suffix.length;

        for(int var14 = 0; var14 < var13; ++var14) {
            String s = var12[var14];
            if (ext.equals(s)) {
                pattern = true;
            }
        }

        if (!pattern) {
            ext = "unknown";
        }

        String newName = (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()) + oldName;
        newName = newName.replace("#", "");
        newName = newName.replace("%", "");
        newName = newName.replace("&", "");
        newName = newName.replace("/", "");
        System.out.println(newName);
        float size = (float)file.getSize() / 1024.0F;
        String type = file.getContentType();
        File filefolder = new File("D:\\A_panDownload\\files\\");
        if (!filefolder.exists()) {
            filefolder.mkdirs();
        }

        String path = "D:\\A_panDownload\\files\\";
        MyFile myFile = new MyFile();
        if (!ext.equals("img") && !ext.equals("jpg") && !ext.equals("png") && !ext.equals("jpeg")) {
            myFile.setIsimg(0);
        } else {
            isImg = true;
            myFile.setIsimg(1);
            File imgFolder = new File("D:\\A_panDownload\\files\\images\\");
            File imgZipFolder = new File("D:\\A_panDownload\\files\\images\\zipped\\");
            if (!imgFolder.exists()) {
                imgFolder.mkdirs();
            }

            if (!imgZipFolder.exists()) {
                imgZipFolder.mkdirs();
            }

            path = "D:\\A_panDownload\\files\\images\\";
        }

        if (space - useSpace > size) {
            try {
                file.transferTo(new File(path + newName));
            } catch (IOException var20) {
                var20.printStackTrace();
                return RestBean.failure(403, "上传错误");
            }

            myFile.setOldfilename(oldName);
            myFile.setNewfilename(newName);
            myFile.setExt(ext);
            myFile.setPath(path);
            myFile.setSize(size);
            myFile.setType(type);
            myFile.setUploadtime(new Date(System.currentTimeMillis()));
            myFile.setUser_id(userId);
            System.out.println(myFile);
            this.fileService.uploadFiles(myFile);
            if (isImg) {
                Thumbnails.of(new String[]{path + newName}).size(300, 300).toFile(path + "zipped\\" + newName);
            }

            return RestBean.success();
        } else {
            return RestBean.failure(403, "空间不足");
        }
    }

    @GetMapping({"/downloadFiles/{isImg}/{id}"})
    public void downloadFiles(@PathVariable("id") Integer fileId, @PathVariable("isImg") Integer isImg, HttpServletResponse response) throws IOException {
        MyFile file = this.fileService.findFileById(fileId);
        String path;
        if (isImg == 1) {
            path = "D:\\A_panDownload\\files\\images";
        } else {
            path = "D:\\A_panDownload\\files\\";
        }

        FileInputStream inputStream = new FileInputStream(new File(path, file.getNewfilename()));
        response.reset();
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(file.getOldfilename(), "utf-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        IOUtils.copy(inputStream, outputStream);
        IOUtils.closeQuietly(inputStream);
        IOUtils.closeQuietly(outputStream);
        this.fileService.setDownLoadCounts(fileId);
    }

    @GetMapping({"/search/{name}"})
    public RestBean search(@PathVariable("name") String name) {
        System.out.println(name);
        name = "%" + name + "%";
        List<MyFile> files = this.fileService.searchFiles(name);
        List<MyFile> nullFile = new ArrayList();
        if (files.equals(nullFile)) {
            System.out.println("屁都没有");
            return RestBean.failure(403, "没有找到哟");
        } else {
            System.out.println(files);
            return RestBean.success(files);
        }
    }

    @PostMapping({"/collect/{id}"})
    public RestBean collect(@PathVariable("id") String id) {
        this.fileService.collectById(id);
        return RestBean.success();
    }

    @GetMapping({"/findAllImages"})
    public RestBean findAllImages(HttpServletRequest req) {
        Integer id = (Integer)req.getServletContext().getAttribute("USER_ID");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年 MM月 dd日");
        List<MyFile> images = this.fileService.findAllImages(id);
        List<MyFile> nullFile = new ArrayList();
        if (images.equals(nullFile)) {
            return RestBean.failure(403, "暂无图片");
        } else {
            Map<String, List<MyFile>> imgList = (Map)images.stream().collect(Collectors.groupingBy((img) -> {
                return simpleDateFormat.format(img.getUploadtime());
            }));
            Map<String, List<MyFile>> stringListMap = this.sortMapByKey(imgList);
            Iterator var8 = stringListMap.keySet().iterator();

            while(var8.hasNext()) {
                String s = (String)var8.next();
                System.out.println(s);
            }

            return RestBean.success(stringListMap);
        }
    }

    @DeleteMapping({"/delete/{id}"})
    public RestBean deleteFile(@PathVariable("id") Integer id) {
        MyFile deleteFile = this.fileService.findFileById(id);
        this.fileService.deleteById(id);
        String path = "D:\\A_panDownload\\files\\";
        File file;
        if (deleteFile.getIsimg() == 1) {
            path = "D:\\A_panDownload\\files\\images";
            String zipPath = "D:\\A_panDownload\\files\\images\\zipped";
            file = new File(path, deleteFile.getNewfilename());
            File zipFile = new File(zipPath, deleteFile.getNewfilename());
            if (file.exists()) {
                file.delete();
            }

            if (zipFile.exists()) {
                zipFile.delete();
            }

            return RestBean.success();
        } else {
            file = new File(path, deleteFile.getNewfilename());
            if (file.exists()) {
                file.delete();
            }

            return RestBean.success();
        }
    }

    @GetMapping({"/findAllCollects"})
    public RestBean findAllCollects(HttpServletRequest req) {
        Integer id = (Integer)req.getServletContext().getAttribute("USER_ID");
        List<MyFile> allFilesList = this.fileService.findAllCollects(id);
        return RestBean.success(allFilesList);
    }

    public Map<String, List<MyFile>> sortMapByKey(Map<String, List<MyFile>> map) {
        if (map != null && !map.isEmpty()) {
            Map<String, List<MyFile>> sortMap = new TreeMap(new MapKeyComparator());
            sortMap.putAll(map);
            return sortMap;
        } else {
            return null;
        }
    }
    static class MapKeyComparator implements Comparator<String>{

        @Override
        public int compare(String str1, String str2) {
            return str2.compareTo(str1);
        }
    }
}
