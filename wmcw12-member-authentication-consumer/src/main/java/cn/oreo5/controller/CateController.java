package cn.oreo5.controller;

import cn.oreo5.api.MySQLRemoteService;
import cn.oreo5.entity.PO.Cate;
import cn.oreo5.entity.PO.User;
import cn.oreo5.entity.VO.CateVO;
import cn.oreo5.util.ResultEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class CateController {

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    //获取美食数据
    @ResponseBody
    @RequestMapping("portal/get/cate.json")
    public ResultEntity<PageInfo<Cate>> getCatePageInfo(
            @RequestBody CateVO cateVO,
            HttpSession session
    ) {
        User user = (User)session.getAttribute("user");
        if (user != null) {
            cateVO.setLabel(Integer.parseInt(user.getLabel()));
        } else {
            cateVO.setLabel(0);
        }
        // 调用Service方法获取分页数据
        return mySQLRemoteService.getCateByCateVORemote(cateVO);
    }
}
