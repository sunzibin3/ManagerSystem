package com.sun.admin.controller;

import com.sun.admin.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
@Slf4j
@Controller
public class indexController {
    @GetMapping(value = {"/","/login"})
    public String loginPage(){
        return "login";
    }

    @PostMapping("/login")
    public String main(User user, HttpSession session, Model model){

        if (StringUtils.hasLength(user.getUserName())&& StringUtils.hasLength(user.getPassWord())){
            //把登录成功的用户保存起来
            session.setAttribute("user", user);
            return "redirect:/main.html";
        }else {
            model.addAttribute("msg", "用户名密码错误");
            return "login";
        }
    }

    @GetMapping("/main.html")
    public String mainPage(HttpSession session,Model model){
        User user = (User) session.getAttribute("user");
        if (user != null){
            return "main";
        }else {
            model.addAttribute("msg", "用户名密码错误");
            return "login";
        }
    }
}
