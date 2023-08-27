package com.hcl.blog.entity;

public class DashBoardStatistic {

    private Long articles;
    private Long comments;
    private Long readingVolume;
    private Long createDay;

    public Long getArticles() {
        return articles;
    }

    public void setArticles(Long articles) {
        this.articles = articles;
    }

    public Long getComments() {
        return comments;
    }

    public void setComments(Long comments) {
        this.comments = comments;
    }

    public Long getReadingVolume() {
        return readingVolume;
    }

    public void setReadingVolume(Long readingVolume) {
        this.readingVolume = readingVolume;
    }

    public Long getCreateDay() {
        return createDay;
    }

    public void setCreateDay(Long createDay) {
        this.createDay = createDay;
    }

    @Override
    public String toString() {
        return "DashBoardStatistic{" +
                "articles=" + articles +
                ", comments=" + comments +
                ", readingVolume=" + readingVolume +
                ", createDay=" + createDay +
                '}';
    }
}
