package cn.oreo5.service.impl;

import cn.oreo5.constant.OreoConstant;
import cn.oreo5.entity.PO.*;
import cn.oreo5.entity.DO.ArticleTopSO;
import cn.oreo5.entity.VO.ArticleModuleVO;
import cn.oreo5.mapper.ArticleCommentMapper;
import cn.oreo5.mapper.ArticleMapper;
import cn.oreo5.mapper.UserMapper;
import cn.oreo5.mapper.UserRecordMapper;
import cn.oreo5.service.api.ArticleService;
import cn.oreo5.service.api.UserService;
import cn.oreo5.util.OreoUtil;
import cn.oreo5.util.ResultEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional(readOnly = false)
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private UserRecordMapper userRecordMapper;

    @Autowired
    private ArticleCommentMapper articleCommentMapper;
    
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    public List<Article> seleteArticle() {
        return articleMapper.selectByExampleWithBLOBs(new ArticleExample());
    }

    public List<Article> getArticleByType(String type) {

        ArticleExample articleExample = new ArticleExample();

        ArticleExample.Criteria criteria = articleExample.createCriteria();

        criteria.andClasEqualTo(Integer.parseInt(type));

        List<Article> list = articleMapper.selectByExampleWithBLOBs(articleExample);

        // 清除TextContent中的标签符号
        for (Article article : list) {
            String newTextContent = OreoUtil.cutLabel(article.getTextContent());
            article.setTextContent(newTextContent);
        }

        return list;
    }

    @Override
    public PageInfo<Article> getArticlePageByKeywordAndType(Integer pageNum, Integer pageSize, String keyword, String type,Integer userId) {

        //如果用户登录，存入用户点赞记录
        if(userId!=0){
            UserRecord userRecord = new UserRecord();
            userRecord.setUserId(userId);
            userRecord.setArticleSearch(keyword);
            userRecordMapper.insert(userRecord);
        }

        // 1.开启分页功能
        PageHelper.startPage(pageNum, pageSize);

        ArticleExample articleExample = new ArticleExample();

        //规则1
        ArticleExample.Criteria criteria = articleExample.createCriteria();

        //规则2
        ArticleExample.Criteria criteria2 = articleExample.createCriteria();

        // 判断是否为全站搜索
        if(type.equals("all")){

            //查询分类和模糊查询title
            criteria.andTitleLike("%"+keyword+"%");

            //查询分类和模糊查询authorName
            criteria2.andAuthorNameLike("%"+keyword+"%");

            //用or关联
            articleExample.or(criteria2);
        }else {

            //查询分类和模糊查询title
            criteria.andClasEqualTo(Integer.parseInt(type));
            criteria.andTitleLike("%"+keyword+"%");

            //查询分类和模糊查询authorName
            criteria2.andClasEqualTo(Integer.parseInt(type));
            criteria2.andAuthorNameLike("%"+keyword+"%");

            //用or关联
            articleExample.or(criteria2);

        }

        // 2.执行查询
        List<Article> articleList = articleMapper.selectByExampleWithBLOBs(articleExample);

        if(articleList.size()>0){
            for (Article article : articleList) {
                if(article.getTextContent()==null){
                    article.setTextContent("暂无内容");
                }
                if(article.getTextContent()!=null){
                    if(article.getTextContent().length()>0){
                        String newTextContent = OreoUtil.cutLabel(article.getTextContent());
                        article.setTextContent(newTextContent);
                    }
                }
            }
        }

        // 3.封装为PageInfo对象返回
        return new PageInfo<>(articleList);
    }

    @Override
    public ResultEntity<List<ArticleModuleVO>> getSmartRecommendationArticle(User user) {

        String userLabel = null;

        if(user.getId()==0){
            userLabel = "2";
        }else {
            // 通过用户ID找到用户信息
            User userDb = userService.getUserById(user.getId());
            // 获取用户标签
            userLabel = userDb.getLabel();
        }

        // 排除标签5老人类
        if(userLabel.equals("5")){
            userLabel="4";
        }

        // 新建查询规则
        ArticleExample articleExample = new ArticleExample();

        ArticleExample.Criteria criteria = articleExample.createCriteria();

        criteria.andKeywordEqualTo(userLabel);

        try {
            // 通过用户标签查该分类的文章
            List<Article> articleList = articleMapper.selectByExample(articleExample);

            List<Article> articles = null;

            if(articleList.size()>4){
                // 取前4篇文章
                articles = articleList.subList(0, 4);
            }else {
                articles = articleList.subList(0, articleList.size());
            }

            // 创建ArticleModuleVO的集合
            List<ArticleModuleVO> articleModuleVOS = new ArrayList<>();

            int i = 0;

            // Article转换为ArticleModuleVO
            for (Article article : articles) {
                // 新建对象
                ArticleModuleVO articleModuleVO = new ArticleModuleVO();

                articleModuleVO.setId(article.getId());
                articleModuleVO.setType(article.getClas().toString());
                articleModuleVO.setAuthorName(article.getAuthorName());
                articleModuleVO.setTitle(article.getTitle());
                articleModuleVO.setHeadImg(article.getHeadImg());

                if(i%2!=0&&i!=0){
                    // 设置标签
                    articleModuleVO.setLabel("你最常看");
                }else {
                    // 设置标签
                    articleModuleVO.setLabel("你最喜欢");
                }
                i++;

                // 装配入集合
                articleModuleVOS.add(articleModuleVO);

            }

            return ResultEntity.successWithData(articleModuleVOS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @Override
    public List<ArticleModuleVO> getRankingListArticle() {
        //  获取所有文章
        List<Article> articleList = articleMapper.selectByExample(new ArticleExample());

        List<ArticleTopSO> list = new ArrayList<>();

        // Article转换为ArticleTopSO
        for(int i = 0 ; i< articleList.size();i++){

            Article a = articleList.get(i);

            ArticleTopSO top = new ArticleTopSO();

            top.setId(a.getId());
            top.setTitle(a.getTitle());
            top.setAuthorName(a.getAuthorName());
            top.setComNum(a.getComNum());
            top.setReadNum(a.getReadNum());
            top.setLikeNum(a.getLikeNum());
            top.setHeadImg(a.getHeadImg());
            list.add(top);
        }

        // 计算出recomIndex
        for(int i = 0 ; i<list.size();i++){
            ArticleTopSO atcTop = list.get(i);
            double recomIndex=atcTop.getComNum()*0.2+atcTop.getReadNum()*0.4+atcTop.getLikeNum()*0.4;
            atcTop.setRecomIndex(recomIndex);
        }

        List<ArticleTopSO> articleTopSOList = null;

        if(list.size()>4){
            // 取前4篇文章
            articleTopSOList = list.subList(0, 4);
        }else {
            // 取前4篇文章
            articleTopSOList = list.subList(0, list.size());
        }

        // 根据recomIndex进行快速排序
        OreoUtil.quickSort(list, 0, list.size()-1);

        // 创建ArticleModuleVO的集合
        List<ArticleModuleVO> articleModuleVOS = new ArrayList<>();

        // ArticleTopSO转换为ArticleModuleVO
        for (ArticleTopSO article : articleTopSOList) {
            // 新建对象
            ArticleModuleVO articleModuleVO = new ArticleModuleVO();

            articleModuleVO.setId(article.getId());
            articleModuleVO.setType(article.getType());
            articleModuleVO.setAuthorName(article.getAuthorName());
            articleModuleVO.setTitle(article.getTitle());
            articleModuleVO.setHeadImg(article.getHeadImg());
            // 获取recomIndex
            double recomIndex = article.getRecomIndex();
            // 四舍五入
            long firepower = Math.round(recomIndex);
            articleModuleVO.setFirepower(firepower);
            // 装配入集合
            articleModuleVOS.add(articleModuleVO);
        }

        return articleModuleVOS;
    }

    @Override
    public List<ArticleModuleVO> getOtherAuthorArticle(String authorName,Integer articleId) {

        ArticleExample articleExample = new ArticleExample();
        ArticleExample.Criteria criteria = articleExample.createCriteria();

        // 规则:作者名相同，排除本文章
        criteria.andAuthorNameEqualTo(authorName);
        criteria.andIdNotEqualTo(articleId);

        // 根据规则查询文章
        List<Article> articles = articleMapper.selectByExample(articleExample);

        List<Article> articleList = null;

        if(articles.size()>=4){
            // 取前4篇文章
            articleList = articles.subList(0, 4);
        }else {
            articleList = articles.subList(0, articles.size());
        }

        // 创建ArticleModuleVO的集合
        List<ArticleModuleVO> articleModuleVOS = new ArrayList<>();

        // ArticleTopSO转换为ArticleModuleVO
        for (Article article : articleList) {
            // 新建对象
            ArticleModuleVO articleModuleVO = new ArticleModuleVO();

            articleModuleVO.setId(article.getId());
            articleModuleVO.setType(article.getClas().toString());
            articleModuleVO.setAuthorName(article.getAuthorName());
            articleModuleVO.setTitle(article.getTitle());
            articleModuleVO.setHeadImg(article.getHeadImg());

            // 装配入集合
            articleModuleVOS.add(articleModuleVO);
        }

        return articleModuleVOS;
    }

    @Override
    public ResultEntity<Article> getArticleById(Integer articleId) {

        ArticleExample articleExample = new ArticleExample();

        ArticleExample.Criteria criteria = articleExample.createCriteria();

        criteria.andIdEqualTo(articleId);

        List<Article> articleList = articleMapper.selectByExampleWithBLOBs(articleExample);

        Article article = articleList.get(0);

        return ResultEntity.successWithData(article);
    }

    @Override
    public String articleAddLike(Integer userId, Integer articleId) {

        //如果用户登录，存入用户点赞记录
        if(userId!=0){
            UserRecord userRecord = new UserRecord();
            userRecord.setUserId(userId);
            userRecord.setArticleLikeId(articleId);
            userRecordMapper.insert(userRecord);
        }

        Article article = articleMapper.selectByPrimaryKey(articleId);
        int newLikeNum = (article.getLikeNum()+1);
        article.setLikeNum(newLikeNum);

        int i = articleMapper.updateByPrimaryKeySelective(article);

        if(i>0){
            return Integer.toString(newLikeNum);
        }

        return "点赞失败！";
    }

    @Override
    public void addReadNum(Integer userId, Integer articleId) {

        //如果用户登录，存入用户访问记录
        if(userId!=0){
            UserRecord userRecord = new UserRecord();
            userRecord.setUserId(userId);
            userRecord.setArticleReadId(articleId);
            userRecordMapper.insert(userRecord);
        }

        Article article = articleMapper.selectByPrimaryKey(articleId);

        int newReadNum = (article.getReadNum()+1);

        article.setReadNum(newReadNum);

        articleMapper.updateByPrimaryKeySelective(article);
    }

    @Override
    public ResultEntity<List<ArticleComment>> getCommentByArticleId(Integer articleId) {

        ArticleCommentExample articleCommentExample = new ArticleCommentExample();

        ArticleCommentExample.Criteria criteria = articleCommentExample.createCriteria();

        criteria.andArticleIdEqualTo(articleId);

        try {
            List<ArticleComment> articleComments = articleCommentMapper.selectByExampleWithBLOBs(articleCommentExample);
            if(articleComments.size()>0){
                return ResultEntity.successWithData(articleComments);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    @Override
    public ResultEntity<String> addCommentByArticleId(Integer articleId, String comment, Integer userId) {

        User user = userMapper.selectByPrimaryKey(userId);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(date);

        ArticleComment articleComment = new ArticleComment();
        articleComment.setArticleId(articleId);
        articleComment.setTextComment(comment);
        articleComment.setUserId(userId);
        articleComment.setUserName(user.getName());
        articleComment.setPublishTime(format);
        articleComment.setHeadImage(user.getHeadImage());

        int result = articleCommentMapper.insertSelective(articleComment);

        if (result == 1) {
            return ResultEntity.successWithData(OreoConstant.MESSAGE_ADD_COMMENT);
        } else {
            return ResultEntity.failed(OreoConstant.MESSAGE_ADD_COMMENT_FAILED);
        }
    }

}
