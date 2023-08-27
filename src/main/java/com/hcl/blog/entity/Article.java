package com.hcl.blog.entity;

import org.springframework.data.elasticsearch.annotations.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Article {
    private int id;
    private Date createTime;
    private String title;
    private String firstPic;
    private String content;
    private String contentText;
    private String contentHtml;
    private int status;
    private String description;
    private String type;
    private String formattedDate;
    private int likeNum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstPic() {
        return firstPic;
    }

    public void setFirstPic(String firstPic) {
        this.firstPic = firstPic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContentHtml() {
        return contentHtml;
    }
    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", title='" + title + '\'' +
                ", firstPic='" + firstPic + '\'' +
                ", content='" + content + '\'' +
                ", contentText='" + contentText + '\'' +
                ", contentHtml='" + contentHtml + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", formattedDate='" + formattedDate + '\'' +
                ", likeNum=" + likeNum +
                '}';
    }
}
