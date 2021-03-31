package cn.oreo5.controller;

import cn.oreo5.entity.PO.Article;
import cn.oreo5.entity.PO.ArticleComment;
import cn.oreo5.entity.PO.User;
import cn.oreo5.entity.DO.ArticleTopSO;
import cn.oreo5.entity.VO.ArticleModuleVO;
import cn.oreo5.service.api.ArticleService;
import cn.oreo5.util.ResultEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleProviderController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping("/get/article/by/type/remote")
    ResultEntity<List<Article>> getArticleByTypeRemote(@RequestParam("type") String type){
        try {

            // 1.调用本地Service完成查询
            List<Article> list = articleService.getArticleByType(type);

            // 2.如果没有抛异常，那么就返回成功的结果
            return ResultEntity.successWithData(list);
        } catch (Exception e) {
            e.printStackTrace();

            // 3.如果捕获到异常则返回失败的结果
            return ResultEntity.failed(e.getMessage());
        }
    }

    @RequestMapping("/get/article/by/keyword/type/remote")
    ResultEntity<PageInfo<Article>> getArticleByKeywordAndTypeRemote(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("keyword") String keyword,
            @RequestParam("type") String type,
            @RequestParam("userId") Integer userId

    ){
        try {
            PageInfo<Article> articlePageByKeywordAndType = articleService.getArticlePageByKeywordAndType(pageNum, pageSize, keyword, type,userId);
            return ResultEntity.successWithData(articlePageByKeywordAndType);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @RequestMapping("/get/smart/recommendation/article/remote")
    ResultEntity<List<ArticleModuleVO>> getSmartRecommendationArticleRemote(
            @RequestBody User user
    ){
       return articleService.getSmartRecommendationArticle(user);
    }

    @RequestMapping("/get/ranking/list/article/remote")
    ResultEntity<List<ArticleModuleVO>> getRankingListArticleRemote(){

        List<ArticleModuleVO> rankingListArticle = articleService.getRankingListArticle();

        return ResultEntity.successWithData(rankingListArticle);
    }

    @RequestMapping("/get/other/author/article/remote")
    ResultEntity<List<ArticleModuleVO>> getOtherAuthorArticleRemote(
            @RequestParam("authorName") String authorName,
            @RequestParam("articleId") Integer articleId
    ){

        List<ArticleModuleVO> articleList = articleService.getOtherAuthorArticle(authorName,articleId);

        return ResultEntity.successWithData(articleList);
    }

    @RequestMapping("/get/article/by/id/remote")
    ResultEntity<Article> getArticleByIdRemote(@RequestParam("articleId") Integer articleId){
        return articleService.getArticleById(articleId);
    }

    @RequestMapping("/article/add/like/remote")
    String articleAddLikeRemote(@RequestParam("userId") Integer userId, @RequestParam("articleId") Integer articleId){
        return articleService.articleAddLike(userId,articleId);
    }

    @RequestMapping("/article/add/read/num/remote")
    void addReadNumRemote(@RequestParam("userId") Integer userId, @RequestParam("articleId") Integer articleId){
        articleService.addReadNum(userId,articleId);
    }

    @RequestMapping("/article/get/comment/by/id/remote")
    ResultEntity<List<ArticleComment>> getCommentByArticleIdRemote(@RequestParam("articleId") Integer articleId){
        return articleService.getCommentByArticleId(articleId);
    }

    @RequestMapping("article/add/comment/by/id/remote")
    ResultEntity<String> addCommentByArticleIdRemote(@RequestParam("articleId") Integer articleId, @RequestParam("comment") String comment, @RequestParam("userId") Integer userId){
        return articleService.addCommentByArticleId(articleId, comment, userId);
    }
}
