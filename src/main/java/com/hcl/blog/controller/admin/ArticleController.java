package com.hcl.blog.controller.admin;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hcl.blog.entity.Article;
import com.hcl.blog.entity.ArticleListExam;
import com.hcl.blog.entity.Category;
import com.hcl.blog.entity.DashBoardStatistic;
import com.hcl.blog.service.ArticleService;
import com.hcl.blog.service.CategoryService;
import com.hcl.blog.service.DashboardService;
import com.hcl.blog.util.PageInfoUtil;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ElasticsearchClient client;

//    @GetMapping
//    public String articlePage(HttpServletRequest request){
//        List<ArticleListExam> articleList = new ArrayList<>();
//
//        List<Article> all = articleService.getAllArticleByDate();
//
//        for(Article a: all){
//            ArticleListExam ale = new ArticleListExam();
//            DashBoardStatistic st = dashboardService.getDashBoardStatistics();
//            List<Category> cs = categoryService.findArticleCategories(a.getId());
//            ale.setArticle(a);
//            ale.setDashBoardStatistic(st);
//            ale.setCategories(cs);
//            articleList.add(ale);
//        }
//
//        request.setAttribute("articleList", articleList);
//
//        return "admin/ico";
//    }

    @GetMapping
    public String articlePage(HttpServletRequest request,
                              @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                              @RequestParam(defaultValue = "5", value = "pageSize") Integer pageSize) throws IOException {
        List<ArticleListExam> articleList = new ArrayList<>();

        List<Article> all = articleService.getAllArticleByDate();

        // 初始化elasticsearch
//        elasticSearchService.initIndex(); // 写个判断语句

        // 批量写入elasticsearch服务器， 为search做准备
        BulkRequest.Builder br = new BulkRequest.Builder();
        for (Article a : all) {
            br.operations(op -> op
                    .index(idx -> idx
                            .index("article")
                            .id(String.valueOf(a.getId()))
                            .document(a)
                    )
            );
        }

        BulkResponse result = client.bulk(br.refresh(Refresh.WaitFor).build());

        if (result.errors()) {
            System.out.println("Bulk had errors");
            for (BulkResponseItem item: result.items()) {
                if (item.error() != null) {
                    System.out.println(item.error().reason());
                }
            }
        }

        // 正常加载
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

        return "admin/ico";
    }

    @RequestMapping(value = "/deleteArticle", method = RequestMethod.GET)
    public String deleteArticle(@RequestParam int id){
        Article a = articleService.getArticleById(id);
        if(a != null){
            System.out.println("delete the No." + id + " article!");
            articleService.deleteArticle(id);
        }
        return "redirect:/article";
    }

    @RequestMapping(value = "/beforeUpdateArticle", method = RequestMethod.GET)
    public String beforeUpdateArticle(@RequestParam int id, HttpServletRequest request){
        Article a = articleService.getArticleById(id);
        System.out.println(a.getTitle());
        request.setAttribute("article", a);
        return "admin/write";
    }


}
