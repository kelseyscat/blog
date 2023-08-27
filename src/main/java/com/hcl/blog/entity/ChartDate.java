package com.hcl.blog.entity;

public class ChartDate {

    private String time;
    private int num;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "ChartDate{" +
                "time='" + time + '\'' +
                ", num=" + num +
                '}';
    }
}
