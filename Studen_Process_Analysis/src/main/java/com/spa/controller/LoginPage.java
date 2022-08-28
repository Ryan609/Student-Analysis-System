package com.spa.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spa.model.User;
import com.spa.service.UserService;

import com.alibaba.fastjson.JSONObject;

@Controller
public class LoginPage {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/login", method=RequestMethod.GET)
    public String loginPage(HttpServletRequest request, Model m) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Boolean logined = username != null;
        m.addAttribute("logined", logined);
        return "login";
    }

    @ResponseBody
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public JSONObject login(HttpServletRequest request, @RequestBody JSONObject data) {
        User user = userService.getUser(data.get("username").toString(), data.get("password").toString());
        JSONObject result = new JSONObject();
        if(user!=null) {
            HttpSession session = request.getSession();
            session.setAttribute("username", user.getUsername());
            session.setAttribute("id", user.getId());
            session.setAttribute("admin", user.getAdmin());
            result.put("code",200);
            result.put("msg", "Login Succeed!");
            result.put("data", null);
            return result;
        }else {
            result.put("code",401);
            result.put("msg", "Login Failed!");
            result.put("data", null);
            return result;
        }
    }

    @ResponseBody
    @RequestMapping(value="/logout", method=RequestMethod.GET)
    public JSONObject logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("username");
        session.removeAttribute("id");
        JSONObject result = new JSONObject();
        result.put("code", 200);
        result.put("msg", "Logout Succeed!");
        result.put("data", null);
        return result;
    }

}