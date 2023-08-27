package com.hcl.blog.entity;

public class LikeCount {
    private String likeKey; // 这里key == articleId
    private int count;

    public String getLikeKey() {
        return likeKey;
    }

    public void setLikeKey(String likeKey) {
        this.likeKey = likeKey;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public LikeCount() {
    }

    public LikeCount(String likeKey, int count) {
        this.likeKey = likeKey;
        this.count = count;
    }

    @Override
    public String toString() {
        return "LikeCount{" +
                "likeKey='" + likeKey + '\'' +
                ", count=" + count +
                '}';
    }
}
