package cn.oreo5.controller;

import cn.oreo5.api.MySQLRemoteService;
import cn.oreo5.constant.OreoConstant;
import cn.oreo5.entity.PO.User;
import cn.oreo5.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @RequestMapping("/auth/do/user/login")
    public String login(
            @RequestParam("name") String name,
            @RequestParam("password") String password,
            ModelMap modelMap,
            HttpSession session) {

        User user = null;

        try {
            // 1.调用远程接口根据登录账号查询User对象
            user = mySQLRemoteService.checkUserLoginRemote(name,password);
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.addAttribute("loginMsg",OreoConstant.MESSAGE_LOGIN_FAILED);
            return "login.html";
        }

        if(user!=null){
            //存入session作用域中
            session.setAttribute("user",user);
            return "portal";
        }else {
            modelMap.addAttribute("loginMsg",OreoConstant.MESSAGE_LOGIN_FAILED);
            return "login.html";
        }

    }

    // 注册功能
    @RequestMapping("/auth/do/user/register.json")
    @ResponseBody
    public ResultEntity<String> register(
            @RequestParam("name") String name,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("sex") String sex,
            @RequestParam("age") String age,
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {

        return mySQLRemoteService.doRegisterRemote(name, phoneNumber, sex, age, email, password);

    }

    // 验证用户名是否存在
    @RequestMapping("auth/do/user/name/unique.json")
    @ResponseBody
    public ResultEntity<String> checkNameUnique(
            @RequestParam("name") String name
    ) {
        return mySQLRemoteService.checkNameUniqueRemote(name);
    }

    // 发送邮件
    @RequestMapping("auth/do/user/send/email")
    @ResponseBody
    public ResultEntity<String> sendEmail(
            @RequestParam("email") String email
    ) {
        // 返回验证码给前端，可以发送到注册
        String s = mySQLRemoteService.sendEmailRemote(email);

        return ResultEntity.successWithData(s);
    }

    @RequestMapping("/auth/do/user/update/password")
    public String userUpdatePassword(
            @RequestParam("name") String name,
            @RequestParam("password") String password,
            ModelMap modelMap) {
        return null;
    }

    @RequestMapping("/auth/do/user/logout")
    public String userLogout(HttpSession session) {

        session.invalidate();

        return "redirect:http://119.23.77.101/";
    }

}
