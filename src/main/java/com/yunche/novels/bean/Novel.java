package com.yunche.novels.bean;


/**
 * @ClassName: Novel
 * @Description:
 * @author: yunche
 * @date: 2019/02/16
 */
public class Novel {

    private String id;
    private String name;
    private String author;
    private Integer wordCounts;
    private String description;
    private String coverImage;
    private String coverThumb;
    private Integer ticketsMonth;
    private Integer ticketsRecommend;
    private Integer categoryId;
    private Integer chapterCounts;

    public Integer getChapterCounts() {
        return chapterCounts;
    }

    public void setChapterCounts(Integer chapterCounts) {
        this.chapterCounts = chapterCounts;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getWordCounts() {
        return wordCounts;
    }

    public void setWordCounts(Integer wordCounts) {
        this.wordCounts = wordCounts;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getCoverThumb() {
        return coverThumb;
    }

    public void setCoverThumb(String coverThumb) {
        this.coverThumb = coverThumb;
    }

    public Integer getTicketsMonth() {
        return ticketsMonth;
    }

    public void setTicketsMonth(Integer ticketsMonth) {
        this.ticketsMonth = ticketsMonth;
    }

    public Integer getTicketsRecommend() {
        return ticketsRecommend;
    }

    public void setTicketsRecommend(Integer ticketsRecommend) {
        this.ticketsRecommend = ticketsRecommend;
    }

    @Override
    public String toString() {
        return "Novel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", wordsCount=" + wordCounts +
                ", description='" + description + '\'' +
                ", coverImage='" + coverImage + '\'' +
                ", coverThumb='" + coverThumb + '\'' +
                ", ticketsMonth=" + ticketsMonth +
                ", ticketsRecommend=" + ticketsRecommend +
                '}';
    }
}
