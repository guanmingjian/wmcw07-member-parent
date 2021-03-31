package cn.oreo5.controller;

import cn.oreo5.entity.PO.User;
import cn.oreo5.service.api.UserService;
import cn.oreo5.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserProviderHandler {

    @Autowired
    private UserService userService;

    @RequestMapping("/user/check/login/remote")
    User checkUserLoginRemote(
            @RequestParam("name") String name,
            @RequestParam("password") String password
    ){
        User user = userService.checkUserLogin(name,password);
        return user;
    }

    @RequestMapping("/user/check/name/unique/remote")
    ResultEntity<String> checkNameUniqueRemote(@RequestParam("name") String name){
        return userService.checkNameUnique(name);
    }

    @RequestMapping("/user/send/email/remote")
    String sendEmailRemote(@RequestParam("email") String email){
        return userService.sendEmail(email);
    }

    @RequestMapping("/user/do/register")
    ResultEntity<String> doRegisterRemote(@RequestParam("name") String name,
                            @RequestParam("phoneNumber") String phoneNumber,
                            @RequestParam("sex") String sex,
                            @RequestParam("age") String age,
                            @RequestParam("email") String email,
                            @RequestParam("password") String password) {
        return userService.doRegister(name, phoneNumber, sex, age, email, password);
    }
}
