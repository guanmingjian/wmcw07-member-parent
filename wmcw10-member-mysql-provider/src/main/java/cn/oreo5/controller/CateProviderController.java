package cn.oreo5.controller;

import cn.oreo5.entity.PO.Cate;
import cn.oreo5.entity.VO.CateVO;
import cn.oreo5.service.api.CateService;
import cn.oreo5.util.ResultEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CateProviderController {

    @Autowired
    private CateService cateService;

    @RequestMapping("/get/cate/by/cate/vo/remote")
    ResultEntity<PageInfo<Cate>> getCateByCateVORemote(@RequestBody CateVO cateVO){
        try {
            PageInfo<Cate> cateByCateVO = cateService.getCateByCateVO(cateVO);
            return ResultEntity.successWithData(cateByCateVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

}
