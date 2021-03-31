package cn.oreo5.service.api;

import cn.oreo5.entity.PO.Hotel;
import cn.oreo5.entity.VO.HotelVO;
import com.github.pagehelper.PageInfo;

public interface HotelService {

    /**
     * 通过封装在hotelVO的数据进行查询
     * @param hotelVO
     * @return
     */
    PageInfo<Hotel> getHotelByHotelVO(HotelVO hotelVO);
}
