package cn.oreo5.controller;

import cn.oreo5.entity.PO.Photo;
import cn.oreo5.service.api.PhotoService;
import cn.oreo5.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PhotoProviderController {

    @Autowired
    private PhotoService photoService;

    @RequestMapping("/get/photo/by/type/remote")
    ResultEntity<List<Photo>> getPhotoByTypeRemote(@RequestParam("type") String type){
        List<Photo> photoList = photoService.getPhotoByType(type);
        return ResultEntity.successWithData(photoList);
    }
}
