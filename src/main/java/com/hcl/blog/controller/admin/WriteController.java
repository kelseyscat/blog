package com.hcl.blog.controller.admin;

import com.hcl.blog.service.ArticleService;
import com.hcl.blog.service.QiniuService;
import com.hcl.blog.util.MarkdownToHtmlUtili;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/write")
public class WriteController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private QiniuService qiniuService;

    @GetMapping
    public String writePage(HttpServletRequest request){
        Set<String> all_type = articleService.findAllArticleType();
        request.setAttribute("Types", all_type);
        return "admin/write";
    }

    @ResponseBody
    @RequestMapping(value="/uploadCoverImg", method= RequestMethod.POST)
    public JSONObject uploadSource(@RequestParam("file") MultipartFile file, HttpSession session) {
        JSONObject res = new JSONObject();

        if(!file.isEmpty()){
            try {
                String fileUrl = qiniuService.saveImage(file);
                res.put("code", 0);
                res.put("msg", "上传成功！");
                res.put("data", fileUrl);
                session.setAttribute("firstPicPath", fileUrl);  // 为了后续insert文章，把路径放在session里
            } catch (IOException e) {
                res.put("msg", "上传失败！");
                e.printStackTrace();
            }
        }
        return res;
//        JSONObject res = new JSONObject();
//        String pathString = null;
//        String fileName = null;
//        if(file != null){
//            fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + file.getOriginalFilename();
//            pathString = "C:/Users/c.hu/Downloads/manage/admin/images/articleImages/" + fileName;
//        }
//        try {
//            File files = new File(pathString);
//            if(!files.getParentFile().exists()){
//                files.getParentFile().mkdirs();
//            }
//            file.transferTo(files);
//            res.put("code", 0);
//            res.put("msg", fileName);
//            res.put("data", pathString);
//
//            String firstPicPath = "http://localhost:8081/images/articleImages/" + fileName;
//            session.setAttribute("firstPicPath", firstPicPath);  // 为了后续insert文章，把路径放在session里
//            System.out.println(res);
//        }catch (Exception e){
//            e.printStackTrace();
//            res.put("msg", "上传失败！");
//        }
//        return res;
    }

    @RequestMapping(value = "/newarticle", method = RequestMethod.GET)
    public String newArticle(@RequestParam String title,
                             @RequestParam String editormd,
                             @RequestParam String description,
                             @RequestParam String type,
                             Model model, HttpServletResponse response, HttpSession session){
        String firstPic = (String) session.getAttribute("firstPicPath");
        System.out.println(firstPic);
        Date createTime = new Date(); // 当前日期
        int status = 1; // 1表示状态为发布
        String htmlContent = MarkdownToHtmlUtili.markdownToHtml(editormd);

        // 提取纯文本
        String contentText = htmlContent.replaceAll("</?[^>]+>", ""); // 剔出<html>的标签
        contentText = contentText.replaceAll("<a>\\s*|\t|\r|\n</a>", ""); // 去除字符串中的空格,回车,换行符,制表符
        contentText = description + contentText;

        // 为了目录，给每个<h>加上id
        Pattern p = Pattern.compile("(?<=<h\\d>).*(?=</h\\d>)");

        Matcher m = p.matcher(htmlContent);
        List<String> hName = new ArrayList<>();
        while(m.find()){
            hName.add(m.group());
        }

        Pattern p2 = Pattern.compile("<h\\d>");
        Matcher m2 = p2.matcher(htmlContent);

        int i = 0;
        String s2 = htmlContent;
        while (m2.find()){
            String tmp = m2.group();
            tmp = tmp.substring(0, tmp.length() - 1) + " id=\"" + hName.get(i) + "\">";
            i ++ ;
            s2 = s2.replaceFirst("<h\\d>", tmp);
        }

        htmlContent = s2;

        articleService.insertNewArticle(title, status, createTime, firstPic, editormd, contentText, htmlContent, description, type);
        return "redirect:/article";
    }

}
