package cn.oreo5.service.api;

import cn.oreo5.entity.PO.Article;
import cn.oreo5.entity.PO.ArticleComment;
import cn.oreo5.entity.PO.User;
import cn.oreo5.entity.DO.ArticleTopSO;
import cn.oreo5.entity.VO.ArticleModuleVO;
import cn.oreo5.util.ResultEntity;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ArticleService {

    List<Article> seleteArticle();

    /**
     * 通过type查询Article
     * @param type
     * @return
     */
    List<Article> getArticleByType(String type);

    /**
     * 通过关键词keyword和文章类型type获取pageInfo类型的文章article
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @param type
     * @return
     */
    PageInfo<Article> getArticlePageByKeywordAndType(Integer pageNum, Integer pageSize, String keyword, String type,Integer userId);

    /**
     * 获得智能推荐的文章
     * @param user
     * @return
     */
    ResultEntity<List<ArticleModuleVO>> getSmartRecommendationArticle(User user);

    /**
     * 获得排行榜文章
     * @return
     */
    List<ArticleModuleVO> getRankingListArticle();

    /**
     * 获得作者其他文章
     * @param authorName
     * @return
     */
    List<ArticleModuleVO> getOtherAuthorArticle(String authorName,Integer articleId);

    /**
     * 根据ID获取文章
     * @param articleId
     * @return
     */
    ResultEntity<Article> getArticleById(Integer articleId);

    /**
     * 文章点赞
     * @param userId
     * @param articleId
     * @return
     */
    String articleAddLike(Integer userId, Integer articleId);

    /**
     * 新增访问量并存入用户的阅读记录
     * @param userId
     * @param articleId
     */
    void addReadNum(Integer userId, Integer articleId);

    /**
     * 通过文章id获取文章评论信息
     * @param articleId
     * @return
     */
    ResultEntity<List<ArticleComment>> getCommentByArticleId(Integer articleId);

    /**
     * 添加用户评论
     * @param articleId
     * @param userId
     * @return
     */
    ResultEntity<String> addCommentByArticleId(Integer articleId, String comment, Integer userId);
}
