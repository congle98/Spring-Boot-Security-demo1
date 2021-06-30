package com.app.configs;


import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc ko hiểu sao bỏ cái này đi mới import css được, trong khi ngta để vẫn dc có sao đâu @@@
public class MvcConfig implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //cung cấp tài nguyên tĩnh tức khai báo file trong resources
           registry.addResourceHandler("/resources/static/**")
                ;
    }

}
