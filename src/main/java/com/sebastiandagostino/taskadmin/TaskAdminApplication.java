package com.sebastiandagostino.taskadmin;

import com.sebastiandagostino.taskadmin.util.LoggingInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class TaskAdminApplication extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggingInterceptor()).addPathPatterns("/**");
    }

    public static void main(String[] args) {
        SpringApplication.run(TaskAdminApplication.class, args);
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(getClass().getPackage().getName()))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(generateApiInfo());
    }

    private ApiInfo generateApiInfo() {
        return new ApiInfo("Task Admin", "This is an exercise to administrate tasks.", "Version 1.0",
                "urn:tos", "sebastiandagostino@gmail.com", "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0");
    }

}
