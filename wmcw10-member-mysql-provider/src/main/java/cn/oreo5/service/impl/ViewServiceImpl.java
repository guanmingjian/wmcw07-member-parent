package cn.oreo5.service.impl;

import cn.oreo5.entity.PO.View;
import cn.oreo5.entity.PO.ViewExample;
import cn.oreo5.mapper.ViewMapper;
import cn.oreo5.service.api.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Transactional(readOnly = false)
@Service
public class ViewServiceImpl implements ViewService {

    @Autowired
    private ViewMapper viewMapper;

    @Override
    public void updateIndexView() {
        // 创建当前日期并格式化
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(date);

        // 新建查询规则（今天主页访问量是否为存在）
        ViewExample viewExample = new ViewExample();

        ViewExample.Criteria criteria = viewExample.createCriteria();
        criteria.andTimeEqualTo(format);
//        criteria.andIndexVisitsIsNotNull();

        List<View> viewList = viewMapper.selectByExample(viewExample);

        if (viewList.size() == 0) {
            ViewExample viewExample1 = new ViewExample();
            ViewExample.Criteria criteria1 = viewExample1.createCriteria();

            View view = new View();
            view.setIndexVisits(1);
            view.setHotelVisits(0);
            view.setCateVisits(0);
            view.setMapVisits(0);
            view.setTime(format);

            viewMapper.insertSelective(view);
        } else {
            ViewExample viewExample1 = new ViewExample();
            ViewExample.Criteria criteria1 = viewExample1.createCriteria();

            criteria1.andIdEqualTo(viewList.get(0).getId());

            View view = new View();
            view.setIndexVisits(viewList.get(0).getIndexVisits() + 1);

            viewMapper.updateByExampleSelective(view, viewExample1);
        }

    }

    @Override
    public void updateHotelView() {
        // 创建当前日期并格式化
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(date);

        // 新建查询规则（今天酒店访问量是否为存在）
        ViewExample viewExample = new ViewExample();

        ViewExample.Criteria criteria = viewExample.createCriteria();
        criteria.andTimeEqualTo(format);
        criteria.andHotelVisitsIsNotNull();

        List<View> viewList = viewMapper.selectByExample(viewExample);

        if (viewList.size() == 0) {
            ViewExample viewExample1 = new ViewExample();
            ViewExample.Criteria criteria1 = viewExample1.createCriteria();

            View view = new View();
            view.setIndexVisits(0);
            view.setHotelVisits(1);
            view.setCateVisits(0);
            view.setMapVisits(0);
            view.setTime(format);

            viewMapper.insertSelective(view);
        } else {
            ViewExample viewExample1 = new ViewExample();
            ViewExample.Criteria criteria1 = viewExample1.createCriteria();

            criteria1.andIdEqualTo(viewList.get(0).getId());

            View view = new View();
            view.setHotelVisits(viewList.get(0).getHotelVisits() + 1);

            viewMapper.updateByExampleSelective(view, viewExample1);
        }
    }

    @Override
    public void updateCateView() {
        // 创建当前日期并格式化
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(date);

        // 新建查询规则（今天美食访问量是否为存在）
        ViewExample viewExample = new ViewExample();

        ViewExample.Criteria criteria = viewExample.createCriteria();
        criteria.andTimeEqualTo(format);
        criteria.andCateVisitsIsNotNull();

        List<View> viewList = viewMapper.selectByExample(viewExample);

        if (viewList.size() == 0) {
            ViewExample viewExample1 = new ViewExample();
            ViewExample.Criteria criteria1 = viewExample1.createCriteria();

            View view = new View();
            view.setIndexVisits(0);
            view.setHotelVisits(0);
            view.setCateVisits(1);
            view.setMapVisits(0);
            view.setTime(format);

            viewMapper.insertSelective(view);
        } else {
            ViewExample viewExample1 = new ViewExample();
            ViewExample.Criteria criteria1 = viewExample1.createCriteria();

            criteria1.andIdEqualTo(viewList.get(0).getId());

            View view = new View();
            view.setCateVisits(viewList.get(0).getCateVisits() + 1);

            viewMapper.updateByExampleSelective(view, viewExample1);
        }
    }

    @Override
    public void updateMapView() {
        // 创建当前日期并格式化
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(date);

        // 新建查询规则（今天地图访问量是否为存在）
        ViewExample viewExample = new ViewExample();

        ViewExample.Criteria criteria = viewExample.createCriteria();
        criteria.andTimeEqualTo(format);
        criteria.andMapVisitsIsNotNull();

        List<View> viewList = viewMapper.selectByExample(viewExample);

        if (viewList.size() == 0) {
            ViewExample viewExample1 = new ViewExample();
            ViewExample.Criteria criteria1 = viewExample1.createCriteria();

            View view = new View();
            view.setIndexVisits(0);
            view.setHotelVisits(0);
            view.setCateVisits(0);
            view.setMapVisits(1);
            view.setTime(format);

            viewMapper.insertSelective(view);
        } else {
            ViewExample viewExample1 = new ViewExample();
            ViewExample.Criteria criteria1 = viewExample1.createCriteria();

            criteria1.andIdEqualTo(viewList.get(0).getId());

            View view = new View();
            view.setMapVisits(viewList.get(0).getMapVisits() + 1);

            viewMapper.updateByExampleSelective(view, viewExample1);
        }
    }
}
