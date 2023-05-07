package com.pandownload.service;

import com.pandownload.entity.User;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface UserService {

    /**
     * 用户注册
     * @param user
     * @return
     */
    Integer addUser(User user) throws ParseException;

    /**
     * 查重
     * @param user
     * @return
     */
    public Integer findUserName(User user);

    /**
     * 获取id
     * @param username
     * @return
     */
     Integer findId(String username);
    /**
     * 账号密码登录
     * @param user
     * @return
     */
    List<Map<String, String>> findByUserName(User user);

    /**
     * 查询空间和头像
     * @param id
     * @return
     */
    public User findIndexInfo(Integer id);
    /**
     * 获取文件数量
     * @return
     */
    int findUserFileCounts(Integer id);

    /**
     * 上传头像
     * @param user
     * @return
     */
    public Integer uploadAvatar(User user);

    /**
     * 查询原来的头像信息进行后续删除
     * @param id
     * @return
     */
    User findOldAvatar(Integer id);
    /**
     * 查询个人信息
     * @param id
     * @return
     */
    public User findInfoById(Integer id);
    /**
     * 修改个人信息
     * @param user
     * @return
     */
    public Integer setPersonInfo(User user);

    void setNickname(String name, Integer id);

    void setPassword(String password, Integer userId);
}