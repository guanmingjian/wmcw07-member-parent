package cn.oreo5.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class MyConfiguration implements WebMvcConfigurer {

    /**
     * 配置虚拟路径
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("/imgs/**").addResourceLocations("file:F:/imgs/");
        //registry.addResourceHandler("/imgs/**").addResourceLocations("file:/data/imgs/");
        registry.addResourceHandler("/imgs/**").addResourceLocations("file:/usr/oreo/imgs/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
