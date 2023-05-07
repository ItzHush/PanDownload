package com.pandownload;


import com.pandownload.entity.MyFile;
import com.pandownload.entity.User;
import com.pandownload.service.FileService;
import com.pandownload.service.UserService;
import com.pandownload.utils.Md5Utils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class PandownloadApplicationTests {

    @Resource
    private UserService service;
    @Resource
    private FileService fileService;
    @Resource
    private Md5Utils md5Utils;
    @Test
    void contextLoads() throws ParseException {
        User user = new User();
        user.setUsername("Hush233");
        user.setPassword(md5Utils.toMD5("1234"));
        user.setNickname("Hush233");
        Integer integer = service.addUser(user);
        System.out.println(integer);
    }

    @Test
    void FileTest(){
        List<MyFile> allFiles = fileService.findAllFiles(1);
        System.out.println(allFiles);
    }

    @Test
    void fileCountsTest(){
        User indexInfo = service.findIndexInfo(1);
        System.out.println(indexInfo);
    }
    @Test
    void fileTest(){
        User indexInfo = service.findIndexInfo(1);
        System.out.println(indexInfo);
    }
}
