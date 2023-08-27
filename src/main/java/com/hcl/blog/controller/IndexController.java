package com.hcl.blog.controller;

import com.github.pagehelper.PageInfo;
import com.hcl.blog.entity.Article;
import com.hcl.blog.entity.ArticleListExam;
import com.hcl.blog.entity.Category;
import com.hcl.blog.entity.DashBoardStatistic;
import com.hcl.blog.service.ArticleService;
import com.hcl.blog.service.CategoryService;
import com.hcl.blog.service.DashboardService;
import com.hcl.blog.util.PageInfoUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private DashboardService dashboardService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                        @RequestParam(defaultValue = "5", value = "pageSize") Integer pageSize,
                        HttpServletRequest request){
        List<ArticleListExam> articleList = new ArrayList<>();

        List<Article> all = articleService.getAllArticleByDate();

        for(Article a: all){
            ArticleListExam ale = new ArticleListExam();
            DashBoardStatistic st = dashboardService.getDashBoardStatistics();
            List<Category> cs = categoryService.findArticleCategories(a.getId());
            ale.setArticle(a);
            ale.setDashBoardStatistic(st);
            ale.setCategories(cs);
            articleList.add(ale);
        }

        PageInfo<ArticleListExam> pageInfo = PageInfoUtil.listToPageInfo(articleList, pageNum, pageSize);

        request.setAttribute("pageInfo", pageInfo);
        request.setAttribute("ArticleNum", String.valueOf(all.size()));

        return "index";
    }

}
