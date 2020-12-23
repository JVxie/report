package com.jvxie.report.configs;

import com.jvxie.report.interceptors.AdminRoleInterceptor;
import com.jvxie.report.interceptors.TeacherRoleInterceptor;
import com.jvxie.report.interceptors.UserLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Bean
    public UserLoginInterceptor userLoginInterceptor() {
        return new UserLoginInterceptor();
    }

    @Bean
    public AdminRoleInterceptor adminRoleInterceptor() {
        return new AdminRoleInterceptor();
    }

    @Bean
    public TeacherRoleInterceptor teacherRoleInterceptor() {
        return new TeacherRoleInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login", "/user/register");

        registry.addInterceptor(adminRoleInterceptor())
                .addPathPatterns("/admin", "/admin/**");

        registry.addInterceptor(teacherRoleInterceptor())
                .addPathPatterns("/teacher", "/teacher/**");
    }
}
