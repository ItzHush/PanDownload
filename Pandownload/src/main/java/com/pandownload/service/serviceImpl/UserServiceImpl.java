package com.pandownload.service.serviceImpl;

import com.pandownload.entity.User;
import com.pandownload.service.UserService;
import com.pandownload.utils.Md5Utils;
import com.pandownload.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private Md5Utils md5Utils;

    @Override
    public Integer addUser(User user) throws ParseException {
        return userMapper.addUser(user);
    }

    @Override
    public Integer findUserName(User user) {
        return null;
    }

    @Override
    public Integer findId(String username) {
        return userMapper.findById(username);
    }

    @Override
    public List<Map<String, String>> findByUserName(User user) {
        return userMapper.findByUserName(user);
    }

    @Override
    public User findIndexInfo(Integer id) {
        return userMapper.findIndexInfo(id);
    }

    @Override
    public int findUserFileCounts(Integer id) {
        return userMapper.findUserFileCounts(id);
    }

    @Override
    public Integer uploadAvatar(User user) {
        return userMapper.uploadAvatar(user);
    }

    @Override
    public User findOldAvatar(Integer id) {
        return userMapper.findOldAvatar(id);
    }

    @Override
    public User findInfoById(Integer id) {
        return null;
    }

    @Override
    public Integer setPersonInfo(User user) {
        return null;
    }

    @Override
    public void setNickname(String name, Integer id) {
        userMapper.setNickname(name, id);
    }

    @Override
    public void setPassword(String password, Integer userId) {
        userMapper.setPassword(password, userId);
    }
}
