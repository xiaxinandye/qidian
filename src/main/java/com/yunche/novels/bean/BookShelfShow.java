package com.yunche.novels.bean;

/**
 * @author yunche
 * @date 2019/04/18
 */
public class BookShelfShow {
    private String imageUrl;
    private String bookName;
    private String lastReadTime;
    private String lastChapterName;
    private String novelId;
    private String chapterId;

    public String getNovelId() {
        return novelId;
    }

    public void setNovelId(String novelId) {
        this.novelId = novelId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getLastChapterName() {
        return lastChapterName;
    }

    public void setLastChapterName(String lastChapterName) {
        this.lastChapterName = lastChapterName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getLastReadTime() {
        return lastReadTime;
    }

    public void setLastReadTime(String lastReadTime) {
        this.lastReadTime = lastReadTime;
    }
}
