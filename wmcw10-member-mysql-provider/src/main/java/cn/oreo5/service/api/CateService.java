package cn.oreo5.service.api;

import cn.oreo5.entity.PO.Cate;
import cn.oreo5.entity.VO.CateVO;
import com.github.pagehelper.PageInfo;

/**
 * @author boy
 * @create 2020-05-16 15:51
 */
public interface CateService {

    /**
     * 通过封装在cateVO的数据进行查询
     * @param cateVO
     * @return
     */
    PageInfo<Cate> getCateByCateVO(CateVO cateVO);
}
