package com.hcl.blog.entity;

public class Like {

    private String ip;
    private int articleId;
    private int userId;
    private int status;

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Like() {
    }

    public Like(int articleId, String ip, Integer status) {
        this.articleId = articleId;
        this.ip = ip;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Like{" +
                ", ip=" + ip +
                ", articleId=" + articleId +
                ", userId=" + userId +
                ", status=" + status +
                '}';
    }
}
