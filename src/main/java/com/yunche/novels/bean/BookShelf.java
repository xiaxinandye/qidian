package com.yunche.novels.bean;

import java.util.Date;

/**
 * @author yunche
 * @date 2019/04/18
 */
public class BookShelf {
    private Integer id;
    private Integer userId;
    private String novelId;
    private String chapterId;
    private String readTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNovelId() {
        return novelId;
    }

    public void setNovelId(String novelId) {
        this.novelId = novelId;
    }

    public String getchapterId() {
        return chapterId;
    }

    public void setchapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getReadTime() {
        return readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }
}
