package com.pandownload.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private String salt;
    private String nickname;
    private float space;
    private float useSpace;//useSpace不在表中
    private int fileCounts;
    private String avatar;
    private String newavatarname;
    private Date regDate;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", nickname='" + nickname + '\'' +
                ", space=" + space +
                ", usespace=" + useSpace +
                ", filecounts=" + fileCounts +
                ", avatar='" + avatar + '\'' +
                ", newavatarname='" + newavatarname + '\'' +
                ", regdate=" + regDate +
                '}';
    }
}
