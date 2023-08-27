package com.hcl.blog.entity;

import java.util.Date;

public class Comment {
    private int id;
    private String content;
    private int replyObject;
    private int replyId;
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getReplyObject() {
        return replyObject;
    }

    public void setReplyObject(int replyObject) {
        this.replyObject = replyObject;
    }

    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", replyObject=" + replyObject +
                ", replyId=" + replyId +
                ", createTime=" + createTime +
                '}';
    }
}
