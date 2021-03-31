package cn.oreo5.controller;

import cn.oreo5.api.MySQLRemoteService;
import cn.oreo5.entity.PO.Photo;
import cn.oreo5.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PhotoController {

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    // 获取地图数据文章
    @RequestMapping("portal/get/photo.json")
    @ResponseBody
    public ResultEntity<List<Photo>> getPhoto(
            @RequestParam("type") String type
    ) {
        return mySQLRemoteService.getPhotoByTypeRemote(type);
    }

}
