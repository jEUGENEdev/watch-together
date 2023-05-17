package org.jeugenedev.watch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootConfiguration
public class SpringConfig implements WebMvcConfigurer {
    @Value("${video_directory}")
    private String videoDirectory;
    @Value("${video_directory_static}")
    private String videoDirectoryStatic;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler(videoDirectoryStatic).addResourceLocations(videoDirectory);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("main");
        registry.addViewController("/watch").setViewName("watch");
        registry.addViewController("/room").setViewName("create");
    }
}
