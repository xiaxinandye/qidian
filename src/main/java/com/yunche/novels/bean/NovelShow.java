package com.yunche.novels.bean;

import java.text.DateFormat;
import java.util.Date;

/**
 * @ClassName: NovelShow
 * @Description: 用于展示小说的一些状态，如最近更新章节等
 * @author: yunche
 * @date: 2019/02/19
 */
public class NovelShow {
    /***
     * 最近更新章节名
     */
    private String lastChapterName;

    /**
     * 最新章节更新日期
     */
    private String lastUpdate;

    private String lastChapterId;

    public String getLastChapterId() {
        return lastChapterId;
    }

    public void setLastChapterId(String lastChapterId) {
        this.lastChapterId = lastChapterId;
    }

    public String getLastChapterName() {
        return lastChapterName;
    }

    public void setLastChapterName(String lastChapterName) {
        this.lastChapterName = lastChapterName;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "NovelShow{" +
                "lastChapterName='" + lastChapterName + '\'' +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
