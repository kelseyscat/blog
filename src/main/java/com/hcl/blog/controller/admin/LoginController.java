package com.hcl.blog.controller.admin;

import com.hcl.blog.entity.User;
import com.hcl.blog.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.quartz.QuartzTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage(){
        return "admin/login";
    }

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @RequestMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        @RequestParam boolean rememberme,
                        Model model,
                        HttpServletResponse response){

        int expiredSeconds = rememberme ? 3600 * 24 * 10 : 3600 * 12;
        Map<String, Object> map = userService.login(username, password, expiredSeconds);

        if(map.containsKey("ticket")){
            Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
            cookie.setPath(contextPath);
            cookie.setMaxAge(expiredSeconds);
            response.addCookie(cookie);
//            return "admin/index";
            return "redirect:/dashboard";
        }
        else{
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            return "redirect:/admin";
        }
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout(@CookieValue("ticket") String ticket){
        userService.logout(ticket);
        return "redirect:/admin";
    }
}
