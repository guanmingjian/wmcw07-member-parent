package cn.oreo5.controller;

import cn.oreo5.api.MySQLRemoteService;
import cn.oreo5.entity.PO.Map;
import cn.oreo5.util.OreoUtil;
import cn.oreo5.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MapController {

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    // 获取地图数据文章
    @RequestMapping("portal/get/map.json")
    @ResponseBody
    public ResultEntity<List<Map>> getNews(
            @RequestParam("type") String type,
            @RequestParam String longitude,
            @RequestParam String latitude
    ) {
        ResultEntity<List<Map>> mapByTypeRemote = mySQLRemoteService.getMapByTypeRemote(type);
        List<Map> mapList = mapByTypeRemote.getData();

        double lng = Double.parseDouble(longitude);
        double lat = Double.parseDouble(latitude);

        List<Map> list = OreoUtil.matchPoit(lng,lat,mapList);

        return ResultEntity.successWithData(list);
    }


}
