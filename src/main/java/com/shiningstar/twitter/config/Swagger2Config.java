package com.shiningstar.twitter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class Swagger2Config {
    private static final String HTTP_WWW_SHININGSTAR_COM = "http://www.shiningstar.com";

    @Bean
    public Docket infoApiProfit() {
        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("Creating definition for API Twitter Manager")
//                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.shiningstar.twitter.rest"))
                .paths(PathSelectors.any())
                .paths(regex("/.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Design, Build, and Document APIs for Twitter MicroService")
                .description("Documentation interface for client REST API")
                .termsOfServiceUrl(HTTP_WWW_SHININGSTAR_COM)
                .contact(new Contact("Shiningstar", HTTP_WWW_SHININGSTAR_COM, "team@shiningstar"))
                .license("Shiningstar license")
                .licenseUrl(HTTP_WWW_SHININGSTAR_COM)
                .version("1.0")
                .build();
    }
}
