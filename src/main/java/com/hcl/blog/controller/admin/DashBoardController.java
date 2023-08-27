package com.hcl.blog.controller.admin;

import com.hcl.blog.entity.DashBoardStatistic;
import com.hcl.blog.service.DashboardService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashBoardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public String dashboardPage(HttpServletRequest request){
        DashBoardStatistic st = dashboardService.getDashBoardStatistics();

        request.setAttribute("statistics", st);

        return "admin/index";
    }



}
