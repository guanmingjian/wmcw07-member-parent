package cn.oreo5.controller;

import cn.oreo5.entity.PO.Map;
import cn.oreo5.service.api.MapService;
import cn.oreo5.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MapProviderController {

    @Autowired
    private MapService mapService;

    @RequestMapping("/get/map/by/type/remote")
    ResultEntity<List<Map>> getMapByTypeRemote(@RequestParam("type") String type){
        try {

            // 1.调用本地Service完成查询
            List<Map> list = mapService.getMapByType(type);

            // 2.如果没有抛异常，那么就返回成功的结果
            return ResultEntity.successWithData(list);
        } catch (Exception e) {
            e.printStackTrace();

            // 3.如果捕获到异常则返回失败的结果
            return ResultEntity.failed(e.getMessage());
        }
    }
}
