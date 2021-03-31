package cn.oreo5.controller;

import cn.oreo5.api.MySQLRemoteService;
import cn.oreo5.entity.PO.Hotel;
import cn.oreo5.entity.VO.HotelVO;
import cn.oreo5.util.ResultEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HotelController {

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    //获取酒店数据
    @ResponseBody
    @RequestMapping("portal/get/hotel.json")
    public ResultEntity<PageInfo<Hotel>> getUserPageInfo(
            @RequestBody HotelVO hotelVO
    ) {
        // 调用Service方法获取分页数据
        return mySQLRemoteService.getHotelByHotelVORemote(hotelVO);
    }
}
