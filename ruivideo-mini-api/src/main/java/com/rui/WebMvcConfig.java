package com.rui;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @program: ruivideo
 * @description:
 * @author: huangrui
 * @create: 2020-07-06 14:59
 **/

@Configuration
public class WebMvcConfig  extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("file:D:/productivity/Source/Repos/WeChat/ruivideo_dev/");
    }
}
