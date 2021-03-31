package cn.oreo5.controller;

import cn.oreo5.api.MySQLRemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ViewController {

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    // 记录主页访问量
    @RequestMapping("/user/visit/index")
    @ResponseBody
    public void index() {
        mySQLRemoteService.indexVisits();
    }

    // 记录酒店访问量
    @RequestMapping("/user/visit/hotel")
    @ResponseBody
    public void hotel() {
        mySQLRemoteService.hotelVisits();
    }

    // 记录美食访问量
    @RequestMapping("/user/visit/cate")
    @ResponseBody
    public void cate() {
        mySQLRemoteService.cateVisits();
    }

    // 记录地图访问量
    @RequestMapping("/user/visit/map")
    @ResponseBody
    public void map() {
        mySQLRemoteService.mapVisits();
    }

}
