package com.kyriba.school.userservice.configuration;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.kyriba.school.userservice"))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(new ApiInfo(
            "User service REST API",
            "Contains endpoint that allows to manage users in School Application",
            "1.0",
            "Terms of service",
            new Contact(
                "Andrei Mushinsky",
                "https://github.com/AndreiMushinsky",
                "andrem911@mail.ru"),
            "License of API", "API license URL", Collections.emptyList()
        ));
  }

}
