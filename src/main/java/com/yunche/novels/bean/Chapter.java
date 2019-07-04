package com.yunche.novels.bean;

/**
 * @ClassName: Chapter
 * @Description:
 * @author: yunche
 * @date: 2019/02/23
 */
public class Chapter {

    private String id;
    private String novelId;
    private String volumeName;
    private String name;
    private Integer wordCounts;
    private String createDatetime;
    private String content;
    private Integer isQidian;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNovelId() {
        return novelId;
    }

    public void setNovelId(String novelId) {
        this.novelId = novelId;
    }

    public String getVolumeName() {
        return volumeName;
    }

    public void setVolumeName(String volumeName) {
        this.volumeName = volumeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWordCounts() {
        return wordCounts;
    }

    public void setWordCounts(Integer wordCounts) {
        this.wordCounts = wordCounts;
    }

    public String getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsQidian() {
        return isQidian;
    }

    public void setIsQidian(Integer isQidian) {
        this.isQidian = isQidian;
    }

    public Integer getIsVip() {
        return isVip;
    }

    public void setIsVip(Integer isVip) {
        this.isVip = isVip;
    }

    private Integer isVip;
}
