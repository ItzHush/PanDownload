package com.pandownload.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyFile {
    private Integer id;
    private String oldfilename;
    private String newfilename;
    private String ext;
    private String path;
    private float size;
    private String type;
    private int isimg;
    private int downcounts;
    private Date uploadtime;
    private Date changetime;
    private int ispublic;
    private int iscollect;
    private int isshare;
    private int isrecycle;
    private Integer user_id;
    //以下属性不存在数据库
    private String icon;
    private int ids;

    @Override
    public String toString() {
        return "Myfile{" +
                "id=" + id +
                ", oldfilename='" + oldfilename + '\'' +
                ", newfilename='" + newfilename + '\'' +
                ", ext='" + ext + '\'' +
                ", path='" + path + '\'' +
                ", size=" + size +
                ", type='" + type + '\'' +
                ", isimg=" + isimg +
                ", downcounts=" + downcounts +
                ", uploadtime=" + uploadtime +
                ", changetime=" + changetime +
                ", ispublic=" + ispublic +
                ", iscollect=" + iscollect +
                ", isshare=" + isshare +
                ", isrecycle=" + isrecycle +
                ", user_id=" + user_id +
                ", icon='" + icon + '\'' +
                ", ids=" + ids +
                '}';
    }

}
