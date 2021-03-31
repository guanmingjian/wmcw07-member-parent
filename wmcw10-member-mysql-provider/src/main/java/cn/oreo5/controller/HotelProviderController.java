package cn.oreo5.controller;

import cn.oreo5.entity.PO.Hotel;
import cn.oreo5.entity.VO.HotelVO;
import cn.oreo5.service.api.HotelService;
import cn.oreo5.util.ResultEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HotelProviderController {

    @Autowired
    private HotelService hotelService;

    @RequestMapping("/get/hotel/by/hotel/vo/remote")
    ResultEntity<PageInfo<Hotel>> getHotelByHotelVORemote(@RequestBody HotelVO hotelVO){
        PageInfo<Hotel> hotelByHotelVO = hotelService.getHotelByHotelVO(hotelVO);
        return ResultEntity.successWithData(hotelByHotelVO);
    }
}
