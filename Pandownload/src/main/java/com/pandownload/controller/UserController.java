package com.pandownload.controller;

import com.pandownload.entity.RestBean;
import com.pandownload.entity.User;
import com.pandownload.service.UserService;
import com.pandownload.utils.Md5Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private Md5Utils md5Utils;

    //登录模块
    @PostMapping ("/login")
    public RestBean login(@RequestBody User user, HttpServletRequest req){
        List<Map<String, String>> list = userService.findByUserName(user);
        if(list.size() == 1){
            req.getServletContext().removeAttribute("USER_NAME");
            req.getServletContext().removeAttribute("USER_ID");
            String pwd = list.get(0).get("password");
            Integer id = userService.findId(user.getUsername());
            if (pwd.equals(md5Utils.toMD5(user.getPassword()))){
//                session.setAttribute("USER_NAME",user.getUsername());
//                session.setAttribute("USER_ID",id);
                req.getServletContext().setAttribute("USER_NAME",user.getUsername());
                req.getServletContext().setAttribute("USER_ID",id);
                return RestBean.success(200);
            }
        }
        return RestBean.failure(403, "账号或密码错误");
    }
    @PostMapping("/getUserName")
    public RestBean getUserName(@RequestBody User user){
        if(userService.findId(user.getUsername()) != null){
            return RestBean.failure(403, "用户名已存在");
        } else  {
            System.out.println(userService.findId(user.getUsername()));
            return RestBean.success(200);
        }
    }

    @PostMapping("/register")
    public RestBean register(@RequestBody User user) throws ParseException {
        String md5 = md5Utils.toMD5(user.getPassword());
        user.setPassword(md5);
        Date date = new Date();
        user.setRegDate(date);
        Integer integer = userService.addUser(user);
        if(integer == 1){
            return RestBean.success(200);
        } else return RestBean.failure(403, "添加失败");
    }

    @GetMapping("/getIndexInfo")
    public RestBean getUserInfo(HttpServletRequest req){
        int fileCounts = userService.findUserFileCounts((Integer) req.getServletContext().getAttribute("USER_ID"));
        System.out.println(fileCounts);
        User user = this.userService.findIndexInfo((Integer) req.getServletContext().getAttribute("USER_ID"));
        List<String> list = new LinkedList<>();
        //获取使用空间
        float useSpace = 0;
        if (fileCounts!=0){
            useSpace = user.getUseSpace();
        }
        //获取总空间
        float space = user.getSpace();
        //获取头像地址
        String avatar = user.getAvatar();
        //头像名称
        String newavatarname = user.getNewavatarname();
        //昵称
        String nickname = user.getNickname();
        //百分比
        String sp = String.valueOf((useSpace/(space*1024*1024))*100);
        System.out.println((useSpace/(space*1024*1024)));
        if(sp.length()> 6)
            sp = sp.substring(0, 4);
        //设置头像
        if ("0".equals(avatar)){
            list.add("tou.jpg");
        }else{
            list.add(newavatarname);
        }
        //计算结果保留两位小数
        if (useSpace<1024){
            list.add(useSpace+"KB");
        }else if (useSpace>=1024&&useSpace<1048576){
            BigDecimal b= new BigDecimal(useSpace/1024);
            float f1 = b.setScale(1, RoundingMode.HALF_UP).floatValue();
            list.add(f1+"MB");
        }else{
            BigDecimal b= new BigDecimal(useSpace/1024/1024);
            float f2 = b.setScale(2, RoundingMode.HALF_UP).floatValue();
            list.add(f2+"GB");
        }
        list.add(nickname);
        //list.add(username);
        list.add(String.valueOf((long)space));
        list.add(String.valueOf(useSpace));
        list.add(String.valueOf(fileCounts));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String reg = dateFormat.format(user.getRegDate());
        list.add(reg);
        list.add(sp);
        list.add(newavatarname);
        return RestBean.success(list);
    }

    //更换头像
    @PostMapping("/avatarUpload")
    public RestBean avatarUpload(MultipartFile file, HttpServletRequest req){
        Integer userId = (Integer) req.getServletContext().getAttribute("USER_ID");
        String oldAvatarName = file.getOriginalFilename();
        //生成新的文件名称
        String newAvatarName = userId +"_"+ UUID.randomUUID().toString().replace("-","") +"_"+ oldAvatarName;
        String path = "D:\\A_panDownload\\avatar\\";
        File filepath = new File(path);
        if (!filepath.exists()) {
            filepath.mkdirs();
        }
        User oldAvatar = userService.findOldAvatar(userId);
        System.out.println(oldAvatar.getNewavatarname());
        if(oldAvatar.getNewavatarname()!= null){
            File oldavatarfile = new File("D:/A_panDownload/avatar/",oldAvatar.getNewavatarname());
            if (oldavatarfile.exists()){
                oldavatarfile.delete();
            }
        }
        //上传文件
        try {
            file.transferTo(new File(path + newAvatarName));
        } catch (IOException e) {
            return RestBean.failure(403, "上传失败力······");
        }
        User user = new User();
        user.setId(userId);
        user.setAvatar("avatar/");
        user.setNewavatarname(newAvatarName);
        userService.uploadAvatar(user);
        return RestBean.success();
    }

    //修改个人信息
    @PostMapping("/changeNickName/{value}")
    public RestBean changeNickName (@PathVariable("value") String name, HttpServletRequest req) {
        Integer userId = (Integer) req.getServletContext().getAttribute("USER_ID");
        userService.setNickname(name, userId);
        return RestBean.success();
    }
    @PostMapping("/changePassword/{value}")
    public RestBean changePassword (@PathVariable("value") String password, HttpServletRequest req) {
        String md5 = md5Utils.toMD5(password);
        Integer userId = (Integer) req.getServletContext().getAttribute("USER_ID");
        userService.setPassword(md5, userId);
        return RestBean.success();
    }


}
