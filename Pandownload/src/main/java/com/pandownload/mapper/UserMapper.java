package com.pandownload.mapper;

import com.pandownload.entity.User;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface UserMapper {
    /**
     * 账号密码登录
     * @param user
     * @return
     */
   @MapKey("id")
   List<Map<String, String>> findByUserName(User user);

    Integer findById(String username);

    Integer addUser(User user);

    int findUserFileCounts(Integer id);

    User findIndexInfo(Integer id);

    User findOldAvatar(Integer id);

    Integer uploadAvatar(User user);

    void setNickname(@Param("name") String name, @Param("id") Integer id);

    void setPassword(@Param("password") String password, @Param("id") Integer userId);
}
