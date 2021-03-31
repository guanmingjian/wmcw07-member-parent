package cn.oreo5.service.impl;

import cn.oreo5.entity.PO.Cate;
import cn.oreo5.entity.PO.CateExample;
import cn.oreo5.entity.VO.CateVO;
import cn.oreo5.mapper.CateMapper;
import cn.oreo5.service.api.CateService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = false)
@Service
public class CateServiceImpl implements CateService {

    @Autowired
    private CateMapper cateMapper;

    @Override
    public PageInfo<Cate> getCateByCateVO(CateVO cateVO) {

        // 1.开启分页功能
        PageHelper.startPage(cateVO.getPageNum(), cateVO.getPageSize());

        // 默认值为1，表示根据用户的label去查询匹配的美食lable；当查询位置或美食类别后，置为0，不智能推荐。
        Integer judge = 1;

        CateExample cateExample = new CateExample();
        CateExample.Criteria criteria = cateExample.createCriteria();
        CateExample.Criteria criteria1 = cateExample.createCriteria();
        CateExample.Criteria criteria2 = cateExample.createCriteria();
        CateExample.Criteria criteria3 = cateExample.createCriteria();
        cateExample.or(criteria1);
        cateExample.or(criteria2);
        cateExample.or(criteria3);

        // 规则一：店名搜索（未用上）
        if (cateVO.getKeyword() != null && cateVO.getKeyword().length() > 0) {
            criteria.andTitleLike("%" + cateVO.getKeyword() + "%");
            judge = 0;
        }

        // 规则二：接收地址，查询指定区域
        if (cateVO.getAddress() != null && cateVO.getAddress().length() > 0) {
            criteria1.andAddressLike("%" + cateVO.getAddress() + "%");
            judge = 0;
        }

        // 规则三：接收美食类别，查询指定类别
        if (cateVO.getCateClas() != null) {
            criteria2.andCateClasEqualTo(cateVO.getCateClas());
            judge = 0;
        }

        // 规则四：接收用户的id
        if (cateVO.getLabel()<=5 && cateVO.getLabel()>=1 && judge == 1) {
            criteria3.andLabelEqualTo(cateVO.getLabel());
        }


        // 2.执行查询
        List<Cate> cateList = cateMapper.selectByExample(cateExample);
        // 3.封装为PageInfo对象返回
        return new PageInfo<>(cateList);
    }
}