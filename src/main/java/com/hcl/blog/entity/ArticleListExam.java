package com.hcl.blog.entity;

import java.util.Date;
import java.util.List;

public class ArticleListExam {

    private Article article;
    private DashBoardStatistic dashBoardStatistic;
    private List<Category> categories;

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public DashBoardStatistic getDashBoardStatistic() {
        return dashBoardStatistic;
    }

    public void setDashBoardStatistic(DashBoardStatistic dashBoardStatistic) {
        this.dashBoardStatistic = dashBoardStatistic;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "ArticleListExam{" +
                "article=" + article +
                ", dashBoardStatistic=" + dashBoardStatistic +
                ", categories=" + categories +
                '}';
    }
}
