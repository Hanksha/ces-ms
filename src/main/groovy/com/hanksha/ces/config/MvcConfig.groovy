package com.hanksha.ces.config;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine
import org.thymeleaf.templateresolver.TemplateResolver;

/**
 * Created by vivien on 7/3/16.
 */

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    SpringTemplateEngine templateEngine(TemplateResolver templateResolver) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine()
        templateEngine.setTemplateResolver(templateResolver)
        templateEngine.addDialect(new LayoutDialect())
        templateEngine.addDialect(new SpringSecurityDialect())

        templateEngine
    }

}
