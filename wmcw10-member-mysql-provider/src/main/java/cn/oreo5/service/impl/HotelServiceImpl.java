package cn.oreo5.service.impl;

import cn.oreo5.entity.PO.Hotel;
import cn.oreo5.entity.PO.HotelExample;
import cn.oreo5.entity.VO.HotelVO;
import cn.oreo5.mapper.HotelMapper;
import cn.oreo5.service.api.HotelService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelMapper hotelMapper;

    @Override
    public PageInfo<Hotel> getHotelByHotelVO(HotelVO hotelVO) {

        // 1.开启分页功能
        PageHelper.startPage(Integer.parseInt(hotelVO.getPageNum()), Integer.parseInt(hotelVO.getPageSize()));

        HotelExample hotelExample = new HotelExample();

        // 模糊查询
        HotelExample.Criteria criteria = hotelExample.createCriteria();
        criteria.andNameLike("%"+hotelVO.getKeyword()+"%");

        HotelExample.Criteria criteria1 = hotelExample.createCriteria();
        criteria1.andLocationLike("%"+hotelVO.getKeyword()+"%");
        hotelExample.or(criteria1);

        HotelExample.Criteria criteria2 = hotelExample.createCriteria();
        criteria2.andBriefIntroLike("%"+hotelVO.getKeyword()+"%");
        hotelExample.or(criteria2);

        HotelExample.Criteria criteria3 = hotelExample.createCriteria();
        criteria3.andTypeLike("%"+hotelVO.getKeyword()+"%");
        hotelExample.or(criteria3);


        // 比对查询
        if(hotelVO.getLocation()!=null&&hotelVO.getLocation().length()>0){
            criteria.andRegionEqualTo(hotelVO.getLocation());
            criteria1.andRegionEqualTo(hotelVO.getLocation());
            criteria2.andRegionEqualTo(hotelVO.getLocation());
            criteria3.andRegionEqualTo(hotelVO.getLocation());
        }
        if(hotelVO.getStar()!=null&&hotelVO.getStar().length()>0){
            criteria.andStarLevelEqualTo(hotelVO.getStar());
            criteria1.andStarLevelEqualTo(hotelVO.getStar());
            criteria2.andStarLevelEqualTo(hotelVO.getStar());
            criteria3.andStarLevelEqualTo(hotelVO.getStar());
        }
        if(hotelVO.getType()!=null&&hotelVO.getType().length()>0){
            criteria.andTypeEqualTo(hotelVO.getType());
            criteria1.andTypeEqualTo(hotelVO.getType());
            criteria2.andTypeEqualTo(hotelVO.getType());
            criteria3.andTypeEqualTo(hotelVO.getType());
        }

        if(hotelVO.getPrice()!=null&&hotelVO.getPrice().length()>0){
            if(hotelVO.getPrice().equals("6")){
                // 区间查询
                criteria.andPriceBetween(500,1000);
                criteria1.andPriceBetween(500,1000);
                criteria2.andPriceBetween(500,1000);
                criteria3.andPriceBetween(500,1000);
            }else {
                Integer end = Integer.parseInt(hotelVO.getPrice())*100;
                // 区间查询
                criteria.andPriceBetween(end-100,end);
                criteria1.andPriceBetween(end-100,end);
                criteria2.andPriceBetween(end-100,end);
                criteria3.andPriceBetween(end-100,end);
            }
        }

        // 2.执行查询
        List<Hotel> hotels = hotelMapper.selectByExample(hotelExample);

        // 3.封装为PageInfo对象返回
        return new PageInfo<>(hotels);
    }
}
