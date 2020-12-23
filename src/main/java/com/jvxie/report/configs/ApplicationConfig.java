package com.jvxie.report.configs;

import com.jvxie.report.UnderlineToCamelArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

    /**
     * 添加参数解析器
     *
     * @param argumentResolvers 参数解析器
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new UnderlineToCamelArgumentResolver());
    }
}
