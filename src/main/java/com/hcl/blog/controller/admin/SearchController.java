package com.hcl.blog.controller.admin;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.alibaba.fastjson2.JSON;
import com.github.pagehelper.PageInfo;
import com.hcl.blog.entity.Article;
import com.hcl.blog.entity.ArticleListExam;
import com.hcl.blog.entity.Category;
import com.hcl.blog.entity.DashBoardStatistic;
import com.hcl.blog.service.ArticleService;
import com.hcl.blog.service.CategoryService;
import com.hcl.blog.service.DashboardService;
import com.hcl.blog.service.ElasticSearchService;
import com.hcl.blog.util.PageInfoUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class SearchController {

    @Autowired
    private ElasticsearchClient client;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ElasticSearchService elasticSearchService;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String articlePage(HttpServletRequest request,
                              @RequestParam String keyword,
                              @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                              @RequestParam(defaultValue = "5", value = "pageSize") Integer pageSize) throws IOException {

        // 查询
        SearchResponse<Map> searchResponse = null;

        try {
            searchResponse = client.search(s -> s
                    .index("article")
                    // 查 询
                    .query(queryBuilder -> queryBuilder
                            .multiMatch(multiMatchQueryBuilder -> multiMatchQueryBuilder
                                    .fields("title", "content", "description")
                                    .query(keyword)
                                    .operator(Operator.Or))

                    )
//                    // 分页查询，从第 0 页开始查询 3 个 document
//                    .from(pageNum)
//                    .size(pageSize)
                    // 高亮 显示
                    .highlight(highlightBuilder -> highlightBuilder
                            .preTags("<em style=\"color: orange; \">")
                            .postTags("</em>")
                            .requireFieldMatch(true) //多字段时，需要设置为false
                            .fields("title", highlightFieldBuilder -> highlightFieldBuilder)
                            .fields("content", highlightFieldBuilder -> highlightFieldBuilder)
                            .fields("description", highlightFieldBuilder -> highlightFieldBuilder)
                    )
                    // 按 createTime 降序排序
                    .sort(f -> f.field(o -> o.field("createTime").order(SortOrder.Desc))), Map.class
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Article> as = new ArrayList<>();
        // 高亮 并且 分页
        List<Hit<Map>> hits = searchResponse.hits().hits();
        for (Hit<Map> hit : hits) {
            Map<String, Object> docMap = hit.source();
            String json = JSON.toJSONString(docMap);
            Article article = JSON.parseObject(json, Article.class);
            Map<String, List<String>> highlight = hit.highlight();
//            System.out.println(article.getTitle());
            article.setTitle(highlight.get("title") == null ? article.getTitle() : highlight.get("title").get(0));
//            System.out.println(highlight.get("title").get(0));
            article.setContent(highlight.get("content") == null ? article.getTitle() : highlight.get("content").get(0));
            article.setDescription(highlight.get("description") == null ? article.getTitle() : highlight.get("description").get(0));
            as.add(article);
        }

        // 整 合
        List<ArticleListExam> articleList = new ArrayList<>();
        for(Article a: as){
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
        request.setAttribute("keyword", keyword);

        return "admin/ico";
    }

}
