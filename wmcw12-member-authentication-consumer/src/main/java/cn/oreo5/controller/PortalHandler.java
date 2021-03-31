package cn.oreo5.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.StringTokenizer;

@Controller
public class PortalHandler {

    private Logger logger = LoggerFactory.getLogger(PortalHandler.class);

    @RequestMapping("/")
    public String showPortalPage() {

        // 跳转到首页
        return "portal";
    }

    //自动转发受保护的页面
    @RequestMapping("{page}")
    public String main(
            @PathVariable String page,
            @RequestParam(value="type", defaultValue="0") Integer type,
            @RequestParam(value="articleId", defaultValue="0") Integer articleId,
            ModelMap modelMap
    ){
        if(type!=0){
            modelMap.addAttribute("type",type);
        }
        if(articleId!=0){
            modelMap.addAttribute("articleId",articleId);
        }

        if("html".equals(page.substring(page.lastIndexOf('.') + 1))){

            // 实例化对象，并指向以 . 对 page 进行分割
            StringTokenizer s = new StringTokenizer(page, ".");
            String toPage = s.nextToken();

            logger.info("自动转发了"+page+"页面");

            return toPage;
        }else {
            return null;
        }
    }
}
