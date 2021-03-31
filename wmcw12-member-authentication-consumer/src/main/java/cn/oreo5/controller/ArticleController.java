package cn.oreo5.controller;

import cn.oreo5.api.MySQLRemoteService;
import cn.oreo5.constant.OreoConstant;
import cn.oreo5.entity.PO.Article;
import cn.oreo5.entity.PO.ArticleComment;
import cn.oreo5.entity.PO.User;
import cn.oreo5.entity.VO.ArticleModuleVO;
import cn.oreo5.util.ResultEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ArticleController {

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    // 获取首页的新闻文章
    @RequestMapping("portal/get/news/article.json")
    @ResponseBody
    public ResultEntity<List<Article>> getNews() {

        // 新闻类型4
        String type = "4";

        return mySQLRemoteService.getArticleByTypeRemote(type);
    }

    //获取文章数据
    @ResponseBody
    @RequestMapping("article/get/{type}/page.json")
    public ResultEntity<PageInfo<Article>> getArticlePageInfo(
            @RequestParam(value="pageNum", defaultValue="1") Integer pageNum,
            @RequestParam(value="pageSize", defaultValue="5") Integer pageSize,
            @RequestParam(value="keyword", defaultValue="") String keyword,
            @PathVariable("type") String type,
            HttpSession session
    ) {
        // 获取session中用户信息
        User user = (User)session.getAttribute("user");

        if(user!=null){
            //获取用户ID
            Integer userId = user.getId();

            return mySQLRemoteService.getArticleByKeywordAndTypeRemote(pageNum, pageSize, keyword,type,userId);
        }else {
            Integer userId = 0;

            return mySQLRemoteService.getArticleByKeywordAndTypeRemote(pageNum, pageSize, keyword,type,userId);
        }

    }

    //获取文章智能推荐数据
    @ResponseBody
    @RequestMapping("article/get/smart/recommendation.json")
    public ResultEntity<List<ArticleModuleVO>> getSmartRecommendationArticle (
            HttpSession session
    ) {
        User user = (User)session.getAttribute("user");

        if(user==null){

            User user1 = new User();
            user1.setId(0);

            return mySQLRemoteService.getSmartRecommendationArticleRemote(user1);
        }else {
            return mySQLRemoteService.getSmartRecommendationArticleRemote(user);
        }
    }

    //获取文章排行榜数据
    @ResponseBody
    @RequestMapping("article/get/ranking/list.json")
    public ResultEntity<List<ArticleModuleVO>> getRankingListArticle () {

        return mySQLRemoteService.getRankingListArticleRemote();
    }

    //获取作者其他文章数据
    @ResponseBody
    @RequestMapping("article/get/other/author.json")
    public ResultEntity<List<ArticleModuleVO>> getOtherAuthorArticle (
            @RequestParam("authorName") String authorName,
            @RequestParam("articleId") Integer articleId
    ) {

        return mySQLRemoteService.getOtherAuthorArticleRemote(authorName,articleId);
    }

    //根据ID获取文章
    @ResponseBody
    @RequestMapping("article/get/article/by/articleId.json")
    public ResultEntity<Article> getArticleById (
            @RequestParam("articleId") Integer articleId
    ) {

        return mySQLRemoteService.getArticleByIdRemote(articleId);
    }

    //文章点赞
    @ResponseBody
    @RequestMapping("article/add/like.json")
    public String articleAddLike (
            @RequestParam("articleId") Integer articleId,
            HttpSession session
    ) {

        // 获取session中用户信息
        User user = (User)session.getAttribute("user");

        if(user!=null){
            //获取用户ID
            Integer userId = user.getId();

            return mySQLRemoteService.articleAddLikeRemote(userId,articleId);
        }else {
            Integer userId = 0;

            return mySQLRemoteService.articleAddLikeRemote(articleId,articleId);
        }
    }

    //新增访问量并存入用户的阅读记录
    @ResponseBody
    @RequestMapping("article/add/read/num.json")
    public void addReadNum (
            @RequestParam("articleId") Integer articleId,
            HttpSession session
    ) {
        // 获取session中用户信息
        User user = (User)session.getAttribute("user");

        if(user!=null){
            //获取用户ID
            Integer userId = user.getId();

            mySQLRemoteService.addReadNumRemote(userId,articleId);
        }else {
            Integer userId = 0;

            mySQLRemoteService.addReadNumRemote(userId,articleId);
        }
    }

    //根据id获取评论
    @ResponseBody
    @RequestMapping("article/get/comment.json")
    public ResultEntity<List<ArticleComment>> getComment (
            @RequestParam("articleId") Integer articleId
    ) {
        return mySQLRemoteService.getCommentByArticleIdRemote(articleId);
    }

    // 添加用户评论
    @ResponseBody
    @RequestMapping("article/add/comment.json")
    public ResultEntity<String> addComment(
            @RequestParam("articleId") Integer articleId,
            @RequestParam("comment") String comment,
            HttpSession session
    ) {

        User user = (User)session.getAttribute("user");

        if(user==null){
            return ResultEntity.failed(OreoConstant.MESSAGE_ADD_COMMENT_FAILED_UNLOGIN);
        }else {
            return mySQLRemoteService.addCommentByArticleRemote(articleId, comment, user.getId());
        }
    }
}
