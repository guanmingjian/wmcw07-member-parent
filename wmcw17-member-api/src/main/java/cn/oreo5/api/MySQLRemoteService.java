package cn.oreo5.api;

import cn.oreo5.entity.PO.*;
import cn.oreo5.entity.DO.ArticleTopSO;
import cn.oreo5.entity.VO.ArticleModuleVO;
import cn.oreo5.entity.VO.CateVO;
import cn.oreo5.entity.VO.HotelVO;
import cn.oreo5.util.ResultEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("oreo-wmcw-mysql")
public interface MySQLRemoteService {

    /**
     * 通过文章类型type获取文章list
     * @param type
     * @return
     */
    @RequestMapping("/get/article/by/type/remote")
    ResultEntity<List<Article>> getArticleByTypeRemote(@RequestParam("type") String type);

    /**
     * 通过keyword获取Hotel(pageInfo)
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @return
     */
    @RequestMapping("/get/hotel/by/keyword/remote")
    ResultEntity<PageInfo<Hotel>> getHotelByKeywordRemote(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "keyword", defaultValue = "") String keyword
    );

    /**
     * 通过地图类型type获取地图map的list
     * @param type
     * @return
     */
    @RequestMapping("/get/map/by/type/remote")
    ResultEntity<List<Map>> getMapByTypeRemote(@RequestParam("type") String type);

    /**
     * 通过关键词keyword和文章类型type获取文章article（pageInfo）
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @param type
     * @return
     */
    @RequestMapping("/get/article/by/keyword/type/remote")
    ResultEntity<PageInfo<Article>> getArticleByKeywordAndTypeRemote(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("keyword") String keyword, @RequestParam("type") String type, @RequestParam("userId") Integer userId);

    /**
     * 获取智能推荐文章
     * @return
     */
    @RequestMapping("/get/smart/recommendation/article/remote")
    ResultEntity<List<ArticleModuleVO>> getSmartRecommendationArticleRemote(@RequestBody User user);

    /**
     * 检测用户登录
     * @param name
     * @param password
     * @return
     */
    @RequestMapping("/user/check/login/remote")
    User checkUserLoginRemote(@RequestParam("name") String name, @RequestParam("password") String password);

    /**
     * 通过封装在hotelVO的数据进行查询
     * @param hotelVO
     * @return
     */
    @RequestMapping("/get/hotel/by/hotel/vo/remote")
    ResultEntity<PageInfo<Hotel>> getHotelByHotelVORemote(@RequestBody HotelVO hotelVO);

    /**
     * 通过封装在cateVO的数据进行查询
     * @param cateVO
     * @return
     */
    @RequestMapping("/get/cate/by/cate/vo/remote")
    ResultEntity<PageInfo<Cate>> getCateByCateVORemote(@RequestBody CateVO cateVO);

    /**
     * 获得排行版文章
     * @return
     */
    @RequestMapping("/get/ranking/list/article/remote")
    ResultEntity<List<ArticleModuleVO>> getRankingListArticleRemote();

    /**
     * 获得作者其他文章
     * @param authorName
     * @return
     */
    @RequestMapping("/get/other/author/article/remote")
    ResultEntity<List<ArticleModuleVO>> getOtherAuthorArticleRemote(@RequestParam("authorName") String authorName, @RequestParam("articleId") Integer articleId);

    /**
     * 根据图片类型查询文章
     * @param type
     * @return
     */
    @RequestMapping("/get/photo/by/type/remote")
    ResultEntity<List<Photo>> getPhotoByTypeRemote(@RequestParam("type") String type);

    /**
     * 检测用户名是否存在
     * @param name
     * @return
     */
    @RequestMapping("/user/check/name/unique/remote")
    ResultEntity<String> checkNameUniqueRemote(@RequestParam("name") String name);
	
	/**
     * 发送邮件
     * @param email
     * @return
     */
    @RequestMapping("/user/send/email/remote")
    String sendEmailRemote(@RequestParam("email") String email);

    /**
     * 根据id去查询数据
     * @param articleId
     * @return
     */
    @RequestMapping("/get/article/by/id/remote")
    ResultEntity<Article> getArticleByIdRemote(@RequestParam("articleId") Integer articleId);

    /**
     * 文章点赞功能
     * @param articleId
     * @return
     */
    @RequestMapping("/article/add/like/remote")
    String articleAddLikeRemote(@RequestParam("userId") Integer userId, @RequestParam("articleId") Integer articleId);

    /**
     * 新增访问量并存入用户的阅读记录
     * @param userId
     * @param articleId
     */
    @RequestMapping("/article/add/read/num/remote")
    void addReadNumRemote(@RequestParam("userId") Integer userId, @RequestParam("articleId") Integer articleId);

    /**
     * 通过文章id获取文章评论信息
     * @param articleId
     * @return
     */
    @RequestMapping("/article/get/comment/by/id/remote")
    ResultEntity<List<ArticleComment>> getCommentByArticleIdRemote(@RequestParam("articleId") Integer articleId);
	
	/**
     * 注册用户
     * @param name
     * @param phoneNumber
     * @param sex
     * @param age
     * @param email
     * @param password
     * @return
     */
    @RequestMapping("/user/do/register")
    ResultEntity<String> doRegisterRemote(@RequestParam("name") String name,
                                          @RequestParam("phoneNumber") String phoneNumber,
                                          @RequestParam("sex") String sex,
                                          @RequestParam("age") String age,
                                          @RequestParam("email") String email,
                                          @RequestParam("password") String password);

    /**
     * 记录主页访问量
     */
    @RequestMapping("/user/visit/index/remote")
    void indexVisits();

    /**
     * 记录酒店访问量
     */
    @RequestMapping("/user/visit/hotel/remote")
    void hotelVisits();

    /**
     * 记录美食访问量
     */
    @RequestMapping("/user/visit/cate/remote")
    void cateVisits();

    /**
     * 记录地图访问量
     */
    @RequestMapping("/user/visit/map/remote")
    void mapVisits();

    /**
     * 添加用户评论
     * @param articleId
     * @param comment
     * @param userId
     * @return
     */
    @RequestMapping("article/add/comment/by/id/remote")
    ResultEntity<String> addCommentByArticleRemote(@RequestParam("articleId") Integer articleId, @RequestParam("comment") String comment, @RequestParam("userId") Integer userId);
}
