package cn.oreo5.test;

import cn.oreo5.entity.DO.ArticleTopSO;
import cn.oreo5.entity.PO.*;
import cn.oreo5.entity.VO.ArticleModuleVO;
import cn.oreo5.entity.VO.HotelVO;
import cn.oreo5.service.api.*;
import cn.oreo5.util.ResultEntity;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.StringTokenizer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyBatisTest {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private MapService mapService;

    @Autowired
    private ViewService viewService;

    private Logger logger = LoggerFactory.getLogger(MyBatisTest.class);

    @Test
    public void testService() {
        logger.info("哈哈哈");
    }

    @Test
    public void testSpilt() {

        String page = "index.html";

        if("html".equals(page.substring(page.lastIndexOf('.') + 1))){
            // 实例化对象，并指向以 . 对 page 进行分割
            StringTokenizer s = new StringTokenizer(page, ".");
            String toPage = s.nextToken();
            System.out.println(toPage);
        }else {
            System.out.println("不是html");
        }

    }

}
