package com.hcl.blog.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.blog.entity.Article;
import com.hcl.blog.entity.ChartDate;
import com.hcl.blog.entity.DashBoardStatistic;
import com.hcl.blog.service.ArticleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class ArchiveController {
    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/archive", method = RequestMethod.GET)
    public String archivePage(HttpServletRequest request){
        Map<Integer, List<Article>> map = new HashMap<>();

        List<Article> als = articleService.getAllArticleByDate();

        for(Article a : als){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(a.getCreateTime());
            // 提取年份
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            String time_year_month = year + "-" + month;
            if (!map.containsKey(year)) {
                map.put(year, new ArrayList<>());
            }
            map.get(year).add(a);

        }

        request.setAttribute("map", map);
        return "archive";
    }

    @RequestMapping("/alarmChart")
    @ResponseBody
    public List<ChartDate> alarmChart(Model model){
        Map<String, Integer> map_time_num = new HashMap<>();

        List<Article> als = articleService.getAllArticleByDate();

        for(Article a : als){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(a.getCreateTime());
            // 提取年份
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            String time_year_month = year + "-" + month;

            if (!map_time_num.containsKey(time_year_month)) {
                map_time_num.put(time_year_month, 1);
            }
            else{
                int cnt = map_time_num.get(time_year_month);
                cnt ++ ;
                map_time_num.put(time_year_month, cnt);
            }
        }
        List<ChartDate> list = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map_time_num.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            ChartDate tmp = new ChartDate();
            tmp.setTime(key);
            tmp.setNum(value);
            list.add(tmp);
        }
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String data = null;
//        try {
//            // 将List转换为JSON字符串
//            data = objectMapper.writeValueAsString(list);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        System.out.println(data);
//        model.addAttribute("data", data);
        return list;
    }

}

